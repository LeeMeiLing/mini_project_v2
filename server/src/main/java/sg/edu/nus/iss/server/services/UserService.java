package sg.edu.nus.iss.server.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.server.models.User;
import sg.edu.nus.iss.server.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder; // = new BCryptPasswordEncoder();
    
    public boolean saveUser(User user){

        // check if userEmail already exist
        Optional<User> opt = userRepo.findUser(user.getUserEmail());
        
        if (opt.isPresent()){
            return false;
        }

        user.setUserPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));
        System.out.println(">>> in service, encoded password: " + user.getUserPassword()); // debug
        
        return userRepo.saveUser(user);
    }

    public User findUser(String userEmail){
        
       Optional<User> user = userRepo.findUser(userEmail);

       if(user.isPresent()){
            return user.get();
       }else{
            throw new RuntimeException(); // change to customed exception
       }

    }
}
