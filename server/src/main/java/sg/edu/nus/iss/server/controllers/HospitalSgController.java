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
import sg.edu.nus.iss.server.models.Statistic;
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

    @PostMapping(path = "/statistic/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateStatistic(@RequestBody String payload) throws Exception{

        JsonObject jo = Json.createReader(new StringReader(payload)).readObject();
        JsonObject jsonStat = jo.getJsonObject("statistic");

        Statistic stat = new Statistic();
        stat.setMortality(jsonStat.getInt("mortality"));
        stat.setPatientSafety(jsonStat.getInt("patientSafety"));
        stat.setReadmission(jsonStat.getInt("readmission"));
        stat.setPatientExperience(jsonStat.getInt("patientExperience"));
        stat.setEffectiveness(jsonStat.getInt("effectiveness"));
        stat.setTimeliness(jsonStat.getInt("timeliness"));
        stat.setMedicalImagingEfficiency(jsonStat.getInt("medicalImagingEfficiency"));
        // stat.setMortality((float) jsonStat.getJsonNumber("mortality").doubleValue());
        // stat.setPatientSafety((float) jsonStat.getJsonNumber("patientSafety").doubleValue());
        // stat.setReadmission((float) jsonStat.getJsonNumber("readmission").doubleValue());
        // stat.setPatientExperience((float) jsonStat.getJsonNumber("patientExperience").doubleValue());
        // stat.setEffectiveness((float) jsonStat.getJsonNumber("effectiveness").doubleValue());
        // stat.setTimeliness((float) jsonStat.getJsonNumber("timeliness").doubleValue());
        // stat.setMedicalImagingEfficiency((float) jsonStat.getJsonNumber("medicalImagingEfficiency").doubleValue());

        String accountPassword = jo.getString("accountPassword");

        hospSgSvc.updateStatistic(stat,accountPassword);

        System.out.println("in HospitalSg controller, done updateStatistic()");

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    

}
