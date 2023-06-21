package sg.edu.nus.iss.server.controllers;

import java.io.StringReader;

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
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.server.models.HospitalSg;
import sg.edu.nus.iss.server.models.Statistic;
import sg.edu.nus.iss.server.services.HospitalSgService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/hospitals/sg")
public class HospitalSgController {

    @Autowired
    private HospitalSgService hospSgSvc;
    
    @PostMapping(path = "/register/hospital", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerHospitalSg(@RequestBody String payload) throws Exception{

        JsonReader rd = Json.createReader(new StringReader(payload));
        JsonObject jo = rd.readObject();

        HospitalSg hospital = HospitalSg.createHospitalSg(jo);
        hospSgSvc.registerHospitalSg(hospital, jo.getString("accountPassword"));

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PostMapping(path = "/statistic/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateStatistic(@RequestBody String payload) throws Exception{

        JsonObject jo = Json.createReader(new StringReader(payload)).readObject();
        JsonObject jsonStat = jo.getJsonObject("statistic");

        Statistic stat = new Statistic();
        stat.setMortality(jsonStat.getJsonNumber("mortality").doubleValue());
        stat.setPatientSafety(jsonStat.getJsonNumber("patientSafety").doubleValue());
        stat.setReadmission(jsonStat.getJsonNumber("readmission").doubleValue());
        stat.setPatientExperience(jsonStat.getJsonNumber("patientExperience").doubleValue());
        stat.setEffectiveness(jsonStat.getJsonNumber("effectiveness").doubleValue());
        stat.setTimeliness(jsonStat.getJsonNumber("timeliness").doubleValue());
        stat.setMedicalImagingEfficiency(jsonStat.getJsonNumber("medicalImagingEfficiency").doubleValue());

        String accountPassword = jo.getString("accountPassword");

        Integer statIndex = hospSgSvc.updateStatistic(stat,accountPassword);

        System.out.println("in HospitalSg controller, done updateStatistic()");

        JsonObject result = Json.createObjectBuilder().add("statIndex", statIndex).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(result.toString());

    }

    // GET /api/hospitals/sg/statistic/{statIndex}?facilityId=123456
    @GetMapping(path = "/statistic/{statIndex}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getStatistic(@PathVariable int statIndex, @RequestParam(required = false) String facilityId){
        
        Statistic stat = hospSgSvc.getStatisticByFacilityIdAndStatIndex(facilityId, statIndex);

        return ResponseEntity.status(HttpStatus.OK).body(stat.toJson().toString());
    }



    

}
