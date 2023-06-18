package sg.edu.nus.iss.server.models;

public class HospitalSg {

    private String facilityId; // to be assign UUID
    private String facilityName;
    private String license;
    private boolean registered; // MOH to verify
    private boolean jeciAccredited; // MOH to verify
    private String address; 
    private String streetName;
    private String zipCode;
    private String countryCode; // drop down option
    private String phoneNumber;
    // private String hospitalType;
    private String hospitalOwnership; 
    private String emergencyServices; // yes or no
    private String hospitalOverallRating; 
    private String ethAddress; // get from metamask
    private String reviewContractAddress; // to be assign
    private String accountPassword; // to be encrypted
    
    /*
     * ethAddress:this.fb.control<string>('',[Validators.required, Validators.minLength(42), Validators.maxLength(42)]),
      accountPassword:this.fb.control<string>('',[Validators.required, Validators.pattern(new RegExp('(?=\\S*$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}'))]),
      facilityName:this.fb.control<string>('',[Validators.required]),
      license:this.fb.control<string>('',[Validators.required]),
      address:this.fb.control<string>('',[Validators.required]),
      streetName:this.fb.control<string>('',[Validators.required]),
      zipCode:this.fb.control<number | undefined>(undefined,[Validators.required]),
      countryCode:this.fb.control<string>('',[Validators.required]),
      phone_number:this.fb.control<string>('',[Validators.required]),
      hospital_type:this.fb.control<string>('',[Validators.required]),
      hospital_ownership:this.fb.control<string>('',[Validators.required]),
      emergency_services:this.fb.control<string>('',[Validators.required])
     */
}
