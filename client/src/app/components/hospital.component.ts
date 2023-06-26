import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { HospitalService } from '../services/hospital.service';
import { Hospital, HospitalDecodedToken, HospitalReview, HospitalSg, MohDecodedToken, ReviewSummary } from '../models';
import { JwtCookieService } from '../services/jwt-cookie.service';
import { MatDialog } from '@angular/material/dialog';
import { PasswordComponent } from './password.component';
import { FrequencyPenaltyComponent } from './frequency-penalty.component';


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
  userFacilityId!:string;

  hospital!:Hospital;
  hospitalSg!:HospitalSg;

  statIndex!:number;
  verifyHospitalCredentialButton!:boolean;
  accountPassword!:string;
  toVerify!:string;
  updateFrequency!: string;
  penalty!: string;

  currentUpdateFrequency!: string;
  currentPenalty!: string;


  constructor(public dialog:MatDialog, private activatedRoute:ActivatedRoute, private hospitalSvc:HospitalService, 
    private router:Router, private jwtCookieSvc:JwtCookieService){}
 
  ngOnInit(): void {
    this.param$ = this.activatedRoute.params.subscribe({
      next: (params) => {
          this.facilityId = params['facilityId'];
          this.hospitalCountry = params['hospitalCountry'];
          this.userRole = this.jwtCookieSvc.decodeToken(this.jwtCookieSvc.getJwt()).userRole;
          console.log('>> userRole: ', this.userRole)
          if(this.userRole == 'moh'){
            const decodedToken = this.jwtCookieSvc.decodeToken(this.jwtCookieSvc.getJwt()) as MohDecodedToken;
            this.countryCode =decodedToken.countryCode.toLowerCase();
          }
          //  if hospital can access hospital component !
          if(this.userRole == 'hospital'){
            const decodedToken = this.jwtCookieSvc.decodeToken(this.jwtCookieSvc.getJwt()) as HospitalDecodedToken;
            this.userFacilityId = decodedToken.facilityId;
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
          console.log('statIndex ', this.statIndex)
          
        },
        error: (err) => console.error(err),
        complete: () => {
          if(this.userRole == 'moh' && this.countryCode == 'sg'){
            this.getCurrentUpdateFrequencyAndPenalty();
            this.showStatisticList();
          }
          if(this.userRole == 'hospital' && this.userFacilityId == this.facilityId){
            this.getCurrentUpdateFrequencyAndPenalty();
          }
          console.log('completed getHospitalSg()');
        }
      });
    }
    
  }

  getCurrentUpdateFrequencyAndPenalty(){
    this.hospitalSvc.getCurrentUpdateFrequencyAndPenalty(this.facilityId).subscribe({
      next:(r : any)=>{
        this.currentUpdateFrequency = r['currentUpdateFrequency'];
        this.currentPenalty = r['currentPenalty'];
      },
      error: (err) => console.error(err),
      complete: () => {
        console.log('>>>>',this.currentUpdateFrequency)
        console.log('>>>>',this.currentPenalty)
        console.log('complete getCurrentUpdateFrequencyAndPenalty()')
      }
    });
  }

  verifyCredentials(){

    this.toVerify = 'license'
    this.openDialog();

  }

  verifyJci(){

    this.toVerify = 'jci'
    this.openDialog();

  }

  setFrequencyAndPenalty(){
    const dialogRef = this.dialog.open(FrequencyPenaltyComponent, {
      width: '250px',
      data: {
        updateFrequency: this.updateFrequency,
        penalty: this.penalty,
        accountPassword: this.accountPassword
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      console.log('result: ', result);
      this.updateFrequency = result.updateFrequency;
      this.penalty = result.penalty;
      this.accountPassword = result.accountPassword;

      this.hospitalSvc.setFrequencyAndPenalty(this.facilityId, this.accountPassword, this.updateFrequency, this.penalty).subscribe({
        next:()=>{
          alert('Verification successful'); // TODO: display loading message while waiting
        },
        error:(err)=>{
          console.error(err);
          if(err.status == 401){
            alert(err.error.error);
          }
          this.updateFrequency = '';
          this.penalty='';
          this.accountPassword = '';
        },
        complete:()=>{
          this.updateFrequency = '';
          this.penalty='';
          this.accountPassword = '';
          this.getHospital()
        }
      });
    }
    );
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
      this.hospitalSvc.verifyCredentials(this.facilityId, this.accountPassword, this.toVerify).subscribe({
        next:()=>{
          alert('Verification successful'); // TODO: display loading message while waiting
        },
        error:(err)=>{
          console.error(err);
          if(err.status == 401){
            alert(err.error.error);
          }
          this.accountPassword = '';
        },
        complete:()=>{
          this.accountPassword = '';
          this.getHospital()
        }
      });
    });
  }

  showStatisticList() {
    this.router.navigate(['/hospital',this.hospitalCountry,this.facilityId,'statistic-list'])
  }
  

  goToStatistic(){
    this.router.navigate(['/statistic', this.facilityId, this.statIndex])
  }

  showReview(){

    this.router.navigate(['/hospital',this.hospitalCountry,this.facilityId,'review-list'])
  
  }

  goToReview(){

    this.router.navigate(['/reviewHospital',this.hospitalCountry,this.facilityId]);

  }

}
