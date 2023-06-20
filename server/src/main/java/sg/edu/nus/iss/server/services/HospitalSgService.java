package sg.edu.nus.iss.server.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.Keys;

import sg.edu.nus.iss.server.exceptions.PostReviewFailedException;
import sg.edu.nus.iss.server.exceptions.RegisterHospitalFailedException;
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
    private RedisTemplate<String, Object> redisTemplate;

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

    public void updateStatistic(Statistic stat, String accountPassword) throws UpdateStatisticFailedException{

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
        }

        try{
            ethSvc.updateStatistic(stat, accountPassword, keyStore, contractAddress);
            System.out.println(">>> In HospSgSvc, done updateing Stat");

        }catch(Exception ex){
            ex.printStackTrace();
            throw new UpdateStatisticFailedException(ex.getMessage());

        }


    }

}
