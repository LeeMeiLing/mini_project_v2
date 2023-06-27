import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { HospitalService } from '../services/hospital.service';
import { HospitalDecodedToken, MohDecodedToken, Statistic } from '../models';
import { JwtCookieService } from '../services/jwt-cookie.service';
import { MatDialog } from '@angular/material/dialog';
import { PasswordComponent } from './password.component';

@Component({
  selector: 'app-show-statistic',
  templateUrl: './show-statistic.component.html',
  styleUrls: ['./show-statistic.component.css']
})
export class ShowStatisticComponent implements OnInit, OnDestroy{
  
  param$!:Subscription;
  statIndex!: number;
  statistic!:Statistic;
  dateUpdated!:Date;
  facilityId!:string;
  waiting=false;

  userRole!:string;
  countryCode!:string;
  userFacilityId!:string;

  accountPassword!: string;


  constructor(public dialog: MatDialog, private activateRoute:ActivatedRoute, private hospSvc:HospitalService, 
    private jwtCookieSvc:JwtCookieService, private router:Router){}

  ngOnDestroy(): void {
    this.param$.unsubscribe();
  }

  ngOnInit(): void {
    this.param$ = this.activateRoute.params.subscribe({
      next: async (params) => {
        this.statIndex = params['statIndex'];
        this.facilityId = params['facilityId'];
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
        await this.getStatistic();
      }
    })
  }


  async getStatistic(){

    this.hospSvc.getStatistic(this.statIndex, this.facilityId).subscribe({
      next:(r:any)=>{
        this.statistic = r as Statistic;
        this.timestampToDate();
      },
      error: (err)=>{
        console.error('error getStatistic: ', err);
      },
      complete: ()=>{
        console.log('complete getStatistic: ', this.statistic);
      }
    });

  }

  timestampToDate(){

    // Convert Solidity timestamp to Date object
    this.dateUpdated = new Date(parseInt(this.statistic['timestamp']) * 1000); // Multiply by 1000 to convert from seconds to milliseconds

  }

  verifyStatistic(statIndex: number) {

    console.log('Use wanna verify stat index: ', statIndex);
  
    const dialogRef = this.dialog.open(PasswordComponent, {
      width: '250px',
      data: { accountPassword: this.accountPassword }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.accountPassword = result;
      console.log("account password ", this.accountPassword);
      this.waiting=true;
      this.hospSvc.verifyStatistic(this.facilityId, statIndex, this.accountPassword).subscribe({
        next: () => {
          alert('Verification successful');
        },
        error: (err) => {
          this.waiting=false;
          console.error(err);
          if (err.status == 401) {
            alert(err.error.error);
          }
          this.accountPassword = '';
        },
        complete: () => {
          this.waiting=false;
          this.accountPassword = '';
          this.router.navigate(['/hospital/sg', this.facilityId])
        }
  
      });
    });

  }

}
