package sg.edu.nus.iss.server.models;

public class User {
    
    private String userEmail;
    private String userPassword;
    private String userName;
    private String mobileNumber;
    private String gender;
    
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getMobileNumber() {
        return mobileNumber;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    @Override
    public String toString() {
        return "User [userEmail=" + userEmail + ", password=" + userPassword + ", userName=" + userName + ", mobileNumber="
                + mobileNumber + ", gender=" + gender + "]";
    }

    

}
