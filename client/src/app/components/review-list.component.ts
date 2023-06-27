import { Component, OnInit } from '@angular/core';
import { HospitalService } from '../services/hospital.service';
import { Subscription } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { JwtCookieService } from '../services/jwt-cookie.service';
import { HospitalReview, MohDecodedToken, ReviewSummary } from '../models';

@Component({
  selector: 'app-review-list',
  templateUrl: './review-list.component.html',
  styleUrls: ['./review-list.component.css']
})
export class ReviewListComponent implements OnInit{

  param$!:Subscription | undefined;
  facilityId!:string | null;
  hospitalCountry!:string | null;
  userRole!: string;
  countryCode!: string;
  totalReview!:number;
  reviews:HospitalReview[] = [];
  reviewSummary!: ReviewSummary;

  constructor(private hospitalSvc:HospitalService, private activatedRoute:ActivatedRoute, private jwtCookieSvc:JwtCookieService){}

  ngOnInit(): void {

    this.param$ = this.activatedRoute.parent?.paramMap.subscribe({
      next: (params)=>{
        this.facilityId = params.get('facilityId');
          this.hospitalCountry = params.get('hospitalCountry');
          this.userRole = this.jwtCookieSvc.decodeToken(this.jwtCookieSvc.getJwt()).userRole;
          if(this.userRole == 'moh'){
            const decodedToken = this.jwtCookieSvc.decodeToken(this.jwtCookieSvc.getJwt()) as MohDecodedToken;
            this.countryCode =decodedToken.countryCode.toLowerCase();
          }
          console.log(this.hospitalCountry, this.facilityId, this.userRole)
          this.getHospitalReview();
      }
    });
    
  }

  getHospitalReview(){
    this.hospitalSvc.getHospitalReview(this.hospitalCountry,this.facilityId)?.subscribe({
      next: (r:any) => {
        this.reviews = r['reviews'];
        this.totalReview = r['totalReview'];
        this.reviewSummary = r['reviewSummary'];
      },
      error: err => console.error(err),
      complete: () => console.log('completed getHospitalReview()')
    });
  }

  range(count: number): number[] {

    let items:number[] = [];

    for (let i = 0; i < count; i++) {
      items.push(i);
    }
    return items;
  }
  

}
