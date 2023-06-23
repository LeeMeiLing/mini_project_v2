// SPDX-License-Identifier: MIT
pragma solidity ^0.8.18;

contract EthHospitalReview{
    
    address public MOH; // approver
    PatientReview[] public reviews;

    // user identity verified by app
    // patient identity verified by MOH
    struct PatientReview{

        string facilityId;
        uint reviewId;
        string patientNRIC;
        bool patientVerified;
        uint8 overallRating;
        uint hashedMessage;

    }

    constructor () {

        MOH = msg.sender;

    }

    function addReview(string memory _facilityId, uint _reviewId, string memory _patientNRIC, uint8  _overallRating, uint _hashedMessage) public{

        PatientReview storage newReview = reviews.push();
        newReview.facilityId = _facilityId;
        newReview.reviewId = _reviewId;
        newReview.patientNRIC = _patientNRIC;
        newReview.overallRating = _overallRating;
        newReview.hashedMessage = _hashedMessage;
        // return reviews.length-1;
        emit reviewIndex(reviews.length - 1);

    }


    event reviewIndex(uint _index);

    function verifyPatient(uint _index) public {
        PatientReview storage toVerify = reviews[_index];
        toVerify.patientVerified = true;
    }

    /*
        Nurse communication 
        Doctor communication 
        Responsiveness of hospital staff 
        Communication about medicines 
        Discharge information 
        Care transition 
        
        Cleanliness of hospital environment 
        Quietness of hospital environment 
     
        Hospital rating
        Willingness to recommend hospital
        
    */
}
