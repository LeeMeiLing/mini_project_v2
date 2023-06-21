// SPDX-License-Identifier: MIT
pragma solidity ^0.8.18;

contract HospitalCredentials{

    /*
        track records, success rates, patient reviews, and qualifications, 
        Joint Commission International (JCI) accreditation
        Mortality (22 percent)
        Safety of care (22 percent)
        Readmissions (22 percent)
        Patient experience (22 percent)
        Effectiveness of care (4 percent)
        Timeliness of care (4 percent)
        Efficient use of medical imaging (4 percent)
    */

    address public hospital; // account owner, responsible to update data
    address public MOH; // approver, responsible to verify data, set update frequency
    string public license;
    uint public updateFrequency; // in days, determined by MOH
    bool public registered; // MOH to verify
    bool public jciAccredited; // MOH to verify
    Statistic[] public statistics;
    uint public lastUpdate;
    uint public penalty;
    PatientReview[] public reviews;

    struct Statistic{
        uint mortality;
        uint patientSafety;
        uint readmission;
        uint patientExperience;
        uint effectiveness;
        uint timeliness;
        uint medicalImagingEfficiency;
        uint timestamp;
        bool verified;
    }
    
    // user identity verified by app
    // patient identity verified by hospital
    struct PatientReview{

        uint reviewId;
        string patientNRIC;
        bool patientVerified;
        uint8 overallRating;
        bytes16 hashedMessage;

    }


    // address & hospital name saved in mysql
    constructor (address _MOH, string memory _licenese) {

        hospital = msg.sender;
        MOH = _MOH;
        license = _licenese;

    }

    modifier approver(){
        require(msg.sender == MOH);
        _;
    }

    modifier accOwner(){
        require(msg.sender == hospital);
        _;
    }

    function verifyLicense() public approver{
        registered = true;
    }

    function verifyStatistic() public approver{
        uint index = statistics.length - 1;
        Statistic storage stat = statistics[index];
        stat.verified = true;
    }

    function getLatestStat() public view returns(Statistic memory){
        uint index = statistics.length - 1;
        return statistics[index];
    }

    function getStatisticsSize() public view returns(uint){
        return statistics.length;
    }

    function updateStatistic( 
        uint _mortality,
        uint _patientSafety,
        uint _readmission,
        uint _patientExperience,
        uint _effectiveness,
        uint _timeliness,
        uint _medicalImagingEfficiency ) public payable accOwner
    {
        bool penaltyRequired = false;

        // only check for late update if lastUpdate > 0
        if(lastUpdate > 0){
            if( block.timestamp > (lastUpdate + updateFrequency * 1 days) ) {
                penaltyRequired = true;
            }
        }

        if(penaltyRequired){
            if(msg.value >= penalty * 1 wei){
                bool success = payable(MOH).send(msg.value);
                if(success){
                    penaltyRequired = false;
                }
            }
        }

        require(!penaltyRequired, "Please pay penalty for late update");

        Statistic storage newStat = statistics.push();
        newStat.mortality = _mortality;
        newStat.patientSafety = _patientSafety;
        newStat.readmission = _readmission;
        newStat.patientExperience = _patientExperience;
        newStat.effectiveness = _effectiveness;
        newStat.timeliness = _timeliness;
        newStat.medicalImagingEfficiency = _medicalImagingEfficiency;
        newStat.timestamp = block.timestamp;
        newStat.verified = false;

        lastUpdate = newStat.timestamp;
    }

    function setUpdateFrequency(uint _days) public approver{
        updateFrequency = _days;
    }

    function setPenalty(uint _wei) public approver{
        penalty = _wei;
    }

     function addReview(uint _reviewId, string memory _patientNRIC, uint8  _overallRating, bytes16 _hashedMessage) public returns(uint index){

        PatientReview storage newReview = reviews.push();
        newReview.reviewId = _reviewId;
        newReview.patientNRIC = _patientNRIC;
        newReview.overallRating = _overallRating;
        newReview.hashedMessage = _hashedMessage;
        return reviews.length-1;

    }

    function verifyPatient(uint _index) public accOwner{
        PatientReview storage toVerify = reviews[_index];
        toVerify.patientVerified = true;
    }
    
}


// contract HospitalCredentials{

//     /*
//         track records, success rates, patient reviews, and qualifications, 
//         Joint Commission International (JCI) accreditation
//         Mortality (22 percent)
//         Safety of care (22 percent)
//         Readmissions (22 percent)
//         Patient experience (22 percent)
//         Effectiveness of care (4 percent)
//         Timeliness of care (4 percent)
//         Efficient use of medical imaging (4 percent)
//     */

//     address public hospital; // account owner, responsible to update data
//     address public MOH; // approver, responsible to verify data, set update frequency
//     string public license;
//     uint public updateFrequency; // in days, determined by MOH
//     bool public registered; // MOH to verify
//     bool public jciAccredited; // MOH to verify
//     Statistic[] public statistics;
//     uint public lastUpdate;
//     uint public penalty;
//     PatientReview[] public reviews;

//     struct Statistic{
//         uint mortality;
//         uint patientSafety;
//         uint readmission;
//         uint patientExperience;
//         uint effectiveness;
//         uint timeliness;
//         uint medicalImagingEfficiency;
//         uint timestamp;
//         bool verified;
//     }
    
//     // user identity verified by app
//     // patient identity verified by hospital
//     struct PatientReview{

//         uint reviewId;
//         string patientNRIC;
//         bool patientVerified;
//         uint8 overallRating;
//         bytes16 hashedMessage;

//     }


//     // address & hospital name saved in mysql
//     constructor (address _MOH, string memory _licenese) {

//         hospital = msg.sender;
//         MOH = _MOH;
//         license = _licenese;

//     }

//     modifier approver(){
//         require(msg.sender == MOH);
//         _;
//     }

//     modifier accOwner(){
//         require(msg.sender == hospital);
//         _;
//     }

//     function verifyLicense() public approver{
//         registered = true;
//     }

//     function verifyStatistic() public approver{
//         uint index = statistics.length - 1;
//         Statistic storage stat = statistics[index];
//         stat.verified = true;
//     }

//     function getLatestStat() public view returns(Statistic memory){
//         uint index = statistics.length - 1;
//         return statistics[index];
//     }

//     function getStatisticsSize() public view returns(uint){
//         return statistics.length;
//     }

//     function updateStatistic( uint _mortality,
//         uint _patientSafety,
//         uint _readmission,
//         uint _patientExperience,
//         uint _effectiveness,
//         uint _timeliness,
//         uint _medicalImagingEfficiency ) public payable accOwner
//     {
//         bool penaltyRequired = false;

//         // only check for late update if lastUpdate > 0
//         if(lastUpdate > 0){
//             if( block.timestamp > (lastUpdate + updateFrequency * 1 days) ) {
//                 penaltyRequired = true;
//             }
//         }

//         if(penaltyRequired){
//             if(msg.value >= penalty * 1 wei){
//                 bool success = payable(MOH).send(msg.value);
//                 if(success){
//                     penaltyRequired = false;
//                 }
//             }
//         }

//         require(!penaltyRequired, "Please pay penalty for late update");

//         Statistic storage newStat = statistics.push();
//         newStat.mortality = _mortality;
//         newStat.patientSafety = _patientSafety;
//         newStat.readmission = _readmission;
//         newStat.patientExperience = _patientExperience;
//         newStat.effectiveness = _effectiveness;
//         newStat.timeliness = _timeliness;
//         newStat.medicalImagingEfficiency = _medicalImagingEfficiency;
//         newStat.timestamp = block.timestamp;
//         newStat.verified = false;

//         lastUpdate = newStat.timestamp;
//     }

//     function setUpdateFrequency(uint _days) public approver{
//         updateFrequency = _days;
//     }

//     function setPenalty(uint _wei) public approver{
//         penalty = _wei;
//     }

//      function addReview(uint _reviewId, string memory _patientNRIC, uint8  _overallRating, bytes16 _hashedMessage) public returns(uint index){

//         PatientReview storage newReview = reviews.push();
//         newReview.reviewId = _reviewId;
//         newReview.patientNRIC = _patientNRIC;
//         newReview.overallRating = _overallRating;
//         newReview.hashedMessage = _hashedMessage;
//         return reviews.length-1;

//     }

//     function verifyPatient(uint _index) public accOwner{
//         PatientReview storage toVerify = reviews[_index];
//         toVerify.patientVerified = true;
//     }
    
// }