import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { HospitalService } from '../services/hospital.service';
import { Hospital, HospitalReview, HospitalSg, ReviewSummary } from '../models';
import { JwtCookieService } from '../services/jwt-cookie.service';
import { MatDialog } from '@angular/material/dialog';
import { PasswordComponent } from './password.component';

@Component({
  selector: 'app-hospital',
  templateUrl: './hospital.component.html',
  styleUrls: ['./hospital.component.css']
})
export class HospitalComponent implements OnInit, OnDestroy{

  param$!:Subscription;
  facilityId!:string;
  hospitalCountry!:string;
  totalReview!:number;
  reviews:HospitalReview[] = [];
  reviewSummary!: ReviewSummary;
  userRole!:string;
  countryCode!:string;

  hospital!:Hospital;
  hospitalSg!:HospitalSg;

  statIndex!:number;
  verifyHospitalCredentialButton!:boolean;
  accountPassword!:string;

  constructor(public dialog:MatDialog, private activatedRoute:ActivatedRoute, private hospitalSvc:HospitalService, 
    private router:Router, private jwtCookieSvc:JwtCookieService){}
 
  ngOnInit(): void {
    this.param$ = this.activatedRoute.params.subscribe({
      next: (params) => {
          this.facilityId = params['facilityId'];
          this.hospitalCountry = params['hospitalCountry'];
          this.userRole = this.jwtCookieSvc.decodeMohToken(this.jwtCookieSvc.getJwt()).userRole;
          if(this.userRole == 'moh'){
            this.countryCode = this.jwtCookieSvc.decodeMohToken(this.jwtCookieSvc.getJwt()).countryCode.toLowerCase();
          }
          this.getHospital();
      },
      
    });

  }

  ngOnDestroy(): void {
    this.param$.unsubscribe();
  }

  getHospital(){

    if(this.hospitalCountry == 'us'){
      this.hospitalSvc.getHospital(this.facilityId).subscribe({
        next: (r:any) => {
          this.hospital = r['hospital'];
          this.totalReview = r['totalReview'];
          console.log(this.hospital)
          
        },
        error: err => console.error(err),
        complete: () => {
          console.log('completed getHospitalUs()')
        }
      });
    }

    if(this.hospitalCountry == 'sg'){
      this.hospitalSvc.getHospitalSgByFacilityId(this.facilityId).subscribe({
        next: (r:any) => {
          this.hospitalSg = r['hospital'];
          this.totalReview = r['totalReview'];
          this.statIndex = r['latestStatIndex']
          console.log(this.hospitalSg)
          
        },
        error: (err) => console.error(err),
        complete: () => {
          if(this.userRole == 'moh' && this.countryCode == 'sg'){
            this.showStatisticList();
          }
          console.log('completed getHospitalSg()');
        }
      });
    }
    
  }

  showVerifyHospitalCredentialsButton(){
    if(this.userRole=='moh' && this.countryCode=='sg' && !this.hospitalSg.registered){
      return true;
    }
    return false;
  }

  async verifyCredentials(){
    // pass accountpassowrd as payload
    await this.openDialog();
    // console.log("account password " , this.accountPassword)
    // this.hospitalSvc.verifyCredentials(this.facilityId, this.accountPassword).subscribe();
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(PasswordComponent, {
      width: '250px',
      data: {accountPassword: this.accountPassword}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.accountPassword = result;
      console.log("account password " , this.accountPassword)
      this.hospitalSvc.verifyCredentials(this.facilityId, this.accountPassword).subscribe();
    });
  }

  showStatisticList() {
    this.router.navigate(['/hospital',this.hospitalCountry,this.facilityId,'statistic-list'])
  }
  

  goToStatistic(){
    this.router.navigate(['/statistic', this.statIndex])
  }

  showReview(){

    // this.hospitalSvc.getHospitalReview(this.hospitalCountry,this.facilityId)?.subscribe({
    //   next: (r:any) => {
    //     this.reviews = r['reviews'];
    //     this.totalReview = r['totalReview'];
    //     this.reviewSummary = r['reviewSummary'];
    //   },
    //   error: err => console.error(err),
    //   complete: () => console.log('completed getHospitalReview()')
    // });

    this.router.navigate(['/hospital',this.hospitalCountry,this.facilityId,'review-list'])
  
  }

  goToReview(){

    // if(this.hospitalCountry == 'us'){
    //   this.router.navigate(['/reviewHospital',this.hospitalCountry,this.hospital.facilityId])
    // } 
    
    // if(this.hospitalCountry == 'sg'){
    //   this.router.navigate(['/reviewHospital',this.hospitalCountry,this.hospitalSg.facilityId])
    // }

    this.router.navigate(['/reviewHospital',this.hospitalCountry,this.facilityId]);

  }

}
