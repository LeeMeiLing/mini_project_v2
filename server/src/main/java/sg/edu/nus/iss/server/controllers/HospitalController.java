package sg.edu.nus.iss.server.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import sg.edu.nus.iss.server.services.HospitalService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {

    @Autowired
    private HospitalService hospSvc;

    /*
     * GET /api/hospitals/states
     */
    @GetMapping(path="/states", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getStates(){

        System.out.println(">>> in controller getStates()");

        List<String> states = hospSvc.getStates();
        
        JsonArrayBuilder arrB = Json.createArrayBuilder();
        states.stream().forEach(s -> arrB.add(s));
        JsonArray stateArray = arrB.build();
        
        return ResponseEntity.status(HttpStatus.OK).body(stateArray.toString());

    }

    /*
     * GET /api/hospitals/{state}/cities
     */
    @GetMapping(path="/{state}/cities", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCities(@PathVariable String state){
        
        System.out.println(">>> in controller getCities()");

        List<String> cities = hospSvc.getCities(state);
        
        JsonArrayBuilder arrB = Json.createArrayBuilder();
        cities.stream().forEach(s -> arrB.add(s));
        JsonArray cityArray = arrB.build();
        
        return ResponseEntity.status(HttpStatus.OK).body(cityArray.toString());

    }
    

    // GET /api/hospitals/search?offset=0
    // GET /api/hospitals/search?name=name&offset=0
    // GET /api/hospitals/search/{state}?offset=0
    // GET /api/hospitals/search/{state}?name=name&offset=0
    // GET /api/hospitals/search/{state}/{city}?offset=0
    // GET /api/hospitals/search/{state}/{city}?name=name&offset=0

    @GetMapping(path ={"/search", "/search/{state}", "search/{state}/{city}"},
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getHospitals(@PathVariable(required = false) String state,@PathVariable(required = false) String city ,
        @RequestParam(required = false) String name ,@RequestParam Integer offset)
    {
        // hospSvc.checkUpdated();
        System.out.println(">>> in controller search Hospitals");
        System.out.println(hospSvc.getHospitalList(state,city,name,offset)); // debug
        // hospSvc.getHospitalList(state,city,name,offset);
        
        return null;
    }
}
