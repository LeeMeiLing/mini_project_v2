package sg.edu.nus.iss.server.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

import sg.edu.nus.iss.server.config.SecurityConfig;
import sg.edu.nus.iss.server.models.EthHospitalReview;
import sg.edu.nus.iss.server.models.HospitalCredentials;

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

    // public HospitalCredentials deployHospitalCredentialsContract(String ethAddressHospital, String ethAddressMOH, String license) throws Exception {

    //     Credentials credentials = Credentials.create(ethAddressHospital);
    //     System.out.println(">>> credentials address " + credentials.getAddress());

    //     HospitalCredentials contract = HospitalCredentials.deploy(web3j, credentials,
    //             new StaticGasProvider(BigInteger.valueOf(875000000), BigInteger.valueOf(3000000)), ethAddressMOH,
    //             license)
    //             .send();

    //     return contract;

    // }

    // // store contract Address in MySQL
    // public HospitalCredentials getHospitalCredentialsContract(String contractAddress) throws IOException{

    //     // Path abiPath = Path.of("..\\..\\resources\\contracts\\HospitalCredentials.abi");
    //     // String contractABI = Files.readString(abiPath);
    //     HospitalCredentials contract = HospitalCredentials.load(contractAddress, web3j, credentials, new StaticGasProvider(BigInteger.valueOf(875000000), BigInteger.valueOf(3000000)));
    //     return contract;
    // }

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




}
