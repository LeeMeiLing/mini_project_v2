package sg.edu.nus.iss.server.models;

import java.util.Arrays;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Moh {
    
    private String countryCode;
    private String countryName;
    private String mohEthAddress;
    private String accountPassword;
    private byte[] encryptedKeyStore;
    
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getMohEthAddress() {
		return mohEthAddress;
	}
	public void setMohEthAddress(String mohEthAddress) {
		this.mohEthAddress = mohEthAddress;
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
	@Override
	public String toString() {
		return "Moh [countryCode=" + countryCode + ", countryName=" + countryName + ", mohEthAddress=" + mohEthAddress
				+ ", accountPassword=" + accountPassword + ", encryptedKeyStore=" + Arrays.toString(encryptedKeyStore)
				+ "]";
	}

	public JsonObject toJson(){

		return Json.createObjectBuilder()
			.add("countryCode", countryCode)
			.add("countryName", countryName)
			.add("mohEthAddress", mohEthAddress)
			.build();
	}

	public static Moh createMoh(JsonObject jo){
        
        Moh moh = new Moh();
  
        moh.setCountryCode(jo.getString("countryCode"));
		moh.setCountryName(jo.getString("countryName"));

        JsonObject encryptedKeyStore = jo.getJsonObject("encryptedKeyStore");
        moh.setEncryptedKeyStore(encryptedKeyStore.toString().getBytes());
        moh.setMohEthAddress("0x" + encryptedKeyStore.getString("address"));
        
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        moh.setAccountPassword(bCryptPasswordEncoder.encode(jo.getString("accountPassword")));

        return moh;

    }
    
}
