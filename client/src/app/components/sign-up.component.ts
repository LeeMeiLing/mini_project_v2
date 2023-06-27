import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit{

  form!: FormGroup;
  result$: any;
  hide = true;

  constructor(private fb:FormBuilder, private router:Router, private userSvc:UserService){}

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
      userEmail:this.fb.control<string>('',[Validators.required, Validators.email]),
      userPassword:this.fb.control<string>('',[Validators.required, Validators.pattern(new RegExp('(?=\\S*$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}'))]),
      userName:this.fb.control<string>('',[Validators.required, Validators.minLength(3)]),
      mobileNumber:this.fb.control<string>('',[Validators.required, Validators.minLength(8),Validators.maxLength(8)]),
      gender:this.fb.control<string>('',[Validators.required]),

    });
  }

  async register(){

    this.userSvc.registerNewUser(this.form.value).subscribe({
      next:()=>{

      },
      error:(err)=>{
        alert(err.error.error)
        console.error(err)
      },
      complete:()=>{
        alert("Registration successful")
        console.log('user registration successful')
        this.router.navigate(['/']);
      }
    }) 
    
  }
}
