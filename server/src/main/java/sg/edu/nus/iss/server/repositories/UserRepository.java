package sg.edu.nus.iss.server.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.server.models.User;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    private final String USER_COLLECTION = "users";
    private final String INSERT_USER = "insert into users (user_email, user_password, user_name, mobile_number, gender) values (?, ?, ?, ?, ?)";

    public void saveUser(User user){

        jdbcTemplate.update(INSERT_USER, new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, user.getUserEmail());
                ps.setString(2, user.getUserPassword());
                ps.setString(3, user.getUserName());
                ps.setString(4, user.getMobileNumber());
                ps.setString(5, user.getGender());
            }
            
        });

       
    }
}
