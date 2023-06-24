import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { HospitalService } from '../services/hospital.service';
import { ActivatedRoute } from '@angular/router';
import { JwtCookieService } from '../services/jwt-cookie.service';
import { HospitalDecodedToken, MohDecodedToken, Statistic } from '../models';
import { MatDialog } from '@angular/material/dialog';
import { PasswordComponent } from './password.component';

@Component({
  selector: 'app-statistic-list',
  templateUrl: './statistic-list.component.html',
  styleUrls: ['./statistic-list.component.css']
})
export class StatisticListComponent implements OnInit {

  param$!: Subscription | undefined;
  facilityId!: string | null; // from path params
  hospitalCountry!: string | null; // from path params
  userRole!: string; // from jwt token
  countryCode!: string; // from jwt token
  userFacilityId!: string; // from jwt token
  statistics!: Statistic[];
  // accountPassword!: string;


  constructor(public dialog: MatDialog, private hospitalSvc: HospitalService, private activatedRoute: ActivatedRoute, private jwtCookieSvc: JwtCookieService) { }

  ngOnInit(): void {

    this.param$ = this.activatedRoute.parent?.paramMap.subscribe({
      next: (params) => {
        this.facilityId = params.get('facilityId');
        this.hospitalCountry = params.get('hospitalCountry');
        this.userRole = this.jwtCookieSvc.decodeToken(this.jwtCookieSvc.getJwt()).userRole;
        if (this.userRole == 'moh') {
          const decodedToken = this.jwtCookieSvc.decodeToken(this.jwtCookieSvc.getJwt()) as MohDecodedToken;
          this.countryCode = decodedToken.countryCode.toLowerCase();
        }
        if (this.userRole == 'hospital') {
          const decodedToken = this.jwtCookieSvc.decodeToken(this.jwtCookieSvc.getJwt()) as HospitalDecodedToken;
          this.userFacilityId = decodedToken.facilityId;
        }
        this.getStatisticList();
      }
    });

  }

  getStatisticList() {
    this.hospitalSvc.getStatisticListByHospital(this.facilityId).subscribe({
      next: (r: any) => {
        this.statistics = r as Statistic[]
      }
    })
  }

  timestampToDate(timestamp: string) {

    // Convert Solidity timestamp (in seconds) to Date object
    return new Date(parseInt(timestamp) * 1000); // Multiply by 1000 to convert from seconds to milliseconds

  }

  // verifyStatistic(statIndex: number) {

  //   console.log('Use wanna verify stat index: ', statIndex);
  
  //   const dialogRef = this.dialog.open(PasswordComponent, {
  //     width: '250px',
  //     data: { accountPassword: this.accountPassword }
  //   });

  //   dialogRef.afterClosed().subscribe(result => {
  //     console.log('The dialog was closed');
  //     this.accountPassword = result;
  //     console.log("account password ", this.accountPassword)
  //     this.hospitalSvc.verifyStatistic(this.facilityId, statIndex, this.accountPassword).subscribe({
  //       next: () => {
  //         alert('Verification successful');
  //       },
  //       error: (err) => {
  //         console.error(err);
  //         if (err.status == 401) {
  //           alert(err.error.error);
  //         }
  //         this.accountPassword = '';
  //       },
  //       complete: () => {
  //         this.accountPassword = '';
  //         this.getStatisticList();
  //       }
  
  //     });
  //   });

  // }


}
