package sg.edu.nus.iss.server.services;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.server.constants.Constant;
import sg.edu.nus.iss.server.models.Hospital;
import sg.edu.nus.iss.server.repositories.HospitalRepository;

@Service
public class HospitalService {

    private Logger logger = Logger.getLogger(HospitalService.class.getName());

    @Autowired
    private HospitalRepository hospitalRepo;

    private Date previousReleased;
    private Date latestReleased;
    private Integer totalHospital;
    private final Integer QUERY_LIMIT = 500;

    // Check if there is any new released data. if there is, get the latest released data from API 
    public void checkUpdated(){

        previousReleased = latestReleased; 
        System.out.println("Previous Released: " + previousReleased); // debug

        RequestEntity<Void> req = RequestEntity.get(Constant.US_DATA_GOV_HOSPITAL_GENERAL_INFORMATION_METASTORE_URL).build();

        RestTemplate template = new RestTemplate();

        String payload;

        try{
            ResponseEntity<String> resp = template.exchange(req,String.class);
            payload = resp.getBody();
            JsonObject jo = Json.createReader(new StringReader(payload)).readObject();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            latestReleased = formatter.parse(jo.getString("released"));
            System.out.println("Latest Released: " + latestReleased); // debug

            if(!latestReleased.equals(previousReleased) ){
                getTotalHospital();
                System.out.println(">>> total Hospital = " + totalHospital); // debug
                int loop = (int) Math.ceil((double)totalHospital / QUERY_LIMIT);
                System.out.println("loop # " + loop); // debug
                for(int i=0 ; i < loop ; i++){
                    getHospitalInfo(i*QUERY_LIMIT);
                }
            }

        }catch(HttpClientErrorException ex){
            logger.info(ex.getResponseBodyAsString());
 
        }catch(Exception ex){
            logger.info(ex.getMessage());

        }
    }

    public void getTotalHospital(){

        // GET https://data.cms.gov/provider-data/api/1/datastore/query/xubh-q36u/{index}
        String url = UriComponentsBuilder.fromUriString(Constant.US_DATA_GOV_HOSPITAL_GENERAL_INFORMATION_URL)
        .pathSegment("0")
        .queryParam("limit", 1)
        .queryParam("offset", 0)
        .toUriString();

        RequestEntity<Void> req = RequestEntity.get(url).build();

        RestTemplate template = new RestTemplate();

        String payload;

        try{
            ResponseEntity<String> resp = template.exchange(req,String.class);
            payload = resp.getBody();
            JsonObject jo = Json.createReader(new StringReader(payload)).readObject();
            totalHospital = jo.getInt("count");
 
        }catch(HttpClientErrorException ex){
            logger.info(ex.getResponseBodyAsString());
 
        }catch(Exception ex){
            logger.info(ex.getMessage());

        }
    }

    // Get Hospitals General Information from API & save to MySQL
    public void getHospitalInfo(Integer offset){

        // GET https://data.cms.gov/provider-data/api/1/datastore/query/xubh-q36u/{index}
        String url = UriComponentsBuilder.fromUriString(Constant.US_DATA_GOV_HOSPITAL_GENERAL_INFORMATION_URL)
        .pathSegment("0")
        .queryParam("limit", QUERY_LIMIT)
        .queryParam("offset", offset)
        .toUriString();

        RequestEntity<Void> req = RequestEntity.get(url).build();

        RestTemplate template = new RestTemplate();

        String payload;

        try{
            ResponseEntity<String> resp = template.exchange(req,String.class);
            payload = resp.getBody();
            JsonArray results = Json.createReader(new StringReader(payload)).readObject().getJsonArray("results");
            List<Hospital> hospitals = results.stream().map(j -> Hospital.createHospital((JsonObject) j)).toList();
            // System.out.println(">>> hospital list: " + hospitals); // debug
            // save to MySQL database
            hospitals.stream().forEach(h -> hospitalRepo.insert(h));

        }catch(HttpClientErrorException ex){
            logger.info(ex.getResponseBodyAsString());


        }catch(Exception ex){
            logger.info(ex.getMessage());
        }

    }
    
    // retrieve from MySQL
    public List<Hospital> getHospitalList(String name, Integer limit){
        
        Optional<List<Hospital>> opt = hospitalRepo.getHospitalList(name, limit);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new RuntimeException(); // TODO
    }

}
