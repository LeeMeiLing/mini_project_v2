package sg.edu.nus.iss.server.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.server.exceptions.RegisterHospitalFailedException;
import sg.edu.nus.iss.server.models.HospitalCredentials;
import sg.edu.nus.iss.server.models.HospitalSg;
import sg.edu.nus.iss.server.repositories.HospitalSgRepository;
import sg.edu.nus.iss.server.security.managers.CustomAuthenticationManager;

@Service
public class HospitalSgService {

    private Logger logger = Logger.getLogger(HospitalService.class.getName());

    @Autowired
    private HospitalSgRepository hospSgRepo;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private CustomAuthenticationManager customAuthenticationManager;

    @Autowired
    private EthereumService ethSvc;
    
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
            throw new RegisterHospitalFailedException("failed to deploy Hospital Credentials contract");
        }

        // update contractAdress to MySQL
        boolean updated = hospSgRepo.updateContractAddress(hospital.getFacilityId(), contract.getContractAddress());

        if(!updated){
            throw new RegisterHospitalFailedException("failed to update contract address into sg_hospitals");
        }

    }

}
