package sg.edu.nus.iss.server.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Statistic {

    private Double mortality;
    private Double  patientSafety;
    private Double  readmission;
    private Double  patientExperience;
    private Double  effectiveness;
    private Double  timeliness;
    private Double  medicalImagingEfficiency;
    private String timestamp; // created on chain
    private boolean verified;
    
    public Statistic() {
    }

    public Statistic(Double mortality, Double patientSafety, Double readmission, Double patientExperience,
            Double effectiveness, Double timeliness, Double medicalImagingEfficiency, String timestamp,
            boolean verified) {
        this.mortality = mortality;
        this.patientSafety = patientSafety;
        this.readmission = readmission;
        this.patientExperience = patientExperience;
        this.effectiveness = effectiveness;
        this.timeliness = timeliness;
        this.medicalImagingEfficiency = medicalImagingEfficiency;
        this.timestamp = timestamp;
        this.verified = verified;
    }
     public Double getMortality() {
        return mortality;
    }
    public void setMortality(Double mortality) {
        this.mortality = mortality;
    }
    public Double getPatientSafety() {
        return patientSafety;
    }
    public void setPatientSafety(Double patientSafety) {
        this.patientSafety = patientSafety;
    }
    public Double getReadmission() {
        return readmission;
    }
    public void setReadmission(Double readmission) {
        this.readmission = readmission;
    }
    public Double getPatientExperience() {
        return patientExperience;
    }
    public void setPatientExperience(Double patientExperience) {
        this.patientExperience = patientExperience;
    }
    public Double getEffectiveness() {
        return effectiveness;
    }
    public void setEffectiveness(Double effectiveness) {
        this.effectiveness = effectiveness;
    }
    public Double getTimeliness() {
        return timeliness;
    }
    public void setTimeliness(Double timeliness) {
        this.timeliness = timeliness;
    }
    public Double getMedicalImagingEfficiency() {
        return medicalImagingEfficiency;
    }
    public void setMedicalImagingEfficiency(Double medicalImagingEfficiency) {
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

    public JsonObject toJson(){

        return Json.createObjectBuilder()
                .add("mortality", mortality)
                .add("patientSafety", patientSafety)
                .add("readmission", readmission)
                .add("patientExperience", patientExperience)
                .add("effectiveness", effectiveness)
                .add("timeliness", timeliness)
                .add("medicalImagingEfficiency", medicalImagingEfficiency)
                .add("timestamp", timestamp)
                .add("verified", verified)
                .build();
    }
    
}
