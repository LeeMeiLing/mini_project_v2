package sg.edu.nus.iss.server.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.server.exceptions.RegisterHospitalFailedException;
import sg.edu.nus.iss.server.models.HospitalSg;
import sg.edu.nus.iss.server.models.Moh;
import sg.edu.nus.iss.server.models.User;
import sg.edu.nus.iss.server.repositories.HospitalRepository;
import sg.edu.nus.iss.server.repositories.HospitalSgRepository;
import sg.edu.nus.iss.server.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private HospitalSgRepository hospSgRepo;

    @Autowired
    private HospitalRepository hospRepo;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    
    public boolean saveUser(User user) throws RegisterHospitalFailedException{

        // check if userEmail already exist
        Integer exist = userRepo.checkUserExist(user.getUserEmail());
        
        if (exist > 0){
            throw new RegisterHospitalFailedException("This is email has been registered. Please use another email.");
        }

        user.setUserPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));
        
        return userRepo.saveUser(user);
    }

    public User findUser(String userEmail){
        
       Optional<User> user = userRepo.findUser(userEmail);

       if(user.isPresent()){
            return user.get();
       }else{
            throw new RuntimeException("Cannot find user with " + userEmail); 
       }

    }

    // if autowired HospitalSgService to CustomAuthenticationManagerForHospital, will cause unresolvable circular reference
    public HospitalSg findHospitalSgByEthAddress(String ethAddress){

        Optional<HospitalSg> opt = hospSgRepo.findHospitalSgByEthAddress(ethAddress);

        if(opt.isPresent()){
            return opt.get();
        }
        throw new RuntimeException("Cannot find hospital with eth_address " + ethAddress);
    }

    public Moh getMohByEthAddress(String mohEthAddress){

         Optional<Moh> opt = hospRepo.getMohByEthAddress(mohEthAddress);

        if(opt.isPresent()){
            return opt.get();
        }
        throw new RuntimeException("Cannot find moh with eth_address " + mohEthAddress);
    }
   
}
