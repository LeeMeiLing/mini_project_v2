package sg.edu.nus.iss.server.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.server.models.Hospital;

@Repository
public class HospitalRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private final Integer LIMIT = 20;

    private final String INSERT_HOSPITAL = """
            insert into hospitals (facility_id, facility_name, address, city, state, zip_code, county_name,
            phone_number, hospital_type, hospital_ownership, emergency_services, hospital_overall_rating ) 
            values (?,?,?,?,?,?,?,?,?,?,?,?)
        """;
    
    private final String UPDATE_HOSPITAL = """
        update hospitals set facility_name = ? , address = ? , city = ? , state = ? , zip_code = ? , 
        county_name = ? , phone_number = ? , hospital_type = ? , hospital_ownership = ? , emergency_services = ? ,
        hospital_overall_rating = ? where facility_id = ?
    """;

    private final String QUERY_ALL_STATES = """
        select distinct state from hospitals order by state
    """;

    private final String QUERY_CITIES = """
        select distinct city from hospitals where state = ? order by city
    """;

    // GET /api/hospitals/search
    private final String QUERY_HOSPITALS_ALL = """
        select * from hospitals limit ? offset ?
    """;

    // GET /api/hospitals/search?name=name
    private final String QUERY_HOSPITALS_BY_NAME = """
        select * from hospitals where facility_name like ? limit ? offset ?
    """;

    // GET /api/hospitals/search/{state}
    private final String QUERY_HOSPITALS_BY_STATE = """
        select * from hospitals where state = ? limit ? offset ?
    """;

    // GET /api/hospitals/search/{state}?name=name
    private final String QUERY_HOSPITALS_BY_STATE_NAME = """
        select * from hospitals where state = 'AL' and facility_name like ? limit ? offset ?
    """;

    // GET /api/hospitals/search/{state}/{city}
    private final String QUERY_HOSPITALS_BY_STATE_CITY = """
        select * from hospitals where state = ? and city = ? limit ? offset ?
    """;

    // GET /api/hospitals/search/{state}/{city}?name=name
    private final String QUERY_HOSPITALS_BY_STATE_CITY_NAME = """
        select * from hospitals where state = ? and city = ? and facility_name like ? limit ? offset ?
    """;

    public boolean insert(Hospital h){
        
        try{
            jdbcTemplate.update(INSERT_HOSPITAL, new PreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, h.getFacilityId());
                    ps.setString(2, h.getFacilityName());
                    ps.setString(3, h.getAddress());
                    ps.setString(4, h.getCity());
                    ps.setString(5, h.getState());
                    ps.setString(6, h.getZipCode());
                    ps.setString(7, h.getCountyName());
                    ps.setString(8, h.getPhoneNumber());
                    ps.setString(9, h.getHospitalType());
                    ps.setString(10, h.getHospitalOwnership());
                    ps.setString(11, h.getEmergencyServices());
                    ps.setString(12, h.getHospitalOverallRating());
                 
                }
                
            });

            return true;
        }catch(DuplicateKeyException ex){
            // System.out.println("in duplicate key exception catch: ");
            update(h);
            return true;
    
        }catch(Exception ex){
            System.out.println("in catch: " + ex);
            return false;
        }
    }


    public boolean update(Hospital h){
        
        try{
            jdbcTemplate.update(UPDATE_HOSPITAL, new PreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    
                    ps.setString(1, h.getFacilityName());
                    ps.setString(2, h.getAddress());
                    ps.setString(3, h.getCity());
                    ps.setString(4, h.getState());
                    ps.setString(5, h.getZipCode());
                    ps.setString(6, h.getCountyName());
                    ps.setString(7, h.getPhoneNumber());
                    ps.setString(8, h.getHospitalType());
                    ps.setString(9, h.getHospitalOwnership());
                    ps.setString(10, h.getEmergencyServices());
                    ps.setString(11, h.getHospitalOverallRating());
                    ps.setString(12, h.getFacilityId());
                 
                }
                
            });

            return true;
    
        }catch(Exception ex){
            System.out.println("in update catch: " + ex);
            return false;
        }
    }
    
    public Optional<List<Hospital>> findAllHospitals(Integer offset){
        
        try{

            List<Hospital> hospitals = jdbcTemplate.query(QUERY_HOSPITALS_ALL, new PreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, LIMIT);
                    ps.setInt(2, offset * LIMIT);
                }
                
            }, BeanPropertyRowMapper.newInstance(Hospital.class));

            if(!hospitals.isEmpty()){
                System.out.println("in repo: " + hospitals);
                return Optional.of(hospitals);
            }else{
                System.out.println("in repo: hospital is empty list " );
                return Optional.empty();
            }

        }catch(Exception ex){
            return Optional.empty();
        }

    }

    public Optional<List<Hospital>> findHospitalsByName(String name, Integer offset){
        
        try{

            List<Hospital> hospitals = jdbcTemplate.query(QUERY_HOSPITALS_BY_NAME, new PreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, name);
                    ps.setInt(2, LIMIT);
                    ps.setInt(3, offset * LIMIT);
                }
                
            }, BeanPropertyRowMapper.newInstance(Hospital.class));

            if(!hospitals.isEmpty()){
                return Optional.of(hospitals);
            }else{
                return Optional.empty();
            }

        }catch(Exception ex){
            return Optional.empty();
        }

    }


    public Optional<List<String>> getStates(){

        System.out.println(">>> in hosp Repo get State");

        try{
            List<String> states = jdbcTemplate.queryForList(QUERY_ALL_STATES,String.class);

            if(states != null){
                System.out.println(">>> in repo, states: " + states); // debug
                return Optional.of(states);
            }else{
                System.out.println(">>> in repo, states: query return null"); // debug
                return Optional.empty();
            }

        }catch(Exception ex){
            System.out.println(">>> in repo, catch: " + ex); // debug
            return Optional.empty();
        }

    }

    public Optional<List<String>> getCities(String state){

        System.out.println(">>> in hosp Repo get cities");

        try{
            List<String> cities = jdbcTemplate.queryForList(QUERY_CITIES,String.class, state);

            if(cities != null){
                System.out.println(">>> in repo, cities: " + cities); // debug
                return Optional.of(cities);
            }else{
                System.out.println(">>> in repo, cities: query return null"); // debug
                return Optional.empty();
            }

        }catch(Exception ex){
            System.out.println(">>> in repo, catch: " + ex); // debug
            return Optional.empty();
        }


    }
}
