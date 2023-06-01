package sg.edu.nus.iss.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.server.models.User;
import sg.edu.nus.iss.server.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepo;
    
    public void saveUser(User user){

        userRepo.saveUser(user);
    }
}
