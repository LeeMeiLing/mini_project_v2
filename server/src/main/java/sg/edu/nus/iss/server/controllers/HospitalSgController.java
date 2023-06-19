package sg.edu.nus.iss.server.controllers;

import java.io.StringReader;

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
import jakarta.json.JsonReader;
import sg.edu.nus.iss.server.models.HospitalSg;
import sg.edu.nus.iss.server.services.HospitalSgService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/hospitals/sg")
public class HospitalSgController {

    @Autowired
    private HospitalSgService hospSgSvc;;
    
    @PostMapping(path = "/register/hospital", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerHospitalSg(@RequestBody String payload) throws Exception{

        JsonReader rd = Json.createReader(new StringReader(payload));
        JsonObject jo = rd.readObject();

        HospitalSg hospital = HospitalSg.createHospitalSg(jo);
        hospSgSvc.registerHospitalSg(hospital, jo.getString("accountPassword"));

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

}
