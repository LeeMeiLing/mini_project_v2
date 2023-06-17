package sg.edu.nus.iss.server.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class HospitalReviewSummary {
    
    private Integer countOfRatingFive;
    private Integer countOfRatingFour;
    private Integer countOfRatingThree;
    private Integer countOfRatingTwo;
    private Integer countOfRatingOne;
    
    private Float avgOverallRating;
    private Float avgNurseComm;
    private Float avgDoctorComm;
    private Float avgStaffResponsiveness;
    private Float avgCommAbtMed;
    private Float avgDischargeInfo;
    private Float avgCareTransition;
    private Float avgCleanliness;
    private Float avgQuietness;
    private Float avgRecommendation;

    public Integer getCountOfRatingFive() {
        return countOfRatingFive;
    }
    public void setCountOfRatingFive(Integer countOfRatingFive) {
        this.countOfRatingFive = countOfRatingFive;
    }
    public Integer getCountOfRatingFour() {
        return countOfRatingFour;
    }
    public void setCountOfRatingFour(Integer countOfRatingFour) {
        this.countOfRatingFour = countOfRatingFour;
    }
    public Integer getCountOfRatingThree() {
        return countOfRatingThree;
    }
    public void setCountOfRatingThree(Integer countOfRatingThree) {
        this.countOfRatingThree = countOfRatingThree;
    }
    public Integer getCountOfRatingTwo() {
        return countOfRatingTwo;
    }
    public void setCountOfRatingTwo(Integer countOfRatingTwo) {
        this.countOfRatingTwo = countOfRatingTwo;
    }
    public Integer getCountOfRatingOne() {
        return countOfRatingOne;
    }
    public void setCountOfRatingOne(Integer countOfRatingOne) {
        this.countOfRatingOne = countOfRatingOne;
    }
    public Float getAvgOverallRating() {
        return avgOverallRating;
    }
    public void setAvgOverallRating(Float averageOverallRating) {
        this.avgOverallRating = averageOverallRating;
    }

    public Float getAvgNurseComm() {
        return avgNurseComm;
    }
    public void setAvgNurseComm(Float avgNurseComm) {
        this.avgNurseComm = avgNurseComm;
    }
    public Float getAvgDoctorComm() {
        return avgDoctorComm;
    }
    public void setAvgDoctorComm(Float avgDoctorComm) {
        this.avgDoctorComm = avgDoctorComm;
    }
    public Float getAvgStaffResponsiveness() {
        return avgStaffResponsiveness;
    }
    public void setAvgStaffResponsiveness(Float avgStaffResponsiveness) {
        this.avgStaffResponsiveness = avgStaffResponsiveness;
    }
    public Float getAvgCommAbtMed() {
        return avgCommAbtMed;
    }
    public void setAvgCommAbtMed(Float avgCommAbtMed) {
        this.avgCommAbtMed = avgCommAbtMed;
    }
    public Float getAvgDischargeInfo() {
        return avgDischargeInfo;
    }
    public void setAvgDischargeInfo(Float avgDischargeInfo) {
        this.avgDischargeInfo = avgDischargeInfo;
    }
    public Float getAvgCareTransition() {
        return avgCareTransition;
    }
    public void setAvgCareTransition(Float avgCareTransition) {
        this.avgCareTransition = avgCareTransition;
    }
    public Float getAvgCleanliness() {
        return avgCleanliness;
    }
    public void setAvgCleanliness(Float avgCleanliness) {
        this.avgCleanliness = avgCleanliness;
    }
    public Float getAvgQuietness() {
        return avgQuietness;
    }
    public void setAvgQuietness(Float avgQuietness) {
        this.avgQuietness = avgQuietness;
    }
    public Float getAvgRecommendation() {
        return avgRecommendation;
    }
    public void setAvgRecommendation(Float avgRecommendation) {
        this.avgRecommendation = avgRecommendation;
    }

    @Override
    public String toString() {
        return "HospitalReviewSummary [countOfRatingFive=" + countOfRatingFive + ", countOfRatingFour="
                + countOfRatingFour + ", countOfRatingThree=" + countOfRatingThree + ", countOfRatingTwo="
                + countOfRatingTwo + ", countOfRatingOne=" + countOfRatingOne + ", avgOverallRating=" + avgOverallRating
                + ", avgNurseComm=" + avgNurseComm + ", avgDoctorComm=" + avgDoctorComm + ", avgStaffResponsiveness="
                + avgStaffResponsiveness + ", avgCommAbtMed=" + avgCommAbtMed + ", avgDischargeInfo=" + avgDischargeInfo
                + ", avgCareTransition=" + avgCareTransition + ", avgCleanliness=" + avgCleanliness + ", avgQuietness="
                + avgQuietness + ", avgRecommendation=" + avgRecommendation + "]";
    }
    public JsonObject toJson(){

        return Json.createObjectBuilder()
            .add("countOfRatingFive",countOfRatingFive!=null?countOfRatingFive:0)
            .add("countOfRatingFour",countOfRatingFour!=null?countOfRatingFour:0)
            .add("countOfRatingThree",countOfRatingThree!=null?countOfRatingThree:0)
            .add("countOfRatingTwo",countOfRatingTwo!=null?countOfRatingTwo:0)
            .add("countOfRatingOne",countOfRatingOne!=null?countOfRatingOne:0)
            .add("avgOverallRating", avgOverallRating)
            .add("avgNurseComm",avgNurseComm)
            .add("avgDoctorComm",avgDoctorComm)
            .add("avgStaffResponsiveness",avgStaffResponsiveness)
            .add("avgCommAbtMed",avgCommAbtMed)
            .add("avgDischargeInfo",avgDischargeInfo)
            .add("avgCareTransition",avgCareTransition)
            .add("avgCleanliness",avgCleanliness)
            .add("avgQuietness",avgQuietness)
            .add("avgRecommendation",avgRecommendation)
            .build();
    }
}
