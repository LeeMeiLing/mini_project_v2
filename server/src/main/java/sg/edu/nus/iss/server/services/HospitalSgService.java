package sg.edu.nus.iss.server.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.Keys;

import sg.edu.nus.iss.server.exceptions.PostReviewFailedException;
import sg.edu.nus.iss.server.exceptions.RegisterHospitalFailedException;
import sg.edu.nus.iss.server.exceptions.ResultNotFoundException;
import sg.edu.nus.iss.server.exceptions.UpdateStatisticFailedException;
import sg.edu.nus.iss.server.models.HospitalCredentials;
import sg.edu.nus.iss.server.models.HospitalSg;
import sg.edu.nus.iss.server.models.Statistic;
import sg.edu.nus.iss.server.repositories.HospitalSgRepository;
import sg.edu.nus.iss.server.security.managers.CustomAuthenticationManagerForHospital;

@Service
public class HospitalSgService {

    @Autowired
    private HospitalSgRepository hospSgRepo;

    @Autowired
    private EthereumService ethSvc;

    @Autowired
    private CustomAuthenticationManagerForHospital customAuthenticationManagerForHospital;
    
    @Transactional(rollbackFor = RegisterHospitalFailedException.class)
    public void registerHospitalSg(HospitalSg hospital, String accountPassword) throws Exception {

        // save to MySQL
        boolean saved = hospSgRepo.insertHospitalSg(hospital);

        if(!saved){
            throw new RegisterHospitalFailedException("failed to insert HospitalSg into sg_hospitals");
        }

        // get MOH address from MySQL
        String mohEthAddress = hospSgRepo.getMohAddress(hospital.getCountryCode());

        HospitalCredentials contract;

        try {
            // deploy contract
            contract =  ethSvc.deployHospitalCredentialsContract(hospital.getEncryptedKeyStore(), accountPassword, 
            mohEthAddress, hospital.getLicense());

        } catch (Exception ex) {
            System.out.println(">>> failed to deploy contract!!");
            ex.printStackTrace();
            throw new RegisterHospitalFailedException("failed to deploy Hospital Credentials contract");
        }

        // update contractAdress to MySQL
        boolean updated = hospSgRepo.updateContractAddress(hospital.getFacilityId(), contract.getContractAddress());

        if(!updated){
            throw new RegisterHospitalFailedException("failed to update contract address into sg_hospitals");
        }

    }

    @Transactional(rollbackFor = UpdateStatisticFailedException.class)
    public Integer updateStatistic(Statistic stat, String accountPassword) throws UpdateStatisticFailedException{

        // get current user eth address
        String ethAddress = customAuthenticationManagerForHospital.getCurrentUser();
        byte[] keyStore = null;
        String contractAddress = null; 
        
        // get contract address & keystore by eth address
        Optional<HospitalSg> opt = hospSgRepo.findHospitalSgByEthAddress(ethAddress);

        if(opt.isPresent()) {
            HospitalSg hosp = opt.get();
            keyStore = hosp.getEncryptedKeyStore();
            contractAddress = hosp.getContractAddress();
        }else{
            throw new UpdateStatisticFailedException("hospitalSg not found for eth Address: " + ethAddress);
        }

        if(keyStore == null){
            throw new UpdateStatisticFailedException("keyStore not found for eth Address " + ethAddress);

        }

        if(contractAddress == null){
            throw new UpdateStatisticFailedException("contract address not found for eth Address " + ethAddress);

        }

        try{
            // update contract
            BigInteger index = ethSvc.updateStatistic(stat, accountPassword, keyStore, contractAddress);
            System.out.println(">>> In HospSgSvc, done updating Eth Stat");

            // update MySQL
            boolean inserted = hospSgRepo.updateStatistic(index.intValue(), contractAddress); // TODO: supporting doc

            if(!inserted){
                throw new UpdateStatisticFailedException("Failed to update statistic in MySQL");
            }

            return index.intValue();

        }catch(Exception ex){
            ex.printStackTrace();
            throw new UpdateStatisticFailedException(ex.getMessage());

        }

    }

    // public Statistic getStatistic(Integer statIndex){

    //     // get contract address by statIndex
    //     Optional<String> opt = hospSgRepo.getContractAddressByStatisticIndex(statIndex);

    //     String contractAddress = null;

    //     if(opt.isPresent()){
    //         contractAddress = opt.get();
    //     }

    //     if(contractAddress == null){
    //         throw new ResultNotFoundException("contractAddress");
    //     }

    //     try{
    //         // get statistic from contract
    //         Statistic stat = ethSvc.getStatistic(contractAddress, statIndex);
    //         return stat;

    //     }catch(Exception ex){
    //         throw new ResultNotFoundException("statistic " + statIndex );

    //     }

    // }

     public Statistic getStatisticByFacilityIdAndStatIndex(String facilityId,Integer statIndex){

        // if facilityId == null, get facilityId from current user
        if(facilityId == null){
            String ethAddress = customAuthenticationManagerForHospital.getCurrentUser();
            facilityId = hospSgRepo.findHospitalSgByEthAddress(ethAddress).get().getFacilityId();
        }

        // get contract address by facilityId
        Optional<HospitalSg> opt = hospSgRepo.findHospitalSgByFacilityId(facilityId);

        String contractAddress = null;

        if(opt.isPresent()){
            HospitalSg hosp = opt.get();
            contractAddress = hosp.getContractAddress();
        }
        
        if(contractAddress == null){
            throw new ResultNotFoundException("facilityId " + facilityId);
        }

        try{
            // get statistic from contract
            Statistic stat = ethSvc.getStatistic(contractAddress, statIndex);
            return stat;

        }catch(Exception ex){
            throw new ResultNotFoundException("statistic " + statIndex );

        }

    }

    public List<HospitalSg> getHospitalsByPendingVerify(){

        Optional<List<HospitalSg>> opt = hospSgRepo.getHospitalsByPendingVerify();

        if(opt.isPresent()){
            return opt.get(); // return empty list if not found
        }

        return null;
    }

    public List<HospitalSg> getHospitalsByStatPendingVerify() {

         Optional<List<HospitalSg>> opt = hospSgRepo.getHospitalsByStatPendingVerify();

        if(opt.isPresent()){
            System.out.println("in svc stat pending ver: " + opt.get());
            return opt.get(); // return empty list if not found
        }

        return null;
    }



}
