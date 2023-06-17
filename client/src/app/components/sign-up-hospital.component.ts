import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { Web3Service } from '../services/web3.service';

@Component({
  selector: 'app-sign-up-hospital',
  templateUrl: './sign-up-hospital.component.html',
  styleUrls: ['./sign-up-hospital.component.css']
})
export class SignUpHospitalComponent implements OnInit{

  form!: FormGroup;
  result$: any;

  constructor(private fb:FormBuilder, private router:Router, private userSvc:UserService, private web3Svc:Web3Service){}

  ngOnInit(): void {
    // this.form = this.createForm()
    this.web3Svc.web3.eth.getAccounts().then(console.log)

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

    facility_id varchar(6) not null,
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
  */
  createForm():FormGroup{
    return this.fb.group({
      ethAddress:this.fb.control<string>('',[Validators.required, Validators.minLength(42), Validators.maxLength(42)]),
      mohEthAddress:this.fb.control<string>('',[Validators.required, Validators.minLength(42), Validators.maxLength(42)]),
      accountPassword:this.fb.control<string>('',[Validators.required, Validators.pattern(new RegExp('(?=\\S*$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}'))]),
      license:this.fb.control<string>('',[Validators.required]),
      mobileNumber:this.fb.control<string>('',[Validators.required, Validators.minLength(8),Validators.maxLength(8)]),
      gender:this.fb.control<string>('',[Validators.required]),

    });
  }

  async register(){

    console.log(this.form.value); // debug
    this.result$ = await this.userSvc.registerNewUser(this.form.value) // return the user email if registered successfully
    console.log(this.result$.email); // debug
    this.router.navigate(['/']);
    
  }
}
