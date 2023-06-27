import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { JwtCookieService } from '../services/jwt-cookie.service';
import { HospitalService } from '../services/hospital.service';
import { Moh } from '../models';
import { Web3Service } from '../services/web3.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  form!: FormGroup;
  userForm!: FormGroup;
  hospitalForm!: FormGroup;
  mohForm!: FormGroup;
  token!: string | null;
  showUserForm:boolean = true;
  showHospitalForm!: boolean;
  showMohForm!: boolean;
  mohList!: Moh[];
  hide=true;

  constructor(private fb:FormBuilder, private router:Router, private userSvc:UserService, 
    private hospSvc:HospitalService, private jwtCookieSvc: JwtCookieService, private web3Svc:Web3Service){}

  ngOnInit(): void {
    this.jwtCookieSvc.deleteJwt();
    this.form = this.createForm();
    this.userForm = this.createUserForm();
  }

  createForm(){
    return this.fb.group({
      signInMethod:this.fb.control<string>('user')
    });
  }

  onChange(){
    this.showUserForm = false;
    this.showHospitalForm = false;
    this.showMohForm = false;

    if(this.form.value['signInMethod'] == 'user'){
      this.showUserForm = true;
      this.userForm = this.createUserForm();
    }
    if(this.form.value['signInMethod'] == 'hospital'){
      this.showHospitalForm = true;
      this.hospitalForm = this.createHospitalForm();
    }
    if(this.form.value['signInMethod'] == 'moh'){
      this.showMohForm = true;
      this.mohForm = this.createMohForm();
      this.hospSvc.getMohList().subscribe({
        next: (r:any)=>{
          this.mohList = r as Moh[];
        },
        error: (err)=>{
          console.error(err)
        },
        complete: ()=>{
          console.log('completed getMohList')
        }
      });
    }
  }

  createUserForm():FormGroup{
    return this.fb.group({
      userEmail:this.fb.control<string>('',[Validators.required, Validators.email]),
      userPassword:this.fb.control<string>('',[Validators.required])
    });
  }

  createHospitalForm():FormGroup{
    return this.fb.group({
      ethAddress:this.fb.control<string>('',[Validators.required, Validators.minLength(42), Validators.maxLength(42)]),
      accountPassword:this.fb.control<string>('',[Validators.required])
    });
  }

 
  createMohForm():FormGroup{
    return this.fb.group({
      countryCode:this.fb.control<string>('',[Validators.required]),
      accountPassword:this.fb.control<string>('',[Validators.required])
    });
  }

  async signInAsUser(){
    
    try{

      const result = await this.userSvc.authenticate(this.userForm.value);
      const status = result.status
      
      this.router.navigate(['/searchHospital']);

    }catch(err:any){

      console.error(err); // err is a HttpErrorResponse


      if(err.status == 400){ // wrong username
        alert(err.error)
      }
      if(err.status == 401){ // wrong password
        alert(err.error)
      }

    }

  }

  async signInAsHospital(){

    try{

      const result = await this.hospSvc.authenticateHospital(this.hospitalForm.value);
      const status = result.status

      this.router.navigate(['/home/hospital']);

    }catch(err:any){

      console.error(err); // err is a HttpErrorResponse

      if(err.status == 400){ // wrong eth Address
        alert(err.error)
      }
      if(err.status == 401){ // wrong account password
        alert(err.error)
      }

    }

  }

  async signInAsMoh(){

    try{

      const currentAccount =  await this.web3Svc.web3.eth.requestAccounts(); // all lower case
      // const currentAccountAddress= this.web3Svc.web3.utils.toChecksumAddress(currentAccount[0]); // convert to checksum form for comparison
      const currentAccountAddress = currentAccount[0];
      const selectedMoh = this.mohList.find(moh => moh.countryCode == this.mohForm.value['countryCode']);

      if(currentAccountAddress === selectedMoh!['mohEthAddress'].toLowerCase()){
        
        console.log('address matched! '); // debug

        try{

          const authMohForm = {
            mohEthAddress: currentAccountAddress,
            accountPassword: this.mohForm.value['accountPassword']
          }

          const result = await this.hospSvc.authenticateMoh(authMohForm);
          console.log('result: ', result) // debug
          const status = result.status
          console.log('sign in as moh, status ', status) // debug

          const cc = this.mohForm.value['countryCode'].toLowerCase();
          this.router.navigate([`/home/moh/${cc}`]);

    
        }catch(err:any){
    
          console.error(err); // err is a HttpErrorResponse
          // display login error
    
          if(err.status == 400){ // wrong eth Address
            alert(err.error)
          }
          if(err.status == 401){ // wrong account password
            alert(err.error)
          }
    
        }
      }else{
        alert('Please make sure you are currently connected using the correct account on MetaMask ');
      } 
     
    }catch(err){

      alert(err);
      console.error(err);

    }

  }

  

}
