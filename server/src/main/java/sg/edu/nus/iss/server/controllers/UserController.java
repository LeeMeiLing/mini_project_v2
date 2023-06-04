package sg.edu.nus.iss.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.server.models.User;
import sg.edu.nus.iss.server.services.UserService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userSvc;
    
    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveUser(@RequestBody User user){

        System.out.println(">>> in controller, registerNewUser: " + user);// debug
        if(userSvc.saveUser(user)){
            JsonObject payload = Json.createObjectBuilder().add("email", user.getUserEmail()).build();
            return ResponseEntity.status(HttpStatus.CREATED).body(payload.toString());
        }

        JsonObject payload = Json.createObjectBuilder().add("error", "Fail to register user").build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(payload.toString());

    }

    // @PostMapping(path = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<String> logInUser(@RequestBody User user){

    //     System.out.println(">>> in controller, logInUser: " + user);// debug
    //     // if(userSvc.saveUser(user)){
    //     //     JsonObject payload = Json.createObjectBuilder().add("email", user.getUserEmail()).build();
    //     //     return ResponseEntity.status(HttpStatus.CREATED).body(payload.toString());
    //     // }

    //     // JsonObject payload = Json.createObjectBuilder().add("error", "Fail to register user").build();
    //     // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(payload.toString());
    //     JsonObject payload = Json.createObjectBuilder().add("test", "Test message").build();
    //     return new ResponseEntity<String>(payload.toString(),HttpStatus.OK);
    // }

    // @PostMapping(path = "/test", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<String> logInUser(@RequestBody User user){

    //     System.out.println(">>> in controller, test logInUSer: " + user);// debug
    //     userSvc.findUser(user.getUserEmail());
    //     // if(userSvc.saveUser(user)){
    //     //     JsonObject payload = Json.createObjectBuilder().add("email", user.getUserEmail()).build();
    //     //     return ResponseEntity.status(HttpStatus.CREATED).body(payload.toString());
    //     // }

    //     // JsonObject payload = Json.createObjectBuilder().add("error", "Fail to register user").build();
    //     // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(payload.toString());

    //     return null;
    // }
}
