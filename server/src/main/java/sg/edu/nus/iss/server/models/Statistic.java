package sg.edu.nus.iss.server.models;

public class Statistic {

    private Integer mortality;
    private Integer  patientSafety;
    private Integer  readmission;
    private Integer  patientExperience;
    private Integer  effectiveness;
    private Integer  timeliness;
    private Integer  medicalImagingEfficiency;
    private String timestamp; // created on chain
    private boolean verified;
    
    
    public Integer getMortality() {
        return mortality;
    }
    public void setMortality(Integer mortality) {
        this.mortality = mortality;
    }
    public Integer getPatientSafety() {
        return patientSafety;
    }
    public void setPatientSafety(Integer patientSafety) {
        this.patientSafety = patientSafety;
    }
    public Integer getReadmission() {
        return readmission;
    }
    public void setReadmission(Integer readmission) {
        this.readmission = readmission;
    }
    public Integer getPatientExperience() {
        return patientExperience;
    }
    public void setPatientExperience(Integer patientExperience) {
        this.patientExperience = patientExperience;
    }
    public Integer getEffectiveness() {
        return effectiveness;
    }
    public void setEffectiveness(Integer effectiveness) {
        this.effectiveness = effectiveness;
    }
    public Integer getTimeliness() {
        return timeliness;
    }
    public void setTimeliness(Integer timeliness) {
        this.timeliness = timeliness;
    }
    public Integer getMedicalImagingEfficiency() {
        return medicalImagingEfficiency;
    }
    public void setMedicalImagingEfficiency(Integer medicalImagingEfficiency) {
        this.medicalImagingEfficiency = medicalImagingEfficiency;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public boolean isVerified() {
        return verified;
    }
    public void setVerified(boolean verified) {
        this.verified = verified;
    }
    
    @Override
    public String toString() {
        return "Statistic [mortality=" + mortality + ", patientSafety=" + patientSafety + ", readmission=" + readmission
                + ", patientExperience=" + patientExperience + ", effectiveness=" + effectiveness + ", timeliness="
                + timeliness + ", medicalImagingEfficiency=" + medicalImagingEfficiency + ", timestamp=" + timestamp
                + ", verified=" + verified + "]";
    }

    

}