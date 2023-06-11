export interface Hospital{
    
    facilityId:string;
    facilityName:string;
    address:string;
    city:string;
    state:string;
    zipCode:string;
    countyName:string;
    phoneNumber:string;
    hospitalType:string;
    hospitalOwnership:string;
    emergencyServices:string;
    hospitalOverallRating:string;
  
}

export interface HospitalReview{
    
    id:number;
    facilityId:string;
    facilityEthAddress:string;
    ethReviewIndex:number;
    reviewer:string;
    patientNRIC:string;
    patientVerified:boolean;
    nurseCommunication:number;
    doctorCommunication:number;
	staffResponsiveness:number;
	communicationAboutMedicines:number;
	dischargeInformation:number;
	careTransition:number;
	cleanliness:number;
	quientness:number;
	overallRating:number;
	willingnessToRecommend:number;
    comments:string;
  
}