package sg.edu.nus.iss.server.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.server.constants.SqlQueryConstant;
import sg.edu.nus.iss.server.models.HospitalSg;

@Repository
public class HospitalSgRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean insertHospitalSg(HospitalSg h){

        try{
            jdbcTemplate.update(SqlQueryConstant.INSERT_SG_HOSPITAL, new PreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, h.getFacilityId());
                    ps.setString(2, h.getFacilityName());
                    ps.setString(3, h.getLicense());
                    ps.setBoolean(4, h.isRegistered());
                    ps.setBoolean(5, h.isJciAccredited());
                    ps.setString(6,h.getAddress());
                    ps.setString(7,h.getStreetName());
                    ps.setString(8,h.getZipCode());
                    ps.setString(9,h.getCountryCode());
                    ps.setString(10,h.getPhoneNumber());
                    ps.setString(11,h.getHospitalOwnership());
                    ps.setString(12,h.getEmergencyServices());
                    ps.setString(13,h.getEthAddress());
                    ps.setString(14,h.getAccountPassword());
                    ps.setBytes(15,h.getEncryptedKeyStore());
                    
                }
                
            });

            return true;
    
        }catch(Exception ex){
            return false;
        }

    }


    public String getMohAddress(String countryCode){

        return jdbcTemplate.queryForObject(SqlQueryConstant.FIND_MOH_ETH_ADDRESS, String.class ,countryCode);
        
    }

    public boolean updateContractAddress(String facilityId, String contractAddress){

         try{
            
            jdbcTemplate.update(SqlQueryConstant.UPDATE_SG_HOSPITAL_CONTRACT_ADDRESS, contractAddress, facilityId);
            return true;
    
        }catch(Exception ex){
            System.out.println("in update catch: " + ex);
            return false;
        }
    }
}
