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
import sg.edu.nus.iss.server.models.User;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

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
            ex.printStackTrace();
            return false;
        }

    }

    public Optional<User> findUser(String userEmail){
        
        try{

            User user = jdbcTemplate.queryForObject(SqlQueryConstant.FIND_USER_BY_EMAIL, BeanPropertyRowMapper.newInstance(User.class), userEmail);
            return Optional.of(user);

        // catch exception if email not found or more than one entry found for the same email
        }catch(Exception ex){
            ex.printStackTrace();
            return Optional.empty();
        }

    }

    


}
