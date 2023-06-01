package sg.edu.nus.iss.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.server.models.User;
import sg.edu.nus.iss.server.services.UserService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userSvc;
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveUser(@RequestBody User user){

        System.out.println(">>> in controller, registerNewUser: " + user);// debug
        userSvc.saveUser(user);
        return null;

    }
}
