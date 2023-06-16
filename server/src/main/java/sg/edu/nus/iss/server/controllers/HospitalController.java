package sg.edu.nus.iss.server.controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.edu.nus.iss.server.models.Hospital;
import sg.edu.nus.iss.server.models.HospitalReview;
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

        // hospSvc.checkUpdated();

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
    

    // GET /api/hospitals/search?offset=0&sortByRating=true&descending=true
    // GET /api/hospitals/search?name=name&offset=0&sortByRating=true&descending=true
    // GET /api/hospitals/search/{state}?offset=0&sortByRating=true&descending=true
    // GET /api/hospitals/search/{state}?name=name&offset=0&sortByRating=true&descending=true
    // GET /api/hospitals/search/{state}/{city}?offset=0&sortByRating=true&descending=true
    // GET /api/hospitals/search/{state}/{city}?name=name&offset=0&sortByRating=true&descending=true

    @GetMapping(path ={"/search", "/search/{state}", "search/{state}/{city}"},
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getHospitals(@PathVariable(required = false) String state,@PathVariable(required = false) String city ,
        @RequestParam(required = false) String name ,@RequestParam Integer offset,
        @RequestParam Boolean sortByRating ,@RequestParam Boolean descending)
    {
        // hospSvc.checkUpdated();

        System.out.println(">>> in controller search Hospitals"); // dbeug

        

        List<Hospital> hospitals = hospSvc.getHospitalList(state,city,name,offset,sortByRating,descending);

        if(hospitals.isEmpty()){
            System.out.println("hospital list is empty, result not found");
            hospSvc.countResult(state,city,name,sortByRating,descending);
            // throw not found exception ? todo!!!!
        }

        JsonArrayBuilder arrB = Json.createArrayBuilder();
        hospitals.stream().map(h -> h.toJson()).forEach(j -> arrB.add(j));
        JsonArray hospitalArray = arrB.build();

        JsonObjectBuilder joB = Json.createObjectBuilder();
        joB.add("results", hospitalArray);

        Integer totalResult = hospSvc.countResult(state,city,name,sortByRating,descending);
        joB.add("count", totalResult);

        JsonObject payload = joB.build();

        return ResponseEntity.status(HttpStatus.OK).body(payload.toString());

    }

    /*
     * GET /api/hospitals/hospital/{facilityId}
     */
    @GetMapping(path ={"/hospital/{facilityId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getHospital(@PathVariable String facilityId){

        Hospital hospital = hospSvc.findHospitalById(facilityId);

        JsonObjectBuilder joB = Json.createObjectBuilder();

        joB.add("hospital", hospital.toJson());
        joB.add("totalReview", 3);
        JsonObject payload = joB.build();

        System.out.println("in controller getHospital: " + payload.toString());

        return ResponseEntity.status(HttpStatus.OK).body(payload.toString());
    }

    /*
     * // POST /api/hospitals/hospital/{facilityId}/review
     */
    @PostMapping(path ={"/hospital/{facilityId}/review"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postHospitalReview(@PathVariable String facilityId, @RequestBody HospitalReview hospitalReview)
    throws Exception{

        System.out.println(">> in controller, postHospitalreview(), hospitalReview: " + hospitalReview);

        hospSvc.postHospitalReview(facilityId, hospitalReview);
        
        return null;
    }

}
