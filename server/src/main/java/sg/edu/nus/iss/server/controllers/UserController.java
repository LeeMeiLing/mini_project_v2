package sg.edu.nus.iss.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${KEY_STORE_PASSWORD}")
    private String keyStorePassword;
    
    @PostMapping(path = "/register/public", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveUser(@RequestBody User user){

        System.out.println(">>> in controller, registerNewUser: " + user);// debug
        if(userSvc.saveUser(user)){
            JsonObject payload = Json.createObjectBuilder().add("email", user.getUserEmail()).build();
            return ResponseEntity.status(HttpStatus.CREATED).body(payload.toString());
        }

        JsonObject payload = Json.createObjectBuilder().add("error", "Fail to register user").build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(payload.toString());

    }

    // @PostMapping(path = "/register/healthcareprovider/sg", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<String> registerHospitalSg(@RequestBody String payload){

    //     JsonReader rd = Json.createReader(new StringReader(payload));
    //     JsonObject jo = rd.readObject();

    //     System.out.println(">>> in controller, registerHealthcareProvider: " + payload);// debug

    //     HospitalSg hospital = HospitalSg.createHospitalSg(jo);

    //     System.out.println("hospital: " + hospital); // debug

    //     userSvc.registerHospitalSg(hospital,jo.getString("accountPassword"));

    //     return null;

    // }

    // @GetMapping(path = "/getKeyStorePassword", produces = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<String> getKeyStorePassword(){

    //     System.out.println("keyStorePassword: " + keyStorePassword);
    //     JsonObject payload = Json.createObjectBuilder().add("keyStorePassword", keyStorePassword).build();
    //     return ResponseEntity.status(HttpStatus.OK).body(payload.toString());

    // }




    // @PostMapping(path = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<String> logInUser(@RequestBody User user){

    //     System.out.println(">>> in controller, logInUser: " + user);// debug
    //     JsonObject payload = Json.createObjectBuilder().add("test", "Test message").build();
    //     return new ResponseEntity<String>(payload.toString(),HttpStatus.OK);
    // }

    
    // @GetMapping(path = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<String> test(){

    //     System.out.println(">>> in controller, test");// debug
    //     User user = userSvc.findUser("kitty@gmail.com");
    //     // if(userSvc.saveUser(user)){
    //     //     JsonObject payload = Json.createObjectBuilder().add("email", user.getUserEmail()).build();
    //     //     return ResponseEntity.status(HttpStatus.CREATED).body(payload.toString());
    //     // }

    //     JsonObject payload = Json.createObjectBuilder().add("test", user.getUserPassword()).build();
    //     return ResponseEntity.status(HttpStatus.OK).body(payload.toString());

    // }
}
