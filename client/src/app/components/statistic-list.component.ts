import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { HospitalService } from '../services/hospital.service';
import { ActivatedRoute } from '@angular/router';
import { JwtCookieService } from '../services/jwt-cookie.service';
import { HospitalDecodedToken, MohDecodedToken, Statistic } from '../models';

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

  constructor(private hospitalSvc: HospitalService, private activatedRoute: ActivatedRoute, private jwtCookieSvc: JwtCookieService) { }

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
        this.statistics.reverse();
      }
    })
  }

  timestampToDate(timestamp: string) {

    // Convert Solidity timestamp (in seconds) to Date object
    return new Date(parseInt(timestamp) * 1000); // Multiply by 1000 to convert from seconds to milliseconds

  }

}
