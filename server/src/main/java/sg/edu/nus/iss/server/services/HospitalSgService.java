package sg.edu.nus.iss.server.services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import sg.edu.nus.iss.server.exceptions.PostReviewFailedException;
import sg.edu.nus.iss.server.exceptions.RegisterHospitalFailedException;
import sg.edu.nus.iss.server.exceptions.ResultNotFoundException;
import sg.edu.nus.iss.server.exceptions.UpdateContractFailedException;
import sg.edu.nus.iss.server.exceptions.UpdateStatisticFailedException;
import sg.edu.nus.iss.server.exceptions.VerificationFailedException;
import sg.edu.nus.iss.server.exceptions.WrongPasswordException;
import sg.edu.nus.iss.server.models.HospitalCredentials;
import sg.edu.nus.iss.server.models.HospitalReview;
import sg.edu.nus.iss.server.models.HospitalReviewSummary;
import sg.edu.nus.iss.server.models.HospitalSg;
import sg.edu.nus.iss.server.models.Moh;
import sg.edu.nus.iss.server.models.Statistic;
import sg.edu.nus.iss.server.repositories.HospitalSgRepository;
import sg.edu.nus.iss.server.security.managers.CustomAuthenticationManager;
import sg.edu.nus.iss.server.security.managers.CustomAuthenticationManagerForHospital;
import sg.edu.nus.iss.server.security.managers.CustomAuthenticationManagerForMoh;

@Service
public class HospitalSgService {

    @Autowired
    private HospitalSgRepository hospSgRepo;

    @Autowired
    private EthereumService ethSvc;

    @Autowired
    private CustomAuthenticationManager customAuthenticationManager;

    @Autowired
    private CustomAuthenticationManagerForHospital customAuthenticationManagerForHospital;

    @Autowired
    private CustomAuthenticationManagerForMoh customAuthenticationManagerForMoh;

    public void registerMoh(Moh moh) throws RegisterHospitalFailedException{

        // check if eth address already used
        Integer exist = hospSgRepo.checkMohEthAddressExist(moh.getMohEthAddress());
        if(exist > 0){
            throw new RegisterHospitalFailedException("This ethereum account is already registered. Please use another account.");
        }

        hospSgRepo.insertMoh(moh);
    }
   
    @Transactional(rollbackFor = RegisterHospitalFailedException.class)
    public void registerHospitalSg(HospitalSg hospital, String accountPassword) throws Exception {

        // check if eth address already used
        Integer exist = hospSgRepo.checkHospEthAddressExist(hospital.getEthAddress());
        if(exist > 0){
            throw new RegisterHospitalFailedException("This ethereum account is already registered. Please use another account.");
        }

        // save to MySQL
        boolean saved = hospSgRepo.insertHospitalSg(hospital);

        if(!saved){
            throw new RegisterHospitalFailedException("Failed to insert HospitalSg into sg_hospitals");
        }

        // get MOH address from MySQL
        String mohEthAddress = hospSgRepo.getMohAddress(hospital.getCountryCode());

        HospitalCredentials contract;

        try {
            // deploy contract
            System.out.println(">>> trying to deploy contract");
            contract =  ethSvc.deployHospitalCredentialsContract(hospital.getEncryptedKeyStore(), accountPassword, 
            mohEthAddress, hospital.getLicense());

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RegisterHospitalFailedException("Failed to deploy Hospital Credentials contract");
        }

        // update contractAdress to MySQL
        boolean updated = hospSgRepo.updateContractAddress(hospital.getFacilityId(), contract.getContractAddress());

        if(!updated){
            throw new RegisterHospitalFailedException("failed to update contract address into sg_hospitals");
        }

    }

    @Transactional(rollbackFor = UpdateStatisticFailedException.class)
    public Integer updateStatistic(Statistic stat, String accountPassword) throws UpdateStatisticFailedException, WrongPasswordException{

        // get current user eth address
        String ethAddress = customAuthenticationManagerForHospital.getCurrentUser();
        byte[] keyStore = null;
        String contractAddress = null; 
        
        // get contract address & keystore by eth address
        Optional<HospitalSg> opt = hospSgRepo.findHospitalSgByEthAddress(ethAddress);

        if(opt.isPresent()) {
            HospitalSg hosp = opt.get();
            keyStore = hosp.getEncryptedKeyStore();
            contractAddress = hosp.getContractAddress();
        }else{
            throw new UpdateStatisticFailedException("hospitalSg not found for eth Address: " + ethAddress);
        }

        if(keyStore == null){
            throw new UpdateStatisticFailedException("keyStore not found for eth Address " + ethAddress);

        }

        if(contractAddress == null){
            throw new UpdateStatisticFailedException("contract address not found for eth Address " + ethAddress);

        }

        try{
            // update contract
            System.out.println(">>> attempting to update statistic in contract");

            BigInteger index = ethSvc.updateStatistic(stat, accountPassword, keyStore, contractAddress);
            System.out.println(">>> updated statistic in contract");

            // update MySQL
            boolean inserted = hospSgRepo.updateStatistic(index.intValue(), contractAddress); 

            if(!inserted){
                throw new UpdateStatisticFailedException("Failed to update statistic in MySQL");
            }

            return index.intValue();

        }catch(WrongPasswordException ex){
            throw new WrongPasswordException(ex.getMessage());
            
        }catch(Exception ex2){
            throw new UpdateStatisticFailedException(ex2.getMessage());
        }

    }

     public Statistic getStatisticByFacilityIdAndStatIndex(String facilityId,Integer statIndex){

        // if facilityId == null, get facilityId from current user
        if(facilityId == null){
            String ethAddress = customAuthenticationManagerForHospital.getCurrentUser();
            facilityId = hospSgRepo.findHospitalSgByEthAddress(ethAddress).get().getFacilityId();
        }

        // get contract address by facilityId
        Optional<HospitalSg> opt = hospSgRepo.findHospitalSgByFacilityId(facilityId);

        String contractAddress = null;

        if(opt.isPresent()){
            HospitalSg hosp = opt.get();
            contractAddress = hosp.getContractAddress();
        }
        
        if(contractAddress == null){
            throw new ResultNotFoundException("facilityId " + facilityId);
        }

        try{
            // get statistic from contract
            Statistic stat = ethSvc.getStatistic(contractAddress, statIndex);
            return stat;

        }catch(Exception ex){
            throw new ResultNotFoundException("statistic " + statIndex );

        }

    }

    public List<Statistic> getStatisticListByHospital(String facilityId){

        // get contract address by facilityId
        String contractAddress = hospSgRepo.findHospitalSgByFacilityId(facilityId).get().getContractAddress();

        try{
            return ethSvc.getAllStatistic(contractAddress);

        }catch(Exception ex){
            throw new ResultNotFoundException("Failed to get statistic list");
        }

    }

    public List<HospitalSg> getHospitalsByPendingVerify(){

        Optional<List<HospitalSg>> opt = hospSgRepo.getHospitalsByPendingVerify();

        if(opt.isPresent()){
            return opt.get(); // return empty list if not found
        }

        return null;
    }

    public List<HospitalSg> getHospitalsByStatPendingVerify() throws Exception {

        // ====Get Unverified stat from eth contract =========
        // get distinct stat contract address
        List<String> contracts = hospSgRepo.getContractAddressFromStat(); // if remove stat table, find distinct contract from sg_hospitals
        List<String> hospitalWithStatPending = new ArrayList<>();

        for(String c : contracts){

            List<Integer> unverifiedStatIndex = ethSvc.getUnverifiedStatIndex(c);

            if(!unverifiedStatIndex.isEmpty()){
                hospitalWithStatPending.add(c);
            }
        }

        // get HospitalSg by contract address & add to a list of HospitalSg
        List<HospitalSg> hospitals = new ArrayList<>();
        for(String c : hospitalWithStatPending){

            Optional<HospitalSg> opt = hospSgRepo.findHospitalSgByContractAddress(c);
            if(opt.isPresent()){
                hospitals.add(opt.get());
            }
        } 

        // return the list of HospitalSg
        return hospitals;

    }

   

    public List<HospitalSg> getHospitalsSgList(String hospitalOwnership, String name, Integer offset,
            Boolean sortByRating, Boolean descending) {

        Optional<List<HospitalSg>> opt;

        if (hospitalOwnership != null) {

            if (name != null) {
                // search with hospitalOwnership & name
                opt = hospSgRepo.findHospitalsByOwnershipAndName(hospitalOwnership, name, offset, sortByRating, descending);
            } else {
                // search with hospitalOwnership
                opt = hospSgRepo.findHospitalsByOwnership(hospitalOwnership, offset, sortByRating, descending);
            }
        } else {

            if (name != null) {
                // search with name
                opt = hospSgRepo.findHospitalsByName(name, offset, sortByRating, descending);
            } else {
                // search all without filter
                opt = hospSgRepo.findAllHospitals(offset, sortByRating, descending);
            }
        }

        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new ResultNotFoundException("Hospital");
        }

    }

    public HospitalSg findHospitalSgById(String facilityId) {

        Optional<HospitalSg> opt = hospSgRepo.findHospitalSgByFacilityId(facilityId);

        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new ResultNotFoundException("Hospital ID: " + facilityId);
        }
    }

    public Integer getHospitalSgReviewCountByFacilityId(String facilityId) {

        return hospSgRepo.getReviewCountByFacilityId(facilityId);
    }

    @Transactional(rollbackFor = PostReviewFailedException.class)
    public boolean postHospitalReview(String facilityId, HospitalReview review) throws Exception {

        review.setFacilityId(facilityId);

        // retrieve current user
        review.setReviewer(customAuthenticationManager.getCurrentUser());

        // set timestamp
        review.setReviewDate(new java.sql.Date(new Date().getTime()));

        // ===== perform in transaction =====

        // save to MySql & get id >> for user to see own review
        Integer reviewId = hospSgRepo.insertHospitalReview(review);

        // update sg_hospital hospital_overall_rating
        Float newAvgRating = getHospitalSgReviewSummary(facilityId).getAvgOverallRating();

        boolean updated = hospSgRepo.updateHospitalSgOverallRating(newAvgRating, facilityId);
        if(!updated){
            throw new PostReviewFailedException("Failed to update new overall rating into sg_hospitals");
        }

        // save to Smart Contract & get ethReviewIndex >> for hospital owner to
        // verify Patient
        if (reviewId > 0) {

            // get contract address from MySql
            String contractAddress = findHospitalSgById(facilityId).getContractAddress();

            // load the contract
            HospitalCredentials contract = ethSvc.loadHospitalCredentialsContract(contractAddress, null);

            // generate hash: md5(comments)
            String toHash = review.getComments();
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            // digest() method is called to calculate message digest of an input, digest()
            // return array of byte
            byte[] digest = md.digest(toHash.getBytes());
            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, digest);

            try {
                // add review to Eth Smart Contract
                TransactionReceipt ethReviewIndex = contract
                        .addReview(new BigInteger(String.valueOf(reviewId)), review.getPatientId(),
                                new BigInteger(String.valueOf(review.getOverallRating())), no)
                        .send();
                String returnedIndex = ethReviewIndex.getLogs().get(0).getData();
                Integer reviewIndex = Integer.decode(returnedIndex);
                // save review index to review table
                boolean reviewIndexSaved = hospSgRepo.saveEthReviewIndex(reviewId, reviewIndex);

                if (!reviewIndexSaved) {
                    throw new PostReviewFailedException("Error in hospitalRepo.saveEthReviewIndex()");
                }


            } catch (Exception ex) {
                ex.printStackTrace();
                throw new PostReviewFailedException("Error interacting with smart contract: " + ex);

            }

            // // TODO: check if comments has been amended , FOR USE DURING READ REVIEW
            // if (no.equals(contract.reviews(new BigInteger(String.valueOf(review_index))).send().component5())) {
            //     System.out.println("hashMessage is match: !!!");
            // }

        } else {
            throw new PostReviewFailedException("Error in hospitalRepo.insertHospitalReview()");
        }

        return true;

    }

    public List<HospitalReview> getHospitalReviews(String facilityId) {

        Optional<List<HospitalReview>> opt = hospSgRepo.getHospitalReviews(facilityId);

        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new ResultNotFoundException("Review ");
        }
    }

    public HospitalReviewSummary getHospitalSgReviewSummary(String facilityId) {

        return hospSgRepo.getHospitalReviewSummary(facilityId);
       
    }

    public Integer getLatestVerifiedStatIndex(String facilityId) throws Exception{

        // get hosp contract address
        String contractAddress = hospSgRepo.findHospitalSgByFacilityId(facilityId).get().getContractAddress();

        // loop through stat to find the latest verified stat then return the index
        return ethSvc.getLatestVerifiedStatIndex(contractAddress);

    }

    
    public boolean verifyLicense(String facilityId, String accountPassword) throws Exception {

        HospitalSg hospital = hospSgRepo.findHospitalSgByFacilityId(facilityId).get();

        Moh moh = customAuthenticationManagerForMoh.getMoh();

        // update contract registered
        try{
            ethSvc.verifyLicense(hospital.getContractAddress(),moh.getEncryptedKeyStore(),accountPassword);
        }catch(Exception ex){
            if(ex instanceof WrongPasswordException){
                throw ex;
            }
            throw new VerificationFailedException("Failed to verify license in smart contract");
        }

        // update MySQL sg_hospitals
        boolean updated = hospSgRepo.updateHospitalSgRegistered(facilityId);

        if(updated){
            return true;
        }else{
            throw new VerificationFailedException("Failed to update sg_hospitals registered column");
        }

    }

     public boolean verifyJci(String facilityId, String accountPassword) throws Exception {

        HospitalSg hospital = hospSgRepo.findHospitalSgByFacilityId(facilityId).get();

        Moh moh = customAuthenticationManagerForMoh.getMoh();

        // update contract registered
        try{
            ethSvc.verifyJci(hospital.getContractAddress(),moh.getEncryptedKeyStore(),accountPassword);
        }catch(Exception ex){
            if(ex instanceof WrongPasswordException){
                throw ex;
            }
            throw new VerificationFailedException("Failed to verify JCI accreditation in smart contract");
        }

        // update MySQL sg_hospitals
        boolean updated = hospSgRepo.updateHospitalSgJci(facilityId);

        if(updated){
            return true;
        }else{
            throw new VerificationFailedException("Failed to update sg_hospitals jciAccredited column");
        }

    }

    public boolean setUpdateFrequencyandPenalty(String facilityId, String updateFrequency, String penalty, String accountPassword) throws UpdateContractFailedException, WrongPasswordException {
        
        HospitalSg hospital = hospSgRepo.findHospitalSgByFacilityId(facilityId).get();

        Moh moh = customAuthenticationManagerForMoh.getMoh();

        ethSvc.setUpdateFrequencyandPenalty(hospital.getContractAddress(), moh.getEncryptedKeyStore() , accountPassword, updateFrequency, penalty);
        
        return true;
       
    }

    public Integer getUpdateFrequency(String facilityId) throws Exception{

        HospitalSg hospital = hospSgRepo.findHospitalSgByFacilityId(facilityId).get();
        return ethSvc.getUpdateFrequency(hospital.getContractAddress()).intValue();
    }

    public Integer getPenalty(String facilityId) throws Exception{

        HospitalSg hospital = hospSgRepo.findHospitalSgByFacilityId(facilityId).get();
        return ethSvc.getPenalty(hospital.getContractAddress()).intValue();
    }

    // by moh
    public boolean verifyStatistic(String facilityId, Integer statIndex , String accountPassword) throws Exception {
        
        HospitalSg hospital = hospSgRepo.findHospitalSgByFacilityId(facilityId).get();

        Moh moh = customAuthenticationManagerForMoh.getMoh();

        // update contract registered
        try{
            ethSvc.verifyStatistic(hospital.getContractAddress(),moh.getEncryptedKeyStore(), statIndex, accountPassword);
            return true;
        }catch(Exception ex){
            if(ex instanceof WrongPasswordException){
                throw ex;
            }
            throw new VerificationFailedException("Failed to verify statistic in smart contract");
        }

    }

    // TODO: hospital owner to verify
    public boolean verifyPatient() {
        
        return false;
    }

    

}
