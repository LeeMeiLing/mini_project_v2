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
            System.out.println("error here !!!");
            ex.printStackTrace();
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

    public Optional<HospitalSg> findHospitalSgByEthAddress(String ethAddress){

         try{

            HospitalSg hosp = jdbcTemplate.queryForObject(SqlQueryConstant.FIND_HOSPITAL_SG_BY_ETH_ADDRESS, BeanPropertyRowMapper.newInstance(HospitalSg.class), ethAddress);
            return Optional.of(hosp);

        // catch exception if ethAddress not found or more than one entry found for the same eth Address
        }catch(Exception ex){
            return Optional.empty();
        }

    }

    public Optional<HospitalSg> findHospitalSgByFacilityId(String facilityId){

         try{

            HospitalSg hosp = jdbcTemplate.queryForObject(SqlQueryConstant.FIND_HOSPITAL_SG_BY_FACILITYID, BeanPropertyRowMapper.newInstance(HospitalSg.class), facilityId);
            return Optional.of(hosp);

        // catch exception if ethAddress not found or more than one entry found for the same eth Address
        }catch(Exception ex){
            return Optional.empty();
        }

    }


    public boolean updateStatistic(Integer ethStatIndex, String contractAddress){

         try{
            
            jdbcTemplate.update(SqlQueryConstant.INSERT_STATISTIC, ethStatIndex, contractAddress);
            return true;
    
        }catch(Exception ex){

            return false;
        }
        
    }

    // public Optional<String> getContractAddressByStatisticIndex(Integer statIndex){

    //     String contractAddress = null;

    //     contractAddress = jdbcTemplate.queryForObject(SqlQueryConstant.GET_STATISTIC_CONTRACT_ADDRESS, String.class, statIndex);
        
    //     if(contractAddress != null){
    //         return Optional.of(contractAddress);
    //     }
        
    //     return Optional.empty();
    // }
}
