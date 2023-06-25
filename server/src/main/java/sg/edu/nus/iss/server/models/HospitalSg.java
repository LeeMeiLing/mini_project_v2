package sg.edu.nus.iss.server.models;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class HospitalSg {

    private String facilityId; // to be assign UUID
    private String facilityName;
    private String license;
    private boolean registered; // MOH to verify
    private boolean jciAccredited; // MOH to verify
    private String address; 
    private String streetName;
    private String zipCode;
    private String countryCode; // drop down option
    private String phoneNumber;
    private String hospitalOwnership; 
    private String emergencyServices; // yes or no
    private String hospitalOverallRating; // get from review
    private String ethAddress; // get from metamask
    private String contractAddress; // to be assign
    private String accountPassword; // to be encrypted
    private byte[] encryptedKeyStore;
    
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
    public String getLicense() {
        return license;
    }
    public void setLicense(String license) {
        this.license = license;
    }
    public boolean isRegistered() {
        return registered;
    }
    public void setRegistered(boolean registered) {
        this.registered = registered;
    }
    public boolean isJciAccredited() {
        return jciAccredited;
    }
    public void setJciAccredited(boolean jciAccredited) {
        this.jciAccredited = jciAccredited;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getStreetName() {
        return streetName;
    }
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    public String getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
    public String getContractAddress() {
        return contractAddress;
    }
    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }
    public String getAccountPassword() {
        return accountPassword;
    }
    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }
    public byte[] getEncryptedKeyStore() {
        return encryptedKeyStore;
    }
    public void setEncryptedKeyStore(byte[] encryptedKeyStore) {
        this.encryptedKeyStore = encryptedKeyStore;
    }
    
    public static HospitalSg createHospitalSg(JsonObject jo){
        
        HospitalSg hosp = new HospitalSg();
        hosp.setFacilityId(UUID.randomUUID().toString().substring(0, 6));
        hosp.setFacilityName(jo.getString("facilityName"));
        hosp.setLicense(jo.getString("license"));
        hosp.setRegistered(false);
        hosp.setJciAccredited(false);
        hosp.setAddress(jo.getString("address"));
        hosp.setStreetName(jo.getString("streetName"));
        hosp.setZipCode(jo.getString("zipCode"));
        hosp.setCountryCode(jo.getString("countryCode"));
        hosp.setPhoneNumber(jo.getString("phoneNumber"));
        hosp.setHospitalOwnership(jo.getString("hospitalOwnership"));
        hosp.setEmergencyServices(jo.getString("emergencyServices"));

        JsonObject encryptedKeyStore = jo.getJsonObject("encryptedKeyStore");
        hosp.setEncryptedKeyStore(encryptedKeyStore.toString().getBytes());
        hosp.setEthAddress("0x" + encryptedKeyStore.getString("address"));
        
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        hosp.setAccountPassword(bCryptPasswordEncoder.encode(jo.getString("accountPassword")));

        return hosp;

    }

    @Override
    public String toString() {
        return "HospitalSg [facilityId=" + facilityId + ", facilityName=" + facilityName + ", license=" + license
                + ", registered=" + registered + ", jciAccredited=" + jciAccredited + ", address=" + address
                + ", streetName=" + streetName + ", zipCode=" + zipCode + ", countryCode=" + countryCode
                + ", phoneNumber=" + phoneNumber + ", hospitalOwnership=" + hospitalOwnership + ", emergencyServices="
                + emergencyServices + ", hospitalOverallRating=" + hospitalOverallRating + ", ethAddress=" + ethAddress
                + ", contractAddress=" + contractAddress + ", accountPassword=" + accountPassword
                + ", encryptedKeyStore=" + Arrays.toString(encryptedKeyStore) + "]";
    }

    public JsonObject toJson(){

        return Json.createObjectBuilder()
            .add("facilityId",facilityId)
            .add("facilityName",facilityName)
            .add("license",license)
            .add("registered",registered)
            .add("jciAccredited",jciAccredited)
            .add("address",address)
            .add("streetName",streetName)
            .add("zipCode",zipCode)
            .add("countryCode",countryCode)
            .add("phoneNumber",phoneNumber)
            .add("hospitalOwnership",hospitalOwnership)
            .add("hospitalOverallRating", hospitalOverallRating==null?"Not Available":hospitalOverallRating)
            .add("emergencyServices",emergencyServices)
            .add("ethAddress",ethAddress)
            .add("contractAddress",contractAddress)
            .build();
    }
    
}
