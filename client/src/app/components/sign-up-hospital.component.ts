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
  waiting=false;
  hide=true;
  hidepassword=true;

  constructor(private fb:FormBuilder, private hospSvc:HospitalService, private web3Svc:Web3Service, private router:Router){}

  ngOnInit(): void {
    this.form = this.createForm()
  }

  /* PASSWORD 
    - consist only of non-whitespace characters
    - at least 8 characters
    - must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number
    - can contain special characters

  */
  createForm():FormGroup{
    return this.fb.group({

      privateKey:this.fb.control<string>('',[Validators.required, Validators.minLength(66), Validators.maxLength(66)]),
      accountPassword:this.fb.control<string>('',[Validators.required, Validators.pattern(new RegExp('(?=\\S*$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}'))]),
      facilityName:this.fb.control<string>('',[Validators.required]),
      license:this.fb.control<string>('',[Validators.required]),
      address:this.fb.control<string>('',[Validators.required]),
      streetName:this.fb.control<string>('',[Validators.required]),
      zipCode:this.fb.control<string >('',[Validators.required, Validators.minLength(6), Validators.maxLength(6)]),
      countryCode:this.fb.control<string>('',[Validators.required]),
      phoneNumber:this.fb.control<string>('',[Validators.required]),
      hospitalOwnership:this.fb.control<string>('',[Validators.required]),
      emergencyServices:this.fb.control<string>('',[Validators.required])

    });
  }

  async privateKeyNotMatched():Promise<boolean>{

    try{

      const currentAccount =  await this.web3Svc.web3.eth.requestAccounts(); // all lower case
      const currentAccountAddress= this.web3Svc.web3.utils.toChecksumAddress(currentAccount[0]); // convert to checksum form for comparison
      const fromKey = this.web3Svc.web3.eth.accounts.privateKeyToAccount(this.form.value['privateKey']);
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
    // console.log('keyStore: ', encryptedKeyStore);
    this.waiting=true;
    this.form.patchValue({privateKey:null}) // remove privateKey before sending to server

    this.form.addControl('encryptedKeyStore', this.fb.control(encryptedKeyStore))
    this.hospSvc.registerHospital(this.form.value).subscribe({
      next:()=>{},
      error:(err)=>{
        this.waiting=false;
        console.error(err)
        alert(err.error.error)
      },
      complete:()=>{
        this.waiting=false;
        alert('Registration Successful')
        console.log('registered success')
        this.router.navigate(['/'])
      }
    });
   
  }
}
