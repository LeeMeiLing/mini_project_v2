import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  form!: FormGroup;

  constructor(private fb:FormBuilder, private router:Router){}

  ngOnInit(): void {
    this.form = this.createForm()
  }

  
  createForm():FormGroup{
    return this.fb.group({
      userEmail:this.fb.control<string>('',[Validators.required, Validators.email]),
      userPassword:this.fb.control<string>('',[Validators.required])
    });
  }

  login():void{
    this.router.navigate(['/home']);
  }

}
