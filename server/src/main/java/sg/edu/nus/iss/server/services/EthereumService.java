package sg.edu.nus.iss.server.services;

import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
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

    public EthereumService(String infuraUrl, String privateKey) {

        // @INFURA SEPOLIA
        web3j = Web3j.build(new HttpService(infuraUrl));
        System.out.println(">>> InfuraURL " + infuraUrl); // debug

        // @INFURA SEPOLIA
        System.out.println(">>> private Key " + privateKey); // debug
        credentials = Credentials.create(privateKey);
        System.out.println(">>> credentials address: " + credentials.getAddress()); // debug

        // // @GANACHE
        // web3j = Web3j.build(new HttpService("http://localhost:8545"));

        // // @GANACHE
        // credentials = Credentials.create("0xb7791c35a9621dcc594e4d732df2634b7f87dbd43a9a826f7a1a0ccdda4898fb");
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

    // TODO: use MOH's credentials from front end to deploy contract
    // EthHospitalReview contract deployed by MOH
    public EthHospitalReview deployEthHospitalReviewContract() throws Exception{

        System.out.println(">>> credentials address " + credentials.getAddress());

        EthHospitalReview contract = EthHospitalReview.deploy(web3j, credentials,
                new StaticGasProvider(BigInteger.valueOf(875000000), BigInteger.valueOf(3000000)))
                .send();

        System.out.println(">>> in eth svc, contract address: " + contract.getContractAddress()); //debug
        return contract;

    }

    // TODO: use MOH's credentials from front end to get contract
    public EthHospitalReview getEthHospitalReviewContract(String contractAddress){

        EthHospitalReview contract = EthHospitalReview.load(contractAddress, web3j, credentials, new StaticGasProvider(BigInteger.valueOf(875000000), BigInteger.valueOf(3000000)));
        return contract;

    }

    public HospitalCredentials deployHospitalCredentialsContract(byte[] keyStore, String accountPassword, String mohEthAddress, String license) throws Exception{

        File file = File.createTempFile("temp","txt");
        FileWriter writer = new FileWriter(file);
        
        writer.write(new String(keyStore, StandardCharsets.UTF_8));
        writer.close();
        
        Credentials credentials = WalletUtils.loadCredentials(accountPassword, file);
        System.out.println(">>>>>> in eth svc, credentials acc: " + credentials.getAddress());

        // deploy
        HospitalCredentials contract = HospitalCredentials.deploy(web3j, credentials, 
            new StaticGasProvider(BigInteger.valueOf(875000000), BigInteger.valueOf(3000000)), mohEthAddress, license).send();

        System.out.println(">>> in eth svc, deployed Hosp Credentials contract address: " + contract.getContractAddress()); //debug
        
        return contract;

    }

    public HospitalCredentials loadHospitalCredentialsContract(String contractAddress, Credentials credentials){

        return HospitalCredentials.load(contractAddress, web3j, credentials,  new StaticGasProvider(BigInteger.valueOf(875000000), BigInteger.valueOf(3000000)));
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
        contract.updateStatistic(BigInteger.valueOf(stat.getMortality()), BigInteger.valueOf(stat.getPatientSafety()), 
            BigInteger.valueOf(stat.getReadmission()), BigInteger.valueOf(stat.getPatientExperience()), 
            BigInteger.valueOf(stat.getEffectiveness()), BigInteger.valueOf(stat.getTimeliness()), 
            BigInteger.valueOf(stat.getMedicalImagingEfficiency()), getPenalty(contractAddress)).send();

        // get statistic index and return
        return contract.getStatisticsSize().send().subtract(BigInteger.ONE);

        // return contract.updateStatistic(floatToBigInteger(stat.getMortality()), floatToBigInteger(stat.getPatientSafety()), 
        //     floatToBigInteger(stat.getReadmission()), floatToBigInteger(stat.getPatientExperience()), 
        //     floatToBigInteger(stat.getEffectiveness()), floatToBigInteger(stat.getTimeliness()), 
        //     floatToBigInteger(stat.getMedicalImagingEfficiency()), getPenalty(contractAddress)).send();
        
    }

    public Statistic getStatistic(String contractAddress, Integer statIndex) throws Exception{

        HospitalCredentials contract = loadHospitalCredentialsContract(contractAddress, credentials);

        Tuple9<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, Boolean>  result = 
            contract.statistics(BigInteger.valueOf(statIndex)).send();

        Statistic stat = new Statistic(result.component1().intValue(), result.component2().intValue(), result.component3().intValue(),
           result.component4().intValue(), result.component5().intValue(), result.component6().intValue(),
           result.component7().intValue(), result.component8().toString(), result.component9());
        
        return stat;
    }


    // public BigInteger floatToBigInteger(float floatValue){

    //     // Convert float to BigDecimal
    //     BigDecimal decimalValue = BigDecimal.valueOf(floatValue);

    //     // Multiply by a scale factor to maintain decimal places
    //     BigDecimal scaledValue = decimalValue.multiply(BigDecimal.valueOf(100)); // Preserve only 2 decimal places

    //     // Convert to BigInteger
    //     return scaledValue.toBigInteger().clearBit(0)

    // }

    // public float bigIntegerToFloat(BigInteger bigIntValue){

    //     BigDecimal scaledValue = new BigDecimal(bigIntValue);
        
    //     BigDecimal decimalValue = scaledValue.divide(BigDecimal.valueOf(100));

    //     return decimalValue.floatValue();

    // }





}
