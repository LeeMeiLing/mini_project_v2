package sg.edu.nus.iss.server.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.server.constants.SqlQueryConstant;
import sg.edu.nus.iss.server.models.HospitalSg;
import sg.edu.nus.iss.server.models.User;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    // private final String INSERT_USER = "insert into users (user_email, user_password, user_name, mobile_number, gender) values (?, ?, ?, ?, ?)";
    // private final String FIND_USER_BY_EMAIL = "select user_email, user_password from users where user_email = ?";
    // private final String INSERT_SG_HOSPITAL="""
    //         insert into sg_hospitals (
    //         facility_id,
    //         facility_name,
    //         license,
    //         registered,
    //         jci_accredited,
    //         address,
    //         street_name,
    //         zip_code,
    //         country_code,
    //         phone_number,
    //         hospital_type,
    //         hospital_ownership,
    //         emergency_services,
    //         hospital_overall_rating,
    //         eth_address,
    //         contract_address,
    //         account_password,
    //         encrypted_key_store) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
    //         """;

    /*
     * return true if successfully saved new user to MYSQL database
     * return false if failed
     */
    public boolean saveUser(User user){

        try{
            jdbcTemplate.update(SqlQueryConstant.INSERT_USER, new PreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, user.getUserEmail());
                    ps.setString(2, user.getUserPassword());
                    ps.setString(3, user.getUserName());
                    ps.setString(4, user.getMobileNumber());
                    ps.setString(5, user.getGender());
                    
                }
                
            });

            return true;
    
        }catch(Exception ex){
            return false;
        }

    }

    public Optional<User> findUser(String userEmail){
        
        try{

            User user = jdbcTemplate.queryForObject(SqlQueryConstant.FIND_USER_BY_EMAIL, BeanPropertyRowMapper.newInstance(User.class), userEmail);
            System.out.println(">>> in repo findUser(), " + user); // debug
            return Optional.of(user);

        // catch exception if email not found or more than one entry found for the same email
        }catch(Exception ex){
            System.out.println(">>> in repo findUser() catch exception");
            return Optional.empty();
        }

    }

    


}
