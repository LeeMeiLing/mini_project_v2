import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { JwtCookieService } from '../services/jwt-cookie.service';

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

  constructor(private fb:FormBuilder, private router:Router, private userSvc:UserService, private jwtCookieSvc: JwtCookieService){}

  ngOnInit(): void {
    console.log('>> in OnInit');
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

  // TODO:
  createMohForm():FormGroup{
    return this.fb.group({
      ethAddress:this.fb.control<string>('',[Validators.required, Validators.minLength(42), Validators.maxLength(42)]),
      accountPassword:this.fb.control<string>('',[Validators.required])
    });
  }

  async signInAsUser(){
    
    try{

      const result = await this.userSvc.authenticate(this.userForm.value);
      console.log('result: ', result) // debug
      const status = result.status
      console.log('status ', status) // debug
      
      this.router.navigate(['/home']);

    }catch(err:any){

      console.error(err); // err is a HttpErrorResponse
      // display login error

      if(err.status == 400){ // wrong username
        alert(err.error)
      }
      if(err.status == 401){ // wrong password
        alert(err.error)
      }

    }

  }

  // TODO
  async signInAsHospital(){
    
    try{

      const result = await this.userSvc.authenticate(this.hospitalForm.value);
      console.log('result: ', result) // debug
      const status = result.status
      console.log('status ', status) // debug
      
      this.router.navigate(['/home']);

    }catch(err:any){

      console.error(err); // err is a HttpErrorResponse
      // display login error

      if(err.status == 400){ // wrong username
        alert(err.error)
      }
      if(err.status == 401){ // wrong password
        alert(err.error)
      }

    }

  
  }

}
