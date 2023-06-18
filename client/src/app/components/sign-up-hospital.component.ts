import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { Web3Service } from '../services/web3.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-sign-up-hospital',
  templateUrl: './sign-up-hospital.component.html',
  styleUrls: ['./sign-up-hospital.component.css']
})
export class SignUpHospitalComponent implements OnInit{

  form!: FormGroup;
  result$: any;

  constructor(private fb:FormBuilder, private router:Router, private userSvc:UserService, private web3Svc:Web3Service, private http:HttpClient){}

  ngOnInit(): void {
    this.form = this.createForm()
    // this.web3Svc.web3.eth.getAccounts().then(console.log)

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

  // createFormMOH():FormGroup{
  //   return this.fb.group({

  //     updateFrequency:this.fb.control<string>('',[Validators.required]) ,
  //     registered; // MOH to verify
  //     jciAccredited; // MOH to verify
  //     penalty; // MOH to set penalty rule

  //   });
  // }

  async register(){

    const accounts = await this.web3Svc.web3.eth.requestAccounts();
    const acc = accounts[0];
    // const acc = this.web3Svc.web3.eth.accounts.encrypt(privatekey, password);
    console.log(acc)

    // // POST /api/hospitals/hospital/{facilityId}/review
    // const headers = new HttpHeaders().set('Accept','application/json')
    //                                  .set('Content-Type', 'application/json');

    // const payload = acc 
    // this.http.post(`http://localhost:8080/api/hospitals/hospital/testaccount`, payload , { headers }).subscribe(); // todo


    // console.log(this.form.value); // debug
    // this.result$ = await this.userSvc.registerNewUser(this.form.value) // return the user email if registered successfully
    // console.log(this.result$.email); // debug
    // this.router.navigate(['/']);
    
  }
}
