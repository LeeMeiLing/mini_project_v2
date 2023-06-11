package sg.edu.nus.iss.server.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.server.constants.SqlQueryConstant;
import sg.edu.nus.iss.server.models.Hospital;
import sg.edu.nus.iss.server.models.HospitalReview;

@Repository
public class HospitalRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    // private final Integer LIMIT = 20;

    // private final String INSERT_HOSPITAL = """
    //         insert into hospitals (facility_id, facility_name, address, city, state, zip_code, county_name,
    //         phone_number, hospital_type, hospital_ownership, emergency_services, hospital_overall_rating ) 
    //         values (?,?,?,?,?,?,?,?,?,?,?,?)
    //     """;
    
    // private final String UPDATE_HOSPITAL = """
    //     update hospitals set facility_name = ? , address = ? , city = ? , state = ? , zip_code = ? , 
    //     county_name = ? , phone_number = ? , hospital_type = ? , hospital_ownership = ? , emergency_services = ? ,
    //     hospital_overall_rating = ? where facility_id = ?
    // """;

    // private final String QUERY_ALL_STATES = """
    //     select distinct state from hospitals order by state
    // """;

    // private final String QUERY_CITIES = """
    //     select distinct city from hospitals where state = ? order by city
    // """;


    // private final String COUNT = "select count(*) ";
    // private final String SELECT = "select * ";

    // // GET /api/hospitals/search
    // private final String QUERY_HOSPITALS_ALL = """
    //     from hospitals 
    // """;

    // // GET /api/hospitals/search?name=name
    // private final String QUERY_HOSPITALS_BY_NAME = """
    //     from hospitals where facility_name like ? 
    // """;

    // // GET /api/hospitals/search/{state}
    // private final String QUERY_HOSPITALS_BY_STATE = """
    //     from hospitals where state = ? 
    // """;

    // // GET /api/hospitals/search/{state}?name=name
    // private final String QUERY_HOSPITALS_BY_STATE_NAME = """
    //     from hospitals where state = ? and facility_name like ? 
    // """;

    // // GET /api/hospitals/search/{state}/{city}
    // private final String QUERY_HOSPITALS_BY_STATE_CITY = """
    //     from hospitals where state = ? and city = ? 
    // """;

    // // GET /api/hospitals/search/{state}/{city}?name=name
    // private final String QUERY_HOSPITALS_BY_STATE_CITY_NAME = """
    //     from hospitals where state = ? and city = ? and facility_name like ? 
    // """;

    // private final String LIMIT_OFFSET = """
    //     limit ? offset ?
    // """;

    // private final String SORT_ASC = """
    //     and hospital_overall_rating in ('1','2','3','4','5') order by hospital_overall_rating 
    // """;

    // private final String SORT_DESC = """
    //     and hospital_overall_rating in ('1','2','3','4','5') order by hospital_overall_rating desc 
    // """;

    public boolean insert(Hospital h){
        
        try{
            jdbcTemplate.update(SqlQueryConstant.INSERT_HOSPITAL, new PreparedStatementSetter() {

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
            jdbcTemplate.update(SqlQueryConstant.UPDATE_HOSPITAL, new PreparedStatementSetter() {

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

    public Optional<List<Hospital>> findAllHospitals(Integer offset, Boolean sortByRating, Boolean descending){

        PreparedStatementSetter ps = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                
                ps.setInt(1, SqlQueryConstant.LIMIT);
                ps.setInt(2, offset * SqlQueryConstant.LIMIT);
            }
            
        };


        String queryString = null;

        if(sortByRating && descending){
            queryString =  SqlQueryConstant.QUERY_HOSPITALS_ALL + "where " + SqlQueryConstant.SORT_DESC;
        }

        else if(sortByRating && !descending){
            queryString =  SqlQueryConstant.QUERY_HOSPITALS_ALL + "where " + SqlQueryConstant.SORT_ASC;
        }
        else if(!sortByRating){
            queryString =  SqlQueryConstant.QUERY_HOSPITALS_ALL;
        }
        
        return queryHospitals(queryString, ps, false, false);

    }

    public Optional<List<Hospital>> findHospitalsByName(String name, Integer offset, Boolean sortByRating, Boolean descending){

        PreparedStatementSetter ps = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {

                ps.setString(1, "%" + name + "%");
                ps.setInt(2, SqlQueryConstant.LIMIT);
                ps.setInt(3, offset * SqlQueryConstant.LIMIT);
            }
            
        };
        
        return queryHospitals(SqlQueryConstant.QUERY_HOSPITALS_BY_NAME, ps, sortByRating, descending);

    }

    public Optional<List<Hospital>> findHospitalsByState(String state, Integer offset, Boolean sortByRating, Boolean descending){

        PreparedStatementSetter ps = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                
                ps.setString(1, state);
                ps.setInt(2, SqlQueryConstant.LIMIT);
                ps.setInt(3, offset * SqlQueryConstant.LIMIT);
            }
            
        };
        
        return queryHospitals(SqlQueryConstant.QUERY_HOSPITALS_BY_STATE, ps, sortByRating, descending);

    }

    public Optional<List<Hospital>> findHospitalsByStateAndName(String state, String name, Integer offset, Boolean sortByRating, Boolean descending){

        System.out.println(">> in findHospitalsByStateAndName");


        PreparedStatementSetter ps = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                
                ps.setString(1, state);
                ps.setString(2, "%" + name + "%");
                ps.setInt(3, SqlQueryConstant.LIMIT);
                ps.setInt(4, offset * SqlQueryConstant.LIMIT);
            }
            
        };
        
        return queryHospitals(SqlQueryConstant.QUERY_HOSPITALS_BY_STATE_NAME, ps, sortByRating, descending);

    }

    public Optional<List<Hospital>> findHospitalsByStateAndCity(String state, String city, Integer offset, Boolean sortByRating, Boolean descending){

        PreparedStatementSetter ps = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {

                ps.setString(1, state);
                ps.setString(2, city);
                ps.setInt(3, SqlQueryConstant.LIMIT);
                ps.setInt(4, offset * SqlQueryConstant.LIMIT);
            }
            
        };
        
        return queryHospitals(SqlQueryConstant.QUERY_HOSPITALS_BY_STATE_CITY, ps, sortByRating, descending);

    }

    public Optional<List<Hospital>> findHospitalsByStateCityName(String state, String city, String name, Integer offset, Boolean sortByRating, Boolean descending){

        PreparedStatementSetter ps = new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {

                ps.setString(1, state);
                ps.setString(2, city);
                ps.setString(3, "%" + name + "%");              
                ps.setInt(4, SqlQueryConstant.LIMIT);
                ps.setInt(5, offset * SqlQueryConstant.LIMIT);
            }
            
        };
        
        return queryHospitals(SqlQueryConstant.QUERY_HOSPITALS_BY_STATE_CITY_NAME, ps, sortByRating, descending);

    }

    public Optional<List<Hospital>> queryHospitals(String queryString, PreparedStatementSetter ps, Boolean sortByRating, Boolean descending){

        String finalQueryString = null;

        if(sortByRating && descending){
            finalQueryString = SqlQueryConstant.SELECT + queryString + SqlQueryConstant.SORT_DESC + SqlQueryConstant.LIMIT_OFFSET;
        }

        else if(sortByRating && !descending){
            finalQueryString = SqlQueryConstant.SELECT + queryString + SqlQueryConstant.SORT_ASC + SqlQueryConstant.LIMIT_OFFSET;
        }

        else if(!sortByRating){
            finalQueryString = SqlQueryConstant.SELECT + queryString + SqlQueryConstant.LIMIT_OFFSET; 
        }

        try{
            List<Hospital> hospitals = jdbcTemplate.query(finalQueryString, ps, BeanPropertyRowMapper.newInstance(Hospital.class));
            if(!hospitals.isEmpty()){
                System.out.println(" in queryHospitals: " + hospitals);
                return Optional.of(hospitals);
            }else{
                System.out.println(" in queryHospitals, hospitals is empty list: " + hospitals);
                return Optional.empty();
            }
            
        }catch(Exception ex){
            System.out.println(" in queryHospitals,catch exception: " + ex);
            return Optional.empty();
        }
    }

    public Integer countResult(String state, String city, String name, Boolean sortByRating, Boolean descending){

        String queryString = null;
        PreparedStatementSetter ps = null;

        if(state != null){

            if(city != null){
                if(name != null){
                    // search with state, city, name
                    ps = new PreparedStatementSetter() {

                        @Override
                        public void setValues(PreparedStatement ps) throws SQLException {

                            ps.setString(1, state);
                            ps.setString(2, city);
                            ps.setString(3, "%" + name + "%");              
                        }
                        
                    };

                    queryString = SqlQueryConstant.COUNT + SqlQueryConstant.QUERY_HOSPITALS_BY_STATE_CITY_NAME;
                    
                }else{
                    // search with state, city
                    ps = new PreparedStatementSetter() {

                        @Override
                        public void setValues(PreparedStatement ps) throws SQLException {

                            ps.setString(1, state);
                            ps.setString(2, city);
          
                        }
                        
                    };

                    queryString = SqlQueryConstant.COUNT + SqlQueryConstant.QUERY_HOSPITALS_BY_STATE_CITY;

                }
            }else{
                if(name != null){
                    // search with state, name
                    ps = new PreparedStatementSetter() {

                        @Override
                        public void setValues(PreparedStatement ps) throws SQLException {

                            ps.setString(1, state);
                            ps.setString(2, "%" + name + "%");              
                        }
                        
                    };

                    queryString = SqlQueryConstant.COUNT + SqlQueryConstant.QUERY_HOSPITALS_BY_STATE_NAME;

                }else{
                    // search with state
                    ps = new PreparedStatementSetter() {

                        @Override
                        public void setValues(PreparedStatement ps) throws SQLException {
                            ps.setString(1, state);
                        }
                        
                    };

                    queryString = SqlQueryConstant.COUNT + SqlQueryConstant.QUERY_HOSPITALS_BY_STATE;

                    System.out.println("in search with state: " + queryString);

                }
            }
        }else{

            if(name != null){
                // search with name
                ps = new PreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, "%" + name + "%");              
                    }
                    
                };

                queryString = SqlQueryConstant.COUNT + SqlQueryConstant.QUERY_HOSPITALS_BY_NAME;

            }else{
                // search all without filter
                queryString = SqlQueryConstant.COUNT + SqlQueryConstant.QUERY_HOSPITALS_ALL;

                if(sortByRating && descending){
                    queryString =  queryString  + "where " + SqlQueryConstant.SORT_DESC;
                }
        
                else if(sortByRating && !descending){
                    queryString =  queryString +  "where " + SqlQueryConstant.SORT_ASC;
                }

                sortByRating = false;
                descending = false;
            }

        }

        if(sortByRating && descending){
            queryString = queryString + "and " + SqlQueryConstant.SORT_DESC;
        }

        else if(sortByRating && !descending){
            queryString = queryString + "and " + SqlQueryConstant.SORT_ASC;
        }

        return jdbcTemplate.query(queryString, ps, new ResultSetExtractor<Integer>() {

            @Override
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {

                Integer count = -1;

                while(rs.next()){
                    count = rs.getInt(1);
                }
                System.out.println(">>> In repo countResult, count: " + count); // debug
                return count;
            }
            
        });
    }

    public Optional<List<String>> getStates(){

        System.out.println(">>> in hosp Repo get State");

        try{
            List<String> states = jdbcTemplate.queryForList(SqlQueryConstant.QUERY_ALL_STATES,String.class);

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
            List<String> cities = jdbcTemplate.queryForList(SqlQueryConstant.QUERY_CITIES,String.class, state);

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

    public Optional<Hospital> findHospitalById(String facilityId){

        try{

            Hospital hospital = jdbcTemplate.queryForObject(SqlQueryConstant.QUERY_HOSPITAL_BY_ID, BeanPropertyRowMapper.newInstance(Hospital.class), facilityId);
            return Optional.of(hospital);
            
        }catch(Exception ex){
            
            System.out.println(" in queryHospital,catch exception: " + ex);

            if(ex instanceof EmptyResultDataAccessException){
                return Optional.empty(); // if cailityId not found
            }else{
                throw ex; // other exceptions
            }
        }
    }

    public Integer insertHospitalReview(HospitalReview review){

        // facility_id, facility_eth_address, reviewer, patientNRIC,
        // nurse_communication, doctor_communication, staff_responsiveness, communication_about_medicines,
        // discharge_information, care_transition, cleanliness, quientness, overallRating, willingness_to_recommend, comments

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(SqlQueryConstant.INSERT_HOSPITAL_REVIEW, new String[]{"id"});
                ps.setString(1, review.getFacilityId());
                ps.setString(2, review.getFacilityEthAddress());
                ps.setString(3, review.getReviewer());
                ps.setString(4, review.getPatientId());
                ps.setInt(5, review.getNurseCommunication());
                ps.setInt(6, review.getDoctorCommunication());
                ps.setInt(7, review.getStaffResponsiveness());
                ps.setInt(8, review.getCommunicationAboutMedicines());
                ps.setInt(9, review.getDischargeInformation());
                ps.setInt(10, review.getCareTransition());
                ps.setInt(11, review.getCleanliness());
                ps.setInt(12, review.getQuientness());
                ps.setInt(13, review.getOverallRating());
                ps.setInt(14, review.getWillingnessToRecommend());
                ps.setString(15, review.getComments());

                return ps;
            }
            
        };

        try{

            jdbcTemplate.update(psc,generatedKeyHolder);
            return generatedKeyHolder.getKey().intValue();

        }catch(Exception ex){

            System.out.println("in HospitalRepo insertHospitalReview() catch exception: " + ex );
            return -1;

        }
            
    }

    
}
