package sg.edu.nus.iss.server.services;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private Date previousReleased;
    private Date latestReleased;
    private Integer totalHospital;
    private final Integer API_QUERY_LIMIT = 500;

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
                int loop = (int) Math.ceil((double)totalHospital / API_QUERY_LIMIT);
                System.out.println("loop # " + loop); // debug
                for(int i=0 ; i < loop ; i++){
                    getHospitalInfo(i*API_QUERY_LIMIT);
                }
            }

        }catch(HttpClientErrorException ex){
            System.out.println(" >>> in HttpClientErrorException"); // debug
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
        .queryParam("limit", API_QUERY_LIMIT)
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
    

    // retrieve states
    public List<String> getStates(){

        System.out.println(">>> in hosp Svc get State");

        List<Object> objects = new ArrayList<>();
        
        objects = redisTemplate.opsForList().range("states", 0, redisTemplate.opsForList().size("states"));

        if(!objects.isEmpty()){
            
            System.out.println("in if");
            System.out.println(objects);
            return objects.stream().map(o -> String.valueOf(o)).toList();

        }else{

            System.out.println("in else");

            // get from MySQL
            Optional<List<String>> opt = hospitalRepo.getStates();

            if(opt.isPresent()){

                // save to redis
                List<String> states = opt.get();
                states.stream().forEach(s -> redisTemplate.opsForList().rightPush("states", s));

                return opt.get();
            }else{
                throw new RuntimeException(); // TODO
            }
        }

    }

    // retrieve cities
    public List<String> getCities(String state){

        System.out.println(">>> in hosp Svc get Cities");

        List<Object> objects = new ArrayList<>();
        
        objects = redisTemplate.opsForList().range(state, 0, redisTemplate.opsForList().size(state));

        if(!objects.isEmpty()){
            
            System.out.println("in if");
            System.out.println(objects);
            return objects.stream().map(o -> String.valueOf(o)).toList();

        }else{

            System.out.println("in else");

            // get from MySQL
            Optional<List<String>> opt = hospitalRepo.getCities(state);

            if(opt.isPresent()){

                // save to redis
                List<String> cities = opt.get();
                cities.stream().forEach(c -> redisTemplate.opsForList().rightPush(state, c));

                return opt.get();
            }else{
                throw new RuntimeException(); // TODO
            }
        }

    }

    // retrieve from MySQL
    public List<Hospital> getHospitalList(String state, String city, String name, Integer offset,
    Boolean sortByRating, Boolean descending)
    {

        Optional<List<Hospital>> opt;

        if(null != state){

            if(city != null){
                if(name != null){
                    // search with state, city, name
                    opt = hospitalRepo.findHospitalsByStateCityName(state,city,name,offset,sortByRating,descending);
                }else{
                    // search with state, city
                    opt = hospitalRepo.findHospitalsByStateAndCity(state, city, offset,sortByRating,descending);
                }
            }else{
                if(name != null){
                    // search with state, name
                    System.out.println(">> in state !city name svc");
                    opt = hospitalRepo.findHospitalsByStateAndName(state, name, offset,sortByRating,descending);
                }else{
                    // search with state
                    opt = hospitalRepo.findHospitalsByState(state, offset,sortByRating,descending);
                }
            }
        }else{

            if(name != null){
                // search with name
                opt = hospitalRepo.findHospitalsByName(name, offset,sortByRating,descending);
            }else{
                // search all without filter
                opt = hospitalRepo.findAllHospitals(offset,sortByRating,descending);
            }
        }

        if(opt.isPresent()){
            return opt.get();
        }else{
            throw new RuntimeException(); // TODO
        }
        
    }


}
