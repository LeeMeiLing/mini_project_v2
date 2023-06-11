package sg.edu.nus.iss.server.constants;

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

    public static final String INSERT_HOSPITAL_REVIEW = 
    """
    insert into us_hospital_reviews (facility_id, facility_eth_address, reviewer, patient_id,
    nurse_communication, doctor_communication, staff_responsiveness, communication_about_medicines,
    discharge_information, care_transition, cleanliness, quientness, overallRating, willingness_to_recommend, comments) 
    values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
}
