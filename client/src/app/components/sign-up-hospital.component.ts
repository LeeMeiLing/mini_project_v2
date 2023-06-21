import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Web3Service } from '../services/web3.service';
import { HospitalService } from '../services/hospital.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sign-up-hospital',
  templateUrl: './sign-up-hospital.component.html',
  styleUrls: ['./sign-up-hospital.component.css']
})
export class SignUpHospitalComponent implements OnInit{

  form!: FormGroup;
  result$: any;

  constructor(private fb:FormBuilder, private hospSvc:HospitalService, private web3Svc:Web3Service, private router:Router){}

  ngOnInit(): void {
    this.form = this.createForm()
  }

  /* PASSWORD 
    - consist only of non-whitespace characters
    - at least 8 characters
    - must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number
    - can contain special characters

    address public hospital; // account owner, responsible to update data
    address public MOH; // approver, responsible to verify data, set update frequency
    uint public license;
    uint public updateFrequency; // in days, determined by MOH
    bool public registered; // MOH to verify
    bool public jciAccredited; // MOH to verify
    Statistic[] public statistics;
    uint public lastUpdate;
    uint public penalty;
    PatientReview[] public reviews;

    facility_id varchar(6) not null,  // server generate UUID
    facility_name varchar(84),
    address varchar(51),
    city varchar(20),
    state varchar(2),
    zip_code varchar(8),
    county_name varchar(25),
    phone_number varchar(14),
    hospital_type varchar(34),
    hospital_ownership varchar(43),
    emergency_services varchar(3),
    hospital_overall_rating varchar(13),
    eth_address varchar(42),
    review_contract_address varchar(42),

    private String facilityId; // to be assign UUID
    private String facilityName;
    private String license;
    private boolean registered; // MOH to verify
    private boolean jeciAccredited; // MOH to verify
    private String address; 
    private String streetName;
    private String zipCode;
    private String countryCode; // drop down option
    private String phoneNumber;
    // private String hospitalType;
    private String hospitalOwnership; 
    private String emergencyServices; // yes or no
    private String hospitalOverallRating; 
    private String ethAddress; // get from metamask
    private String reviewContractAddress; // to be assign
    private String accountPassword; // to be encrypted
  */
  createForm():FormGroup{
    return this.fb.group({

      // ethAddress:this.fb.control<string>('',[Validators.required, Validators.minLength(42), Validators.maxLength(42)]),
      privateKey:this.fb.control<string>('',[Validators.required, Validators.minLength(66), Validators.maxLength(66)]),
      accountPassword:this.fb.control<string>('',[Validators.required, Validators.pattern(new RegExp('(?=\\S*$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}'))]),
      facilityName:this.fb.control<string>('',[Validators.required]),
      license:this.fb.control<string>('',[Validators.required]),
      address:this.fb.control<string>('',[Validators.required]),
      streetName:this.fb.control<string>('',[Validators.required]),
      zipCode:this.fb.control<string >('',[Validators.required, Validators.minLength(6), Validators.maxLength(6)]),
      countryCode:this.fb.control<string>('',[Validators.required]),
      phoneNumber:this.fb.control<string>('',[Validators.required]),
      // hospitalType:this.fb.control<string>('',[Validators.required]),
      hospitalOwnership:this.fb.control<string>('',[Validators.required]),
      emergencyServices:this.fb.control<string>('',[Validators.required])

    });
  }

  async privateKeyNotMatched():Promise<boolean>{

    try{

      const currentAccount =  await this.web3Svc.web3.eth.requestAccounts(); // all lower case
      const currentAccountAddress= this.web3Svc.web3.utils.toChecksumAddress(currentAccount[0]); // convert to checksum form for comparison
      // console.log(currentAccountAddress);
      const fromKey = this.web3Svc.web3.eth.accounts.privateKeyToAccount(this.form.value['privateKey']);
      // console.log(fromKey);
      if(currentAccountAddress === fromKey.address){
        return false;
      }
      return true;

    }catch(err){

      alert(err);
      console.error(err);
      return true;

    }

  }

  async register(){

    if(await this.privateKeyNotMatched()){
      alert("Private key invalid. Please connect to the correct account and enter a valid private key.");
      return;
    }

    const encryptedKeyStore = await this.web3Svc.web3.eth.accounts.encrypt(this.form.value['privateKey'], this.form.value['accountPassword']);
    console.log('keyStore: ', encryptedKeyStore);
    this.form.patchValue({privateKey:null}) // remove privateKey before sending to server

    this.form.addControl('encryptedKeyStore', this.fb.control(encryptedKeyStore))
    console.log('form: ', this.form)
    this.hospSvc.registerHospital(this.form.value).subscribe({
      next:()=>{},
      error:(err)=>{
        console.error(err)
      },
      complete:()=>{
        console.log('registered success')
        this.router.navigate(['/'])
      }
    });
   
  }
}
