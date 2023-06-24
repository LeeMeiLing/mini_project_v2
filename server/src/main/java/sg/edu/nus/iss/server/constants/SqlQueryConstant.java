package sg.edu.nus.iss.server.constants;

import org.springframework.jdbc.core.PreparedStatementCreator;

public class SqlQueryConstant {
    
    public static final Integer LIMIT = 20;

    public static final String INSERT_HOSPITAL = """
        insert into us_hospitals (facility_id, facility_name, address, city, state, zip_code, county_name,
        phone_number, hospital_type, hospital_ownership, emergency_services, hospital_overall_rating ) 
        values (?,?,?,?,?,?,?,?,?,?,?,?)
    """;
    
    public static final String UPDATE_HOSPITAL = """
        update us_hospitals set facility_name = ? , address = ? , city = ? , state = ? , zip_code = ? , 
        county_name = ? , phone_number = ? , hospital_type = ? , hospital_ownership = ? , emergency_services = ? ,
        hospital_overall_rating = ? where facility_id = ?
    """;

    public static final String QUERY_ALL_STATES = """
        select distinct state from us_hospitals order by state
    """;

    public static final String QUERY_CITIES = """
        select distinct city from us_hospitals where state = ? order by city
    """;


    public static final String COUNT = "select count(*) ";
    public static final String SELECT = "select * ";

    // GET /api/hospitals/search
    public static final String QUERY_HOSPITALS_ALL = "from us_hospitals ";

    // GET /api/hospitals/search?name=name
    public static final String QUERY_HOSPITALS_BY_NAME = "from us_hospitals where facility_name like ? ";

    // GET /api/hospitals/search/{state}
    public static final String QUERY_HOSPITALS_BY_STATE = "from us_hospitals where state = ? ";

    // GET /api/hospitals/search/{state}?name=name
    public static final String QUERY_HOSPITALS_BY_STATE_NAME = "from us_hospitals where state = ? and facility_name like ? ";

    // GET /api/hospitals/search/{state}/{city}
    public static final String QUERY_HOSPITALS_BY_STATE_CITY = "from us_hospitals where state = ? and city = ? ";

    // GET /api/hospitals/search/{state}/{city}?name=name
    public static final String QUERY_HOSPITALS_BY_STATE_CITY_NAME = "from us_hospitals where state = ? and city = ? and facility_name like ? ";

    public static final String LIMIT_OFFSET = "limit ? offset ?";

    public static final String SORT_ASC = "hospital_overall_rating in ('1','2','3','4','5') order by hospital_overall_rating ";

    public static final String SORT_DESC = "hospital_overall_rating in ('1','2','3','4','5') order by hospital_overall_rating desc ";

    public static final String QUERY_HOSPITAL_BY_ID = "select * from us_hospitals where facility_id = ? "; 


    /*
     * using sg_hospital_reviews for sg_hospitals
     */

    public static final String INSERT_HOSPITAL_REVIEW_SG = """
        insert into sg_hospital_reviews (facility_id, reviewer, patient_id,
        nurse_communication, doctor_communication, staff_responsiveness, communication_about_medicines,
        discharge_information, care_transition, cleanliness, quietness, overall_rating, willingness_to_recommend, comments, review_date) 
        values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

    public static final String INSERT_REVIEW_INDEX_SG_HOSPITAL = "update sg_hospital_reviews set eth_review_index = ? where id = ? ";

    public static final String COUNT_REVIEW_SG_HOSPITAL = "select count(*) from sg_hospital_reviews where facility_id = ?";

    public static final String COUNT_REVIEW_RATING_SG_HOSPITAL_BY_HOSPITAL = "select overall_rating, count(*) as count from sg_hospital_reviews where facility_id = ? group by overall_rating";

    public static final String QUERY_REVIEW_SUMMARY_SG_HOSPITAL_BY_HOSPITAL = """
        select facility_id, 
        avg(nurse_communication) as avg_nurse_comm, 
        avg(doctor_communication) as avg_doctor_comm,
        avg(staff_responsiveness) as avg_staff_responsiveness, 
        avg(communication_about_medicines) as avg_comm_abt_med,
        avg(discharge_information) as avg_discharge_info, 
        avg(care_transition) as avg_care_transition, 
        avg(cleanliness) as avg_cleanliness, 
        avg(quietness) as avg_quietness, 
        avg(overall_rating) as avg_overall_rating, 
        avg(willingness_to_recommend) as avg_recommendation
        from sg_hospital_reviews where facility_id = ?
    """;

    public static final String QUERY_REVIEW_SG_HOSPITAL_BY_HOSPITAL = "select * from sg_hospital_reviews where facility_id = ?";

    // =========================================================

    /*
     * using us_hospital_reviews for us_hospitals
     */

    public static final String INSERT_HOSPITAL_REVIEW = """
        insert into us_hospital_reviews (facility_id, reviewer, patient_id,
        nurse_communication, doctor_communication, staff_responsiveness, communication_about_medicines,
        discharge_information, care_transition, cleanliness, quietness, overall_rating, willingness_to_recommend, comments, review_date) 
        values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

    public static final String INSERT_REVIEW_CONTRACT_ADDRESS_US_HOSPITAL = "update us_hospitals set review_contract_address = ? ";

    public static final String INSERT_REVIEW_INDEX_US_HOSPITAL = "update us_hospital_reviews set eth_review_index = ? where id = ? ";

    public static final String COUNT_REVIEW_US_HOSPITAL = "select count(*) from us_hospital_reviews where facility_id = ?";

    public static final String COUNT_REVIEW_RATING_US_HOSPITAL_BY_HOSPITAL = "select overall_rating, count(*) as count from us_hospital_reviews where facility_id = ? group by overall_rating";

    public static final String QUERY_REVIEW_SUMMARY_US_HOSPITAL_BY_HOSPITAL = """
        select facility_id, 
        avg(nurse_communication) as avg_nurse_comm, 
        avg(doctor_communication) as avg_doctor_comm,
        avg(staff_responsiveness) as avg_staff_responsiveness, 
        avg(communication_about_medicines) as avg_comm_abt_med,
        avg(discharge_information) as avg_discharge_info, 
        avg(care_transition) as avg_care_transition, 
        avg(cleanliness) as avg_cleanliness, 
        avg(quietness) as avg_quietness, 
        avg(overall_rating) as avg_overall_rating, 
        avg(willingness_to_recommend) as avg_recommendation
        from us_hospital_reviews where facility_id = ?
    """;

    public static final String QUERY_REVIEW_US_HOSPITAL_BY_HOSPITAL = "select * from us_hospital_reviews where facility_id = ?";

    // =========================================================

    public static final String INSERT_USER = "insert into users (user_email, user_password, user_name, mobile_number, gender) values (?, ?, ?, ?, ?)";
    
    public static final String FIND_USER_BY_EMAIL = "select user_email, user_password from users where user_email = ?";
    
    public static final String INSERT_SG_HOSPITAL="""
        insert into sg_hospitals (
        facility_id,
        facility_name,
        license,
        registered,
        jci_accredited,
        address,
        street_name,
        zip_code,
        country_code,
        phone_number,
        hospital_ownership,
        emergency_services,
        eth_address,
        account_password,
        encrypted_key_store) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
    """;

    public static final String UPDATE_SG_HOSPITAL_CONTRACT_ADDRESS = "update sg_hospitals set contract_address = ? where facility_id = ?";

    public static final String FIND_MOH_ETH_ADDRESS = "select moh_eth_address from moh where country_code = ?";

    public static final String FIND_HOSPITAL_SG_BY_ETH_ADDRESS = "select * from sg_hospitals where eth_address = ?";

    public static final String FIND_HOSPITAL_SG_BY_FACILITYID = "select * from sg_hospitals where facility_id = ?";

    // insert into statistics (eth_stat_index, hosp_contract_address, supporting_documents) values (?,?,?);
    public static final String INSERT_STATISTIC = "insert into statistics (eth_stat_index, hosp_contract_address) values (?,?)";

    public static final String GET_STATISTIC_CONTRACT_ADDRESS = "select hosp_contract_address from statistics where eth_stat_index = ? ";

    public static final String GET_ALL_MOH = "select * from moh";

    public static final String GET_MOH_BY_ETH_ADDRESS = "select * from moh where moh_eth_address = ?";
   
    public static final String FIND_HOSPITAL_SG_BY_VERIFICATION_STATUS = "select * from sg_hospitals where registered = ?";

    public static final String FIND_HOSPITAL_SG_BY_STAT_VERIFICATION_STATUS = """
        select * from sg_hospitals where facility_id in (
        select distinct(facility_id) from sg_hospitals right join 
        statistics on sg_hospitals.contract_address = statistics.hosp_contract_address
        where statistics.verified = ?)
    """;

    public static final String QUERY_HOSPITAL_SG_BY_OWNERSHIP_NAME = "select * from sg_hospitals where hospital_ownership = ? and facility_name like ? ";

    public static final String HOSP_SG_SORT_ASC = "order by hospital_overall_rating ";

    public static final String HOSP_SG_SORT_DESC = "order by hospital_overall_rating desc ";

    public static final String QUERY_HOSPITAL_SG_BY_OWNERSHIP = "select * from sg_hospitals where hospital_ownership = ? ";

    public static final String QUERY_HOSPITAL_SG_BY_NAME = "select * from sg_hospitals where facility_name like ? ";

    public static final String QUERY_ALL_HOSPITAL_SG = "select * from sg_hospitals ";

    public static final String FIND_DISTINCT_CONTRACT_ADDRESS = "select distinct(hosp_contract_address) from statistics";

    public static final String FIND_HOSPITAL_SG_BY_CONTRACT = "select * from sg_hospitals where contract_address = ?";

    public static final String UPDATE_SG_HOSPITAL_RATING = "update sg_hospitals set hospital_overall_rating = ? where facility_id = ?";

    public static final String UPDATE_SG_HOSPITAL_REGISTERED = "update sg_hospitals set registered = ? where facility_id = ?";

    public static final String UPDATE_SG_HOSPITAL_JCI = "update sg_hospitals set jci_accredited = ? where facility_id = ?";



}
