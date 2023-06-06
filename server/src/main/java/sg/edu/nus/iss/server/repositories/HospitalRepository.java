package sg.edu.nus.iss.server.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.server.models.Hospital;

@Repository
public class HospitalRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

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

    private final String QUERY_ALL_HOSPITAL = """

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
    

    public Optional<List<Hospital>> getHospitalList(String name, Integer limit){
        
        // do query !!!
        return null;
    }
}
