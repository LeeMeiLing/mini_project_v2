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
  form!:FormGroup

  constructor(private fb:FormBuilder, private actiavtedRoute:ActivatedRoute, private hospitalSvc:HospitalService, private router:Router){}
 
  ngOnInit(): void {
    this.param$ = this.actiavtedRoute.params.subscribe(
      (params) => { this.facilityId = params['facilityId']; }
    )
    this.form = this.createForm();
  }

  ngOnDestroy(): void {
    this.param$.unsubscribe();
  }

  createForm():FormGroup{
    return this.fb.group({

      patientId:this.fb.control<string>('',[Validators.required]),
      nurseCommunication:this.fb.control<number>(0,[Validators.required]),
      doctorCommunication:this.fb.control<number>(0,[Validators.required]),
      staffResponsiveness:this.fb.control<number>(0,[Validators.required]),
      communicationAboutMedicines:this.fb.control<number>(0,[Validators.required]),
      dischargeInformation:this.fb.control<number>(0,[Validators.required]),
      careTransition:this.fb.control<number>(0,[Validators.required]),
      cleanliness:this.fb.control<number>(0,[Validators.required]),
      quientness:this.fb.control<number>(0,[Validators.required]),
      overallRating:this.fb.control<number>(0,[Validators.required]),
      willingnessToRecommend:this.fb.control<number>(0,[Validators.required]),
      comments:this.fb.control<string>(''),

    })
  }

  postReview(){
    console.log(this.form.value)
    this.hospitalSvc.postHospitalReview(this.facilityId, this.form.value).subscribe({
      next:() => {},
      error: (err) => console.error(err),
      complete:()=>{
        this.router.navigate(['/hospital',this.facilityId]);
      }
    });
    
  }

}
