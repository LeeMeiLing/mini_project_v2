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
  token!: string | null;
  

  constructor(private fb:FormBuilder, private router:Router, private userSvc:UserService, private jwtCookieSvc: JwtCookieService){}

  ngOnInit(): void {
    console.log('>> in OnInit');
    this.jwtCookieSvc.deleteJwt();
    this.form = this.createForm();
  }

  createForm():FormGroup{
    return this.fb.group({
      userEmail:this.fb.control<string>('',[Validators.required, Validators.email]),
      userPassword:this.fb.control<string>('',[Validators.required])
    });
  }


  async login(){
    console.log(this.form.value);
    
    try{

      const result = await this.userSvc.authenticate(this.form.value);
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

      // this.userSvc.authenticate(this.form.value).then(
      //   v => { this.router.navigate(['/home']);}
      // ).catch(
      //   err => console.error(err)
      // )
  
  }

}
