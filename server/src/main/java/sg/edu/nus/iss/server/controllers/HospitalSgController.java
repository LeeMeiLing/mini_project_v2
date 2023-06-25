package sg.edu.nus.iss.server.controllers;

import java.io.StringReader;
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
import jakarta.json.JsonReader;
import sg.edu.nus.iss.server.exceptions.ResultNotFoundException;
import sg.edu.nus.iss.server.exceptions.UpdateContractFailedException;
import sg.edu.nus.iss.server.exceptions.VerificationFailedException;
import sg.edu.nus.iss.server.models.HospitalReview;
import sg.edu.nus.iss.server.models.HospitalReviewSummary;
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

        JsonObject result = Json.createObjectBuilder().add("statIndex", statIndex).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(result.toString());

    }

    // GET /api/hospitals/sg/statistic/{statIndex}?facilityId=123456
    @GetMapping(path = "/statistic/{statIndex}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getStatistic(@PathVariable int statIndex, @RequestParam(required = false) String facilityId){
        
        Statistic stat = hospSgSvc.getStatisticByFacilityIdAndStatIndex(facilityId, statIndex);

        return ResponseEntity.status(HttpStatus.OK).body(stat.toJson().toString());
    }

    // GET /api/hospitals/sg/pending-verify
    @GetMapping(path = "/pending-verify", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getHospitalsByPendingVerify(){
        

        List<HospitalSg> hospitals = hospSgSvc.getHospitalsByPendingVerify();

        JsonArrayBuilder arrB = Json.createArrayBuilder();
        
        if(hospitals == null){
            return  ResponseEntity.status(HttpStatus.OK).body(arrB.build().toString()); // return empty JsonArray
        }
        
        hospitals.stream().map(h -> h.toJson()).forEach(j -> arrB.add(j));

        return  ResponseEntity.status(HttpStatus.OK).body(arrB.build().toString());

    }

    // GET /api/hospitals/sg/statistic/pending-verify
    @GetMapping(path = "/statistic/pending-verify", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getHospitalsByStatPendingVerify() throws Exception{
        
        List<HospitalSg> hospitals = hospSgSvc.getHospitalsByStatPendingVerify();
      
        JsonArrayBuilder arrB = Json.createArrayBuilder();
        
        if(hospitals == null){
            return  ResponseEntity.status(HttpStatus.OK).body(arrB.build().toString()); // return empty JsonArray
        }
        
        hospitals.stream().map(h -> h.toJson()).forEach(j -> arrB.add(j));

        return ResponseEntity.status(HttpStatus.OK).body(arrB.build().toString());

    }
    
    // GET /api/hospitals/sg?hospitalOwnership=xx&name=xx&offset=0&sortByRating=true&descending=true
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getHospitalsSgList(@RequestParam(required = false) String hospitalOwnership ,@RequestParam(required = false) String name ,@RequestParam Integer offset,
        @RequestParam Boolean sortByRating ,@RequestParam Boolean descending){
        
        List<HospitalSg> hospitals = hospSgSvc.getHospitalsSgList(hospitalOwnership,name,offset,sortByRating,descending);

         if(hospitals.isEmpty()){
           throw new ResultNotFoundException("Hospital");
        }

        JsonArrayBuilder arrB = Json.createArrayBuilder();
        hospitals.stream().map(h -> h.toJson()).forEach(j -> arrB.add(j));
        JsonArray hospitalArray = arrB.build();

        JsonObjectBuilder joB = Json.createObjectBuilder();
        joB.add("results", hospitalArray);

        Integer totalResult = hospitals.size();
        joB.add("count", totalResult);

        JsonObject payload = joB.build();

        return ResponseEntity.status(HttpStatus.OK).body(payload.toString());

    }

    // GET api/hospitals/sg/hospital/{facilityId}
     @GetMapping(path ={"/hospital/{facilityId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getHospitalSgByFacilityId(@PathVariable String facilityId) throws Exception{

        HospitalSg hospital = hospSgSvc.findHospitalSgById(facilityId);

        JsonObjectBuilder joB = Json.createObjectBuilder();

        joB.add("hospital", hospital.toJson());
        joB.add("totalReview", hospSgSvc.getHospitalSgReviewCountByFacilityId(facilityId));
        Integer latestVerifiedStat = hospSgSvc.getLatestVerifiedStatIndex(facilityId);
        if(latestVerifiedStat >= 0){
            joB.add("latestStatIndex", latestVerifiedStat);
        }

        JsonObject payload = joB.build();

        return ResponseEntity.status(HttpStatus.OK).body(payload.toString());
    }

     /*
     * // POST /api/hospitals/sg/hospital/{facilityId}/review
     */
    @PostMapping(path ={"/hospital/{facilityId}/review"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postHospitalReview(@PathVariable String facilityId, @RequestBody HospitalReview hospitalReview)
    throws Exception{

        boolean posted = hospSgSvc.postHospitalReview(facilityId, hospitalReview);

        JsonObject payload =  Json.createObjectBuilder().add("posted", posted).build();

        return ResponseEntity.status(HttpStatus.OK).body(payload.toString());
    }


     /*
     * GET /api/hospitals/sg/hospital/{facilityId}/review
     *  this.reviews = r['reviews'];
        this.totalReview = r['totalReview'];
        this.reviewSummary = r['reviewSummary'];
     */
    @GetMapping(path ={"/hospital/{facilityId}/review"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getHospitalReviews(@PathVariable String facilityId){

        Integer totalReview = hospSgSvc.getHospitalSgReviewCountByFacilityId(facilityId);

        List<HospitalReview> reviews = null;
        HospitalReviewSummary reviewSummary;

        if(totalReview > 0){

            reviews = hospSgSvc.getHospitalReviews(facilityId);
            reviewSummary =  hospSgSvc.getHospitalSgReviewSummary(facilityId);

        }else{
            throw new ResultNotFoundException("Reviews");
        }

        JsonObjectBuilder joB = Json.createObjectBuilder();
        joB.add("totalReview", totalReview);

        JsonArrayBuilder reviewArrBuilder = Json.createArrayBuilder();
        reviews.stream().map(r -> r.toJson()).forEach(j -> reviewArrBuilder.add(j));
        joB.add("reviews", reviewArrBuilder.build());

        joB.add("reviewSummary", reviewSummary.toJson());

        JsonObject payload = joB.build();
        
        return ResponseEntity.status(HttpStatus.OK).body(payload.toString());

    }

    // POST /api/hospitals/sg/hospital/{facilityId}/verify-credentials
    @PostMapping(path ={"/hospital/{facilityId}/verify-credentials"}, consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> verifyCredentials(@PathVariable String facilityId, @RequestBody String payload) throws Exception {

        JsonObject jo = Json.createReader(new StringReader(payload)).readObject();
        String accountPassword = jo.getString("accountPassword");
        String toVerify = jo.getString("toVerify");

        if(toVerify.equalsIgnoreCase("license")){
            boolean verified = hospSgSvc.verifyLicense(facilityId, accountPassword);

            if(verified){
                return ResponseEntity.status(HttpStatus.OK).build();
            }else{
                throw new VerificationFailedException("Failed to verify hospital credentials");
            }
        }else if(toVerify.equalsIgnoreCase("jci")){

            boolean verified = hospSgSvc.verifyJci(facilityId, accountPassword);

            if(verified){
                return ResponseEntity.status(HttpStatus.OK).build();
            }else{
                throw new VerificationFailedException("Failed to verify JCI accreditation");
            }
        }

        throw new VerificationFailedException("Verification failed");

    }

    // POST /api/hospitals/sg/hospital/{facilityId}/update-frequency-penalty
    //   'accountPassword': accountPassword,
    //   'updateFrequency': updateFrequency,
    //   'penalty': penalty,
    @PostMapping(path ={"/hospital/{facilityId}/update-frequency-penalty"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setUpdateFrequencyPenalty(@PathVariable String facilityId, @RequestBody String payload) throws Exception {

        JsonObject jo = Json.createReader(new StringReader(payload)).readObject();
        String accountPassword = jo.getString("accountPassword");
        String updateFrequency = jo.getString("updateFrequency");
        String penalty = jo.getString("penalty");

        boolean updated = hospSgSvc.setUpdateFrequencyandPenalty(facilityId, updateFrequency, penalty, accountPassword);

        if(updated){
            return ResponseEntity.status(HttpStatus.OK).build();
        }else{
            throw new UpdateContractFailedException("Failed to verify JCI accreditation");
        }
        
    }
  

    // GET /api/hospitals/sg/hospital/{facilityId}/update-frequency-penalty
        // this.currentUpdateFrequency = r['currentUpdateFrequency'];
        // this.currentPenalty = r['currentPenalty'];
    @GetMapping(path ={"/hospital/{facilityId}/update-frequency-penalty"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUpdateFrequencyPenalty(@PathVariable String facilityId){

        try {
            Integer updateFrequency = hospSgSvc.getUpdateFrequency(facilityId);
            Integer penalty = hospSgSvc.getPenalty(facilityId);

            JsonObject payload = Json.createObjectBuilder()
                .add("currentUpdateFrequency", String.valueOf(updateFrequency))
                .add("currentPenalty", String.valueOf(penalty/1000000000))  // return penalty amount in Gwei
                .build();

            return ResponseEntity.status(HttpStatus.OK).body(payload.toString());

        } catch (Exception e) {
            throw new ResultNotFoundException("Failed to get update frequency and penalty from contract");
        }

    }

    // GET /api/hospitals/sg/statistic-list/{facilityId}
    // return statisctic[]
    @GetMapping(path ={"/statistic-list/{facilityId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getStatisticListByHospital(@PathVariable String facilityId){

        try {

            List<Statistic> statistics = hospSgSvc.getStatisticListByHospital(facilityId);
            
            JsonArrayBuilder statArrBuilder = Json.createArrayBuilder();
            statistics.stream().map(s-> s.toJson()).forEach(j-> statArrBuilder.add(j));
            JsonArray payload = statArrBuilder.build();

            return ResponseEntity.status(HttpStatus.OK).body(payload.toString()); // return empty [] if no stat

        } catch (Exception e) {
            throw new ResultNotFoundException("Statistic ");
        }

    }


    // POST /api/hospitals/sg/hospital/{facilityId}/statistic/{statIndex}
    // payload {accountPassword}
    @PostMapping(path ={"/hospital/{facilityId}/statistic/{statIndex}"}, consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> verifyStatistic(@PathVariable String facilityId, @PathVariable Integer statIndex, @RequestBody String payload) throws Exception{

        JsonObject jo = Json.createReader(new StringReader(payload)).readObject();
        String accountPassword = jo.getString("accountPassword");

        boolean verified = hospSgSvc.verifyStatistic(facilityId, statIndex ,accountPassword);

        if(verified){
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        throw new VerificationFailedException("Failed to verify statistic");

    }

}
