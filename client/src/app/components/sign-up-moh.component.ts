import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Web3Service } from '../services/web3.service';
import { HospitalService } from '../services/hospital.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sign-up-moh',
  templateUrl: './sign-up-moh.component.html',
  styleUrls: ['./sign-up-moh.component.css']
})
export class SignUpMohComponent {
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

  */
  createForm():FormGroup{
    return this.fb.group({

      privateKey:this.fb.control<string>('',[Validators.required, Validators.minLength(66), Validators.maxLength(66)]),
      accountPassword:this.fb.control<string>('',[Validators.required, Validators.pattern(new RegExp('(?=\\S*$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}'))]),
      countryCode:this.fb.control<string>('',[Validators.required]),
      countryName:this.fb.control<string>('',[Validators.required]),

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
    console.log('keyStore: ', encryptedKeyStore);
    this.form.patchValue({privateKey:null}) // remove privateKey before sending to server

    this.form.addControl('encryptedKeyStore', this.fb.control(encryptedKeyStore))
    console.log('form: ', this.form)
    this.hospSvc.registerMoh(this.form.value).subscribe({
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
