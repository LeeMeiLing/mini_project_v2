package sg.edu.nus.iss.server.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Hospital {
    
    private String facilityId;
    private String facilityName;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String countyName;
    private String phoneNumber;
    private String hospitalType;
    private String hospitalOwnership;
    private String emergencyServices;
    private String hospitalOverallRating;
    private String ethAddress;
    private String reviewContractAddress;

    
    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHospitalType() {
        return hospitalType;
    }

    public void setHospitalType(String hospitalType) {
        this.hospitalType = hospitalType;
    }

    public String getHospitalOwnership() {
        return hospitalOwnership;
    }

    public void setHospitalOwnership(String hospitalOwnership) {
        this.hospitalOwnership = hospitalOwnership;
    }

    public String getEmergencyServices() {
        return emergencyServices;
    }

    public void setEmergencyServices(String emergencyServices) {
        this.emergencyServices = emergencyServices;
    }

    public String getHospitalOverallRating() {
        return hospitalOverallRating;
    }

    public void setHospitalOverallRating(String hospitalOverallRating) {
        this.hospitalOverallRating = hospitalOverallRating;
    }

    public String getEthAddress() {
        return ethAddress;
    }

    public void setEthAddress(String ethAddress) {
        this.ethAddress = ethAddress;
    }

    public String getReviewContractAddress() {
        return reviewContractAddress;
    }

    public void setReviewContractAddress(String reviewContractAddress) {
        this.reviewContractAddress = reviewContractAddress;
    }

    @Override
    public String toString() {
        return "Hospital [facilityId=" + facilityId + ", facilityName=" + facilityName + ", address=" + address
                + ", city=" + city + ", state=" + state + ", zipCode=" + zipCode + ", countyName=" + countyName
                + ", phoneNumber=" + phoneNumber + ", hospitalType=" + hospitalType + ", hospitalOwnership="
                + hospitalOwnership + ", emergencyServices=" + emergencyServices + ", hospitalOverallRating="
                + hospitalOverallRating + ", ethAddress=" + ethAddress + ", reviewContractAddress="
                + reviewContractAddress + "]";
    }

    public static Hospital createHospital(JsonObject json){

        Hospital hosp = new Hospital();
        hosp.setFacilityId(json.getString("facility_id"));
        hosp.setFacilityName(json.getString("facility_name"));
        hosp.setAddress(json.getString("address"));
        hosp.setCity(json.getString("city"));
        hosp.setState(json.getString("state"));
        hosp.setZipCode(json.getString("zip_code"));
        hosp.setCountyName(json.getString("county_name"));
        hosp.setPhoneNumber(json.getString("phone_number"));
        hosp.setHospitalType(json.getString("hospital_type"));
        hosp.setHospitalOwnership(json.getString("hospital_ownership"));
        hosp.setEmergencyServices(json.getString("emergency_services"));
        hosp.setHospitalOverallRating(json.getString("hospital_overall_rating"));
        
        return hosp;
    }

    public JsonObject toJson(){

        return Json.createObjectBuilder()
                    .add("facilityId", facilityId)
                    .add("facilityName", facilityName)
                    .add("address", address)
                    .add("city", city)
                    .add("state", state)
                    .add("zipCode", zipCode)
                    .add("countyName", countyName)
                    .add("phoneNumber", phoneNumber)
                    .add("hospitalType", hospitalType)
                    .add("hospitalOwnership", hospitalOwnership)
                    .add("emergencyServices", emergencyServices)
                    .add("hospitalOverallRating", hospitalOverallRating)
                    .build();

    }

}
