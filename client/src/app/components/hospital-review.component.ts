import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { HospitalService } from '../services/hospital.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-hospital-review',
  templateUrl: './hospital-review.component.html',
  styleUrls: ['./hospital-review.component.css']
})
export class HospitalReviewComponent implements OnInit, OnDestroy{

  param$!:Subscription;
  facilityId!:string;
  hospitalCountry!:string;
  form!:FormGroup

  constructor(private fb:FormBuilder, private actiavtedRoute:ActivatedRoute, private hospitalSvc:HospitalService, private router:Router){}
 
  ngOnInit(): void {
    this.param$ = this.actiavtedRoute.params.subscribe(
      (params) => { 
        this.facilityId = params['facilityId'];
        this.hospitalCountry = params['hospitalCountry'];

     }
    )
    this.form = this.createForm();
  }

  ngOnDestroy(): void {
    this.param$.unsubscribe();
  }

  createForm():FormGroup{
    return this.fb.group({

      patientId:this.fb.control<string>('',[Validators.required]),
      nurseCommunication:this.fb.control<number|undefined>(undefined,[Validators.required]),
      doctorCommunication:this.fb.control<number|undefined>(undefined,[Validators.required]),
      staffResponsiveness:this.fb.control<number|undefined>(undefined,[Validators.required]),
      communicationAboutMedicines:this.fb.control<number|undefined>(undefined,[Validators.required]),
      dischargeInformation:this.fb.control<number|undefined>(undefined,[Validators.required]),
      careTransition:this.fb.control<number|undefined>(undefined,[Validators.required]),
      cleanliness:this.fb.control<number|undefined>(undefined,[Validators.required]),
      quietness:this.fb.control<number|undefined>(undefined,[Validators.required]),
      overallRating:this.fb.control<number|undefined>(undefined,[Validators.required]),
      willingnessToRecommend:this.fb.control<number|undefined>(undefined,[Validators.required]),
      comments:this.fb.control<string>(''),

    })
  }

  postReview(){
    console.log(this.form.value)
    this.hospitalSvc.postHospitalReview(this.hospitalCountry, this.facilityId, this.form.value)?.subscribe({
      next:() => {},
      error: (err) => console.error(err),
      complete:()=>{
        alert('You have posted a review');
        this.router.navigate(['/hospital',this.hospitalCountry,this.facilityId]);
      }
    });
    
  }

}
