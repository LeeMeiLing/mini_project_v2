import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { HospitalService } from '../services/hospital.service';
import { Hospital, HospitalReview, ReviewSummary } from '../models';
import { JwtCookieService } from '../services/jwt-cookie.service';

@Component({
  selector: 'app-hospital',
  templateUrl: './hospital.component.html',
  styleUrls: ['./hospital.component.css']
})
export class HospitalComponent implements OnInit, OnDestroy{

  param$!:Subscription;
  facilityId!:string;
  hospital!:Hospital;
  totalReview!:number;
  reviews:HospitalReview[] = [];
  reviewSummary!: ReviewSummary;

  constructor(private actiavtedRoute:ActivatedRoute, private hospitalSvc:HospitalService, private router:Router, private jwtCookieSvc:JwtCookieService){}
 
  ngOnInit(): void {
    this.param$ = this.actiavtedRoute.params.subscribe(
      (params) => {
        this.facilityId = params['facilityId'];
        this.hospitalSvc.getHospital(this.facilityId).subscribe({
          next: (r:any) => {
            this.hospital = r['hospital'];
            this.totalReview = r['totalReview'];
            console.log(this.hospital)
          },
          error: err => console.error(err),
          complete: () => console.log('completed getHospital()')
        });
      }
    );

    this.jwtCookieSvc.decodeToken(this.jwtCookieSvc.getJwt());
    
  }

  ngOnDestroy(): void {
    this.param$.unsubscribe();
  }

  showReview(){
    this.hospitalSvc.getHospitalReview(this.facilityId).subscribe({
      next: (r:any) => {
        this.reviews = r['reviews'];
        this.totalReview = r['totalReview'];
        this.reviewSummary = r['reviewSummary'];
      },
      error: err => console.error(err),
      complete: () => console.log('completed getHospitalReview()')
    });
  }

  goToReview(){
    this.router.navigate(['/reviewHospital',this.hospital.facilityId])
  }

}
