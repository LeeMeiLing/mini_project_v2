export interface Hospital {

    facilityId: string;
    facilityName: string;
    address: string;
    city: string;
    state: string;
    zipCode: string;
    countyName: string;
    phoneNumber: string;
    hospitalType: string;
    hospitalOwnership: string;
    emergencyServices: string;
    hospitalOverallRating: string;

}

export interface HospitalReview {

    id: number;
    facilityId: string;
    facilityEthAddress: string;
    ethReviewIndex: number;
    reviewer: string;
    patientNRIC: string;
    patientVerified: boolean;
    nurseCommunication: number;
    doctorCommunication: number;
    staffResponsiveness: number;
    communicationAboutMedicines: number;
    dischargeInformation: number;
    careTransition: number;
    cleanliness: number;
    quietness: number;
    overallRating: number;
    willingnessToRecommend: number;
    comments: string;
    reviewDate: string;

}

export interface ReviewSummary {

    countOfRatingFive: number;
    countOfRatingFour: number;
    countOfRatingThree: number;
    countOfRatingTwo: number;
    countOfRatingOne: number;
    avgOverallRating: number;
    avgNurseComm: number;
    avgDoctorComm: number;
    avgStaffResponsiveness: number;
    avgCommAbtMed: number;
    avgDischargeInfo: number;
    avgCareTransition: number;
    avgCleanliness: number;
    avgQuietness: number;
    avgRecommendation: number;

}

export interface HospitalSg{

    facilityId: string; // to be assign UUID
    facilityName: string;
    license: string;
    registered: boolean; // MOH to verify
    jciAccredited: boolean; // MOH to verify
    address: string; 
    streetName: string;
    zipCode: string;
    countryCode: string; // drop down option
    phoneNumber: string;
    hospitalOwnership: string; 
    emergencyServices: string; // yes or no
    ethAddress: string; // get from metamask
    contractAddress: string; // to be assign

}

export interface Statistic {

    mortality: number;
    patientSafety: number;
    readmission: number;
    patientExperience: number;
    effectiveness: number;
    timeliness: number;
    medicalImagingEfficiency: number;
    timestamp: string; 
    verified: boolean;

}

export interface Moh {
    countryCode: string,
    countryName: string,
    mohEthAddress: string
}