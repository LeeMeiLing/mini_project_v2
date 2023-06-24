import { Component, OnInit } from '@angular/core';
import { JwtCookieService } from '../services/jwt-cookie.service';
import { HospitalDecodedToken } from '../models';

@Component({
  selector: 'app-hospital-home',
  templateUrl: './hospital-home.component.html',
  styleUrls: ['./hospital-home.component.css']
})
export class HospitalHomeComponent implements OnInit{

  facilityId!:string
  userRole!: string;

  constructor(private jwtCookieSvc:JwtCookieService){}

  ngOnInit(): void {
    this.userRole = this.jwtCookieSvc.decodeToken(this.jwtCookieSvc.getJwt()).userRole;
    if(this.userRole == 'hospital'){
      const decodedToken = this.jwtCookieSvc.decodeToken(this.jwtCookieSvc.getJwt()) as HospitalDecodedToken;
      this.facilityId = decodedToken.facilityId;
    }

  }

}
