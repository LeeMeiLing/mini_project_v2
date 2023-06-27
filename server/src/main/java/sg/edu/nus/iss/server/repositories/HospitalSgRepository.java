package sg.edu.nus.iss.server.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.server.constants.SqlQueryConstant;
import sg.edu.nus.iss.server.models.HospitalReview;
import sg.edu.nus.iss.server.models.HospitalReviewSummary;
import sg.edu.nus.iss.server.models.HospitalSg;
import sg.edu.nus.iss.server.models.Moh;


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
            ex.printStackTrace();
            return false;
        }

    }

     public boolean insertMoh(Moh moh){

        try{
            jdbcTemplate.update(SqlQueryConstant.INSERT_MOH, new PreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
               
                    ps.setString(1,moh.getCountryCode());
                    ps.setString(2,moh.getCountryName());             
                    ps.setString(3,moh.getMohEthAddress());
                    ps.setString(4,moh.getAccountPassword());
                    ps.setBytes(5,moh.getEncryptedKeyStore());
                    
                }
                
            });

            return true;
    
        }catch(Exception ex){
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
            ex.printStackTrace();
            return false;
        }
    }

    public Integer checkHospEthAddressExist(String ethAddress){

        PreparedStatementSetter ps = new PreparedStatementSetter() {

        @Override
        public void setValues(PreparedStatement ps) throws SQLException {
            ps.setString(1, ethAddress);
        }
        
       };

        return jdbcTemplate.query(SqlQueryConstant.CHECK_HOSP_ETH_ADD_EXIST, ps, new ResultSetExtractor<Integer>() {

            @Override
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {

                Integer count = -1;

                while(rs.next()){
                    count = rs.getInt(1); // if facilityId not found, will return 0
                }

                return count;
            }
            
        });
    }

     public Integer checkMohEthAddressExist(String ethAddress){

        PreparedStatementSetter ps = new PreparedStatementSetter() {

        @Override
        public void setValues(PreparedStatement ps) throws SQLException {
            ps.setString(1, ethAddress);
        }
        
       };

        return jdbcTemplate.query(SqlQueryConstant.CHECK_MOH_ETH_ADD_EXIST, ps, new ResultSetExtractor<Integer>() {

            @Override
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {

                Integer count = -1;

                while(rs.next()){
                    count = rs.getInt(1); // if facilityId not found, will return 0
                }

                return count;
            }
            
        });
    }

    public Optional<HospitalSg> findHospitalSgByEthAddress(String ethAddress){

         try{

            HospitalSg hosp = jdbcTemplate.queryForObject(SqlQueryConstant.FIND_HOSPITAL_SG_BY_ETH_ADDRESS, BeanPropertyRowMapper.newInstance(HospitalSg.class), ethAddress);
            return Optional.of(hosp);

        // catch exception if ethAddress not found or more than one entry found for the same eth Address
        }catch(Exception ex){

            ex.printStackTrace();        
            return Optional.empty();
        }

    }

    public Optional<HospitalSg> findHospitalSgByFacilityId(String facilityId){

        try{

            HospitalSg hosp = jdbcTemplate.queryForObject(SqlQueryConstant.FIND_HOSPITAL_SG_BY_FACILITYID, BeanPropertyRowMapper.newInstance(HospitalSg.class), facilityId);
            return Optional.of(hosp);

        // catch exception if ethAddress not found or more than one entry found for the same eth Address
        }catch(Exception ex){
            ex.printStackTrace();
            return Optional.empty();
        }

    }


    public boolean updateStatistic(Integer ethStatIndex, String contractAddress){

         try{
            
            jdbcTemplate.update(SqlQueryConstant.INSERT_STATISTIC, ethStatIndex, contractAddress);
            return true;
    
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        
    }

    public Optional<List<HospitalSg>> getHospitalsByPendingVerify(){

         try{

            // return empty list if not found
            List<HospitalSg> hospitals = jdbcTemplate.query(SqlQueryConstant.FIND_HOSPITAL_SG_BY_VERIFICATION_STATUS, BeanPropertyRowMapper.newInstance(HospitalSg.class), false);
            return Optional.of(hospitals);

        }catch(Exception ex){
            ex.printStackTrace();
            return Optional.empty();
        }

    }


    public Optional<List<HospitalSg>> findHospitalsByOwnershipAndName(String hospitalOwnership, String name,
            Integer offset, Boolean sortByRating, Boolean descending) {

        String queryString = SqlQueryConstant.QUERY_HOSPITAL_SG_BY_OWNERSHIP_NAME;

        if(sortByRating && descending){
            queryString = queryString +SqlQueryConstant.HOSP_SG_SORT_DESC +SqlQueryConstant.LIMIT_OFFSET;

        }else if(sortByRating && !descending){
            queryString = queryString + SqlQueryConstant.HOSP_SG_SORT_ASC +SqlQueryConstant.LIMIT_OFFSET;

        }else{
            queryString = queryString + SqlQueryConstant.LIMIT_OFFSET;

        }

        List<HospitalSg> hospitalSgList = jdbcTemplate.query(queryString,new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, hospitalOwnership);
                ps.setString(2, "%" + name + "%");              
                ps.setInt(3, SqlQueryConstant.LIMIT);
                ps.setInt(4, offset * SqlQueryConstant.LIMIT);
            }
            
        },BeanPropertyRowMapper.newInstance(HospitalSg.class));

        if(!hospitalSgList.isEmpty()){
            return Optional.of(hospitalSgList);
        }else{
            return Optional.empty();
        }
        
    }

    public Optional<List<HospitalSg>> findHospitalsByOwnership(String hospitalOwnership, Integer offset,
            Boolean sortByRating, Boolean descending) {

        String queryString = SqlQueryConstant.QUERY_HOSPITAL_SG_BY_OWNERSHIP;

        if(sortByRating && descending){
            queryString = queryString +SqlQueryConstant.HOSP_SG_SORT_DESC +SqlQueryConstant.LIMIT_OFFSET;

        }else if(sortByRating && !descending){
            queryString = queryString + SqlQueryConstant.HOSP_SG_SORT_ASC +SqlQueryConstant.LIMIT_OFFSET;

        }else{
            queryString = queryString + SqlQueryConstant.LIMIT_OFFSET;

        }

        List<HospitalSg> hospitalSgList = jdbcTemplate.query(queryString,new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, hospitalOwnership);
                ps.setInt(2, SqlQueryConstant.LIMIT);
                ps.setInt(3, offset * SqlQueryConstant.LIMIT);
            }
            
        },BeanPropertyRowMapper.newInstance(HospitalSg.class));

        if(!hospitalSgList.isEmpty()){
            return Optional.of(hospitalSgList);
        }else{
            return Optional.empty();
        }
    }


    public Optional<List<HospitalSg>> findHospitalsByName(String name, Integer offset, Boolean sortByRating,
            Boolean descending) {

        String queryString = SqlQueryConstant.QUERY_HOSPITAL_SG_BY_NAME;

        if(sortByRating && descending){
            queryString = queryString +SqlQueryConstant.HOSP_SG_SORT_DESC +SqlQueryConstant.LIMIT_OFFSET;

        }else if(sortByRating && !descending){
            queryString = queryString + SqlQueryConstant.HOSP_SG_SORT_ASC +SqlQueryConstant.LIMIT_OFFSET;

        }else{
            queryString = queryString + SqlQueryConstant.LIMIT_OFFSET;

        }

        List<HospitalSg> hospitalSgList = jdbcTemplate.query(queryString,new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, "%" + name + "%");              
                ps.setInt(2, SqlQueryConstant.LIMIT);
                ps.setInt(3, offset * SqlQueryConstant.LIMIT);
            }
            
        },BeanPropertyRowMapper.newInstance(HospitalSg.class));

        if(!hospitalSgList.isEmpty()){
            return Optional.of(hospitalSgList);
        }else{
            return Optional.empty();
        }
    }


    public Optional<List<HospitalSg>> findAllHospitals(Integer offset, Boolean sortByRating, Boolean descending) {

        String queryString;

        if(sortByRating && descending){
            queryString = SqlQueryConstant.QUERY_ALL_HOSPITAL_SG +SqlQueryConstant.HOSP_SG_SORT_DESC +SqlQueryConstant.LIMIT_OFFSET;

        }else if(sortByRating && !descending){
            queryString = SqlQueryConstant.QUERY_ALL_HOSPITAL_SG + SqlQueryConstant.HOSP_SG_SORT_ASC +SqlQueryConstant.LIMIT_OFFSET;

        }else{
            queryString = SqlQueryConstant.QUERY_ALL_HOSPITAL_SG + SqlQueryConstant.LIMIT_OFFSET;

        }

        List<HospitalSg> hospitalSgList = jdbcTemplate.query(queryString,new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, SqlQueryConstant.LIMIT);
                ps.setInt(2, offset * SqlQueryConstant.LIMIT);
            }
            
        },BeanPropertyRowMapper.newInstance(HospitalSg.class));

        if(!hospitalSgList.isEmpty()){
            return Optional.of(hospitalSgList);
        }else{
            return Optional.empty();
        }
    }


    public List<String> getContractAddressFromStat() {
            
        List<String> contracts = jdbcTemplate.queryForList(SqlQueryConstant.FIND_DISTINCT_CONTRACT_ADDRESS, String.class);
        return contracts;

    }


    public Optional<HospitalSg> findHospitalSgByContractAddress(String contractAddress) {
        try{

            HospitalSg hosp = jdbcTemplate.queryForObject(SqlQueryConstant.FIND_HOSPITAL_SG_BY_CONTRACT, BeanPropertyRowMapper.newInstance(HospitalSg.class), contractAddress);
            return Optional.of(hosp);

        // catch exception if ethAddress not found or more than one entry found for the same eth Address
        }catch(Exception ex){
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    public Integer insertHospitalReview(HospitalReview review){

        // facility_id, facility_eth_address, reviewer, patientNRIC,
        // nurse_communication, doctor_communication, staff_responsiveness, communication_about_medicines,
        // discharge_information, care_transition, cleanliness, quietness, overallRating, willingness_to_recommend, comments

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(SqlQueryConstant.INSERT_HOSPITAL_REVIEW_SG, new String[]{"id"});
                ps.setString(1, review.getFacilityId());
                ps.setString(2, review.getReviewer());
                ps.setString(3, review.getPatientId());
                ps.setInt(4, review.getNurseCommunication());
                ps.setInt(5, review.getDoctorCommunication());
                ps.setInt(6, review.getStaffResponsiveness());
                ps.setInt(7, review.getCommunicationAboutMedicines());
                ps.setInt(8, review.getDischargeInformation());
                ps.setInt(9, review.getCareTransition());
                ps.setInt(10, review.getCleanliness());
                ps.setInt(11, review.getQuietness());
                ps.setInt(12, review.getOverallRating());
                ps.setInt(13, review.getWillingnessToRecommend());
                ps.setString(14, review.getComments());
                ps.setDate(15, review.getReviewDate());

                return ps;
            }
            
        };

        try{

            jdbcTemplate.update(psc,generatedKeyHolder);
            return generatedKeyHolder.getKey().intValue();

        }catch(Exception ex){

            ex.printStackTrace();
            return -1;

        }
            
    }

    public boolean saveEthReviewIndex(Integer reviewId, Integer reviewIndex){
        
        try{
            jdbcTemplate.update(SqlQueryConstant.INSERT_REVIEW_INDEX_SG_HOSPITAL, new PreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps) throws SQLException {

                    ps.setInt(1, reviewIndex);
                    ps.setInt(2, reviewId);
                    
                }
                
            });

            return true;
    
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    

     public Integer getReviewCountByFacilityId(String facilityId){

       PreparedStatementSetter ps = new PreparedStatementSetter() {

        @Override
        public void setValues(PreparedStatement ps) throws SQLException {
            ps.setString(1, facilityId);
        }
        
       };

        return jdbcTemplate.query(SqlQueryConstant.COUNT_REVIEW_SG_HOSPITAL, ps, new ResultSetExtractor<Integer>() {

            @Override
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {

                Integer count = -1;

                while(rs.next()){
                    count = rs.getInt(1); // if facilityId not found, will return 0
                }

                return count;
            }
            
        });
    }


    public Optional<List<HospitalReview>> getHospitalReviews(String facilityId){

        try{
            List<HospitalReview> reviews = jdbcTemplate.query(SqlQueryConstant.QUERY_REVIEW_SG_HOSPITAL_BY_HOSPITAL, new PreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, facilityId);
                }
                
            }, BeanPropertyRowMapper.newInstance(HospitalReview.class));

            if(!reviews.isEmpty()){
                return Optional.of(reviews);
            }else{
                return Optional.empty();
            }

        }catch(Exception ex){
            ex.printStackTrace();
            return Optional.empty();
        }

    }

    public HospitalReviewSummary getHospitalReviewSummary(String facilityId){

        HospitalReviewSummary reviewSummary = jdbcTemplate.queryForObject(SqlQueryConstant.QUERY_REVIEW_SUMMARY_SG_HOSPITAL_BY_HOSPITAL, BeanPropertyRowMapper.newInstance(HospitalReviewSummary.class), facilityId);
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SqlQueryConstant.COUNT_REVIEW_RATING_SG_HOSPITAL_BY_HOSPITAL, facilityId);

        while(rs.next()){

            Integer rating = rs.getInt("overall_rating");

            switch(rating){
                case 1: reviewSummary.setCountOfRatingOne( rs.getInt("count"));break;
                case 2: reviewSummary.setCountOfRatingTwo( rs.getInt("count"));break;
                case 3: reviewSummary.setCountOfRatingThree( rs.getInt("count"));break;
                case 4: reviewSummary.setCountOfRatingFour( rs.getInt("count"));break;
                case 5: reviewSummary.setCountOfRatingFive( rs.getInt("count"));break;
                default: ;break;
            }
           
        }
   
        return reviewSummary;
    }


    public boolean updateHospitalSgOverallRating(Float newAvgRating, String facilityId) {

        String newAvgRatingString = String.valueOf(newAvgRating);
        if(newAvgRatingString.length()>5){
            newAvgRatingString = newAvgRatingString.substring(0, 5);
        }

        try{
            jdbcTemplate.update(SqlQueryConstant.UPDATE_SG_HOSPITAL_RATING, newAvgRatingString, facilityId);
            return true;
    
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }


    public boolean updateHospitalSgRegistered(String facilityId) {

        try{
            
            jdbcTemplate.update(SqlQueryConstant.UPDATE_SG_HOSPITAL_REGISTERED, true ,facilityId);
            return true;
    
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }

    }

    public boolean updateHospitalSgJci(String facilityId) {

        try{
            
            jdbcTemplate.update(SqlQueryConstant.UPDATE_SG_HOSPITAL_JCI, true ,facilityId);
            return true;
    
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }

    }
}
