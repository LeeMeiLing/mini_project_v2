package sg.edu.nus.iss.server.services;

import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple9;
import org.web3j.tx.gas.StaticGasProvider;

import sg.edu.nus.iss.server.models.EthHospitalReview;
import sg.edu.nus.iss.server.models.HospitalCredentials;
import sg.edu.nus.iss.server.models.Statistic;

public class EthereumService {

    private Web3j web3j;
    private Credentials credentials;
    private StaticGasProvider staticGasProvider;

    public EthereumService(String infuraUrl, String privateKey) {

        // // @INFURA SEPOLIA
        // web3j = Web3j.build(new HttpService(infuraUrl));
        // System.out.println(">>> InfuraURL " + infuraUrl); // debug

        // // @INFURA SEPOLIA
        // System.out.println(">>> private Key " + privateKey); // debug
        // credentials = Credentials.create(privateKey);
        // System.out.println(">>> credentials address: " + credentials.getAddress()); // debug

        staticGasProvider = new StaticGasProvider(BigInteger.valueOf(3500000000L),BigInteger.valueOf(1500000));

        // @GANACHE
        web3j = Web3j.build(new HttpService("http://localhost:8545"));

        // @GANACHE
        credentials = Credentials.create("0x5b941717a3a871384948ce16ca331139cab05bb918b092edd1fe4c330d476077");
        System.out.println(">>> credentials address " + credentials.getAddress());

    }


    public BigInteger checkAccountBalance(Credentials credentials) throws InterruptedException, ExecutionException {

        EthGetBalance result = new EthGetBalance();
        result = web3j.ethGetBalance(credentials.getAddress(), DefaultBlockParameter.valueOf("latest"))
                .sendAsync()
                .get();
        System.out.println(">>> balance " + result.getBalance());

        return result.getBalance();

    }

    // TODO: use MOH's credentials from front end to deploy contract
    // EthHospitalReview contract deployed by MOH
    public EthHospitalReview deployEthHospitalReviewContract() throws Exception{

        System.out.println(">>> credentials address " + credentials.getAddress());

        EthHospitalReview contract = EthHospitalReview.deploy(web3j, credentials, staticGasProvider).send();

        System.out.println(">>> in eth svc, contract address: " + contract.getContractAddress()); //debug
        return contract;

    }

    // TODO: use MOH's credentials from front end to get contract
    public EthHospitalReview getEthHospitalReviewContract(String contractAddress){

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
            return HospitalCredentials.load(contractAddress, web3j, this.credentials,  staticGasProvider);
        }
        return HospitalCredentials.load(contractAddress, web3j, credentials,  staticGasProvider);
    }

    // read Penalty, use app's credential
    public BigInteger getPenalty(String contractAddress) throws Exception{

        HospitalCredentials contract = loadHospitalCredentialsContract(contractAddress, credentials);
        return contract.penalty().send();

    }

    public BigInteger updateStatistic(Statistic stat, String accountPassword, byte[] keyStore, String contractAddress) throws Exception {

        // create credentials from keystore & password
        File file = File.createTempFile("temp","txt");
        FileWriter writer = new FileWriter(file);
        
        writer.write(new String(keyStore, StandardCharsets.UTF_8));
        writer.close();
        
        Credentials credentials = WalletUtils.loadCredentials(accountPassword, file);
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
        Integer statSize = contract.getStatisticsSize().send().intValue();
        
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

}
