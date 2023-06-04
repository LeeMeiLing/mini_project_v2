import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  form!: FormGroup;
  token!: string | null;
  

  constructor(private fb:FormBuilder, private router:Router, private userSvc:UserService){}

  ngOnInit(): void {
    this.form = this.createForm()
  }

  createForm():FormGroup{
    return this.fb.group({
      userEmail:this.fb.control<string>('',[Validators.required, Validators.email]),
      userPassword:this.fb.control<string>('',[Validators.required])
    });
  }

  async login(){
    console.log(this.form.value)
    const result = await this.userSvc.authenticate(this.form.value);
    this.token = result.headers.get('Authorization')
    console.log("result: " , result);
    console.log("token: " , this.token);
  }

}
