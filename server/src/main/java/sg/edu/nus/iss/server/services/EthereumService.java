package sg.edu.nus.iss.server.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple9;
import org.web3j.tx.gas.StaticGasProvider;

import sg.edu.nus.iss.server.exceptions.UpdateContractFailedException;
import sg.edu.nus.iss.server.exceptions.WrongPasswordException;
import sg.edu.nus.iss.server.models.EthHospitalReview;
import sg.edu.nus.iss.server.models.HospitalCredentials;
import sg.edu.nus.iss.server.models.Statistic;

public class EthereumService {

    private Web3j web3j;
    private Credentials credentials;
    private StaticGasProvider staticGasProvider;

    public EthereumService(String infuraUrl, String privateKey) {

        // @INFURA SEPOLIA
        web3j = Web3j.build(new HttpService(infuraUrl));
        System.out.println(">>> InfuraURL " + infuraUrl); // debug

        // @INFURA SEPOLIA
        System.out.println(">>> private Key " + privateKey); // debug
        credentials = Credentials.create(privateKey);
        System.out.println(">>> credentials address: " + credentials.getAddress()); // debug

        staticGasProvider = new StaticGasProvider(BigInteger.valueOf(3500000000L),BigInteger.valueOf(1500000));

        // // @GANACHE
        // web3j = Web3j.build(new HttpService("http://localhost:8545"));

        // // @GANACHE
        // credentials = Credentials.create("0x5b941717a3a871384948ce16ca331139cab05bb918b092edd1fe4c330d476077");
        // System.out.println(">>> credentials address " + credentials.getAddress());

    }


    public BigInteger checkAccountBalance(Credentials credentials) throws InterruptedException, ExecutionException {

        EthGetBalance result = new EthGetBalance();
        result = web3j.ethGetBalance(credentials.getAddress(), DefaultBlockParameter.valueOf("latest"))
                .sendAsync()
                .get();
        System.out.println(">>> balance " + result.getBalance());

        return result.getBalance();

    }

    // EthHospitalReview contract deployed by MOH
    public EthHospitalReview deployEthHospitalReviewContract(byte[] keyStore, String accountPassword) throws Exception{

        Credentials credentials = createCredentialsFromKeyStore(keyStore, accountPassword);

        EthHospitalReview contract = EthHospitalReview.deploy(web3j, credentials, staticGasProvider).send();

        System.out.println(">>> in eth svc, contract address: " + contract.getContractAddress()); //debug
        return contract;

    }
    
    public EthHospitalReview getEthHospitalReviewContract(String contractAddress, byte[] keyStore, String accountPassword) throws IOException, WrongPasswordException{

        Credentials credentials = this.credentials;

        if(keyStore != null && accountPassword!= null){
            credentials = createCredentialsFromKeyStore(keyStore, accountPassword);
        }

        EthHospitalReview contract = EthHospitalReview.load(contractAddress, web3j, credentials, staticGasProvider);
        return contract;

    }

    public HospitalCredentials deployHospitalCredentialsContract(byte[] keyStore, String accountPassword, String mohEthAddress, String license) throws Exception{

        System.out.println("staticGasProvider: " + staticGasProvider);

        File file = File.createTempFile("temp","txt");
        FileWriter writer = new FileWriter(file);
        
        writer.write(new String(keyStore, StandardCharsets.UTF_8));
        writer.close();
        
        Credentials credentials = WalletUtils.loadCredentials(accountPassword, file);
        System.out.println(">>>>>> in eth svc, credentials acc: " + credentials.getAddress());

        // deploy
        HospitalCredentials contract = HospitalCredentials.deploy(web3j, credentials, 
            staticGasProvider, mohEthAddress, license).send();

        System.out.println(">>> in eth svc, deployed Hosp Credentials contract address: " + contract.getContractAddress()); //debug
        
        return contract;

    }

    public HospitalCredentials loadHospitalCredentialsContract(String contractAddress, Credentials credentials){

        if(credentials == null){
            System.out.println(">>> in ethh svc done loading contract (if credentials null): " + contractAddress);

            return HospitalCredentials.load(contractAddress, web3j, this.credentials,  staticGasProvider);
        }

        System.out.println(">>> in ethh svc done loading contract: " + contractAddress);
        return HospitalCredentials.load(contractAddress, web3j, credentials,  staticGasProvider);
    }

    // read Penalty, use app's credential
    public BigInteger getPenalty(String contractAddress) throws Exception{

        HospitalCredentials contract = loadHospitalCredentialsContract(contractAddress, credentials);
        return contract.penalty().send();

    }

    public Credentials createCredentialsFromKeyStore(byte[] keyStore, String accountPassword) throws IOException, WrongPasswordException {
        // create credentials from keystore & password
        File file = File.createTempFile("temp","txt");
        FileWriter writer = new FileWriter(file);
        
        writer.write(new String(keyStore, StandardCharsets.UTF_8));
        writer.close();
        
        try{
            return WalletUtils.loadCredentials(accountPassword, file);

        }catch(CipherException ex){
            throw new WrongPasswordException("Failed to create credentials with provided password.");
        }

    }

    public BigInteger updateStatistic(Statistic stat, String accountPassword, byte[] keyStore, String contractAddress) throws Exception{

        Credentials credentials = createCredentialsFromKeyStore(keyStore, accountPassword);
        System.out.println(">>>>>> in eth svc, credentials acc: " + credentials.getAddress()); // debug

        // load contract from contractAddress
        HospitalCredentials contract = loadHospitalCredentialsContract(contractAddress, credentials);

        // update stat into contract
         contract.updateStatistic(doubleToBigInteger(stat.getMortality()), doubleToBigInteger(stat.getPatientSafety()), 
            doubleToBigInteger(stat.getReadmission()), doubleToBigInteger(stat.getPatientExperience()), 
            doubleToBigInteger(stat.getEffectiveness()), doubleToBigInteger(stat.getTimeliness()), 
            doubleToBigInteger(stat.getMedicalImagingEfficiency()), getPenalty(contractAddress)).send();

        // get statistic index and return
        return contract.getStatisticsSize().send().subtract(BigInteger.ONE);
        
    }

    public Statistic getStatistic(String contractAddress, Integer statIndex) throws Exception{

        HospitalCredentials contract = loadHospitalCredentialsContract(contractAddress, credentials);

        Tuple9<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, Boolean>  result = 
            contract.statistics(BigInteger.valueOf(statIndex)).send();
        
        Statistic stat = new Statistic(bigIntegerToDouble(result.component1()), bigIntegerToDouble(result.component2()), bigIntegerToDouble(result.component3()),
           bigIntegerToDouble(result.component4()), bigIntegerToDouble(result.component5()), bigIntegerToDouble(result.component6()),
           bigIntegerToDouble(result.component7()), result.component8().toString(), result.component9());

        // Statistic stat = new Statistic(bigIntegerToDouble(result.component1()), bigIntegerToDouble(result.component2()), bigIntegerToDouble(result.component3()),
        //    bigIntegerToDouble(result.component4()), bigIntegerToDouble(result.component5()), bigIntegerToDouble(result.component6()),
        //    bigIntegerToDouble(result.component7()), new Date(result.component8().longValue()), result.component9());

        return stat;
    }

     public BigInteger doubleToBigInteger(Double doubleValue){

        // Convert double to BigDecimal
        BigDecimal decimalValue = BigDecimal.valueOf(doubleValue);

        // Multiply by a scale factor to maintain decimal places
        BigDecimal scaledValue = decimalValue.multiply(BigDecimal.valueOf(100)); // Preserve only 2 decimal places

        // Convert to BigInteger
        return scaledValue.toBigInteger();

    }

     public Double bigIntegerToDouble(BigInteger bigIntValue){

        BigDecimal scaledValue = new BigDecimal(bigIntValue);
        
        BigDecimal decimalValue = scaledValue.divide(BigDecimal.valueOf(100));

        return decimalValue.doubleValue();

    }

     public List<Integer> getUnverifiedStatIndex(String contractAddress) throws Exception{
        // >> for each contract, get stat size
        // >> loop through stat to check for unverified stat, get the index
        HospitalCredentials contract = loadHospitalCredentialsContract(contractAddress, credentials);
        System.out.println("done loading contract in getUnverifiedStatIndex");
        Integer statSize = contract.getStatisticsSize().send().intValue();

        System.out.println("statSize = " + statSize);
        
        Statistic stat;
        List<Integer> unverifiedStatIndex = new ArrayList<>();

        for(int i=0; i < statSize; i++){
            stat = getStatistic(contractAddress, i);
            if(!stat.isVerified()){
                unverifiedStatIndex.add(i);
            }
        }

        return unverifiedStatIndex;
    }

    public Integer getLatestVerifiedStatIndex(String contractAddress) throws Exception{

        HospitalCredentials contract = loadHospitalCredentialsContract(contractAddress, credentials);
        Integer statSize = contract.getStatisticsSize().send().intValue();
        
        Statistic stat;
        Integer latestVerifiedStatIndex = -1;

        for(int i=0; i < statSize; i++){
            
            stat = getStatistic(contractAddress, i);
            if(stat.isVerified()){
                latestVerifiedStatIndex = i;
            }
        }

        return latestVerifiedStatIndex;
    }

    public List<Statistic> getAllStatistic(String contractAddress) throws Exception{

        HospitalCredentials contract = loadHospitalCredentialsContract(contractAddress, credentials);
        Integer statSize = contract.getStatisticsSize().send().intValue();

        Statistic stat ;
        List<Statistic> statistics = new ArrayList<>();

        for(int i=0; i < statSize; i++){
            
            stat = getStatistic(contractAddress, i);
            statistics.add(stat);
        }

        return statistics;
    }

    public void verifyLicense(String contractAddress, byte[] keyStore, String accountPassword) throws Exception {

        Credentials credentials = createCredentialsFromKeyStore(keyStore, accountPassword);

        System.out.println("In eth svc managed to create credentials: " + credentials.getAddress());

        HospitalCredentials contract = loadHospitalCredentialsContract(contractAddress, credentials);

        contract.verifyLicense().send();

    }

    public void verifyJci(String contractAddress, byte[] keyStore, String accountPassword) throws Exception {

        Credentials credentials = createCredentialsFromKeyStore(keyStore, accountPassword);

        HospitalCredentials contract = loadHospitalCredentialsContract(contractAddress, credentials);

        contract.verifyJciAccredited().send();

    }

    public void setUpdateFrequencyandPenalty(String contractAddress, byte[] keyStore, String accountPassword ,
        String updateFrequency, String penalty) throws WrongPasswordException, UpdateContractFailedException{

        Credentials credentials;
        try {
            credentials = createCredentialsFromKeyStore(keyStore, accountPassword);
        } catch (IOException | WrongPasswordException e) {
            throw new WrongPasswordException("Failed to create credentials due to invalid password");
        }

        HospitalCredentials contract = loadHospitalCredentialsContract(contractAddress, credentials);

        try {
            contract.setUpdateFrequency(BigInteger.valueOf(Long.parseLong(updateFrequency))).send();
        } catch (Exception e) {
            throw new UpdateContractFailedException("Failed to set update frequency in contract");
        }

        try {
            contract.setPenalty(BigInteger.valueOf(Long.parseLong(penalty)*1000000000)).send();
        } catch (Exception e) {
            throw new UpdateContractFailedException("Failed to set penalty in contract");

        }

    }

    public BigInteger getUpdateFrequency(String contractAddress) throws Exception{

        HospitalCredentials contract = loadHospitalCredentialsContract(contractAddress, credentials);
        return contract.updateFrequency().send();

    }


    public void verifyStatistic(String contractAddress, byte[] keyStore, Integer statIndex,
            String accountPassword) throws Exception{

        Credentials credentials;
        try {
            credentials = createCredentialsFromKeyStore(keyStore, accountPassword);
        } catch (IOException | WrongPasswordException e) {
            
            throw new WrongPasswordException("Failed to create credentials due to invalid password");
        }

        HospitalCredentials contract = loadHospitalCredentialsContract(contractAddress, credentials);

        contract.verifyStatistic(BigInteger.valueOf(statIndex)).send();
        
    }

}
