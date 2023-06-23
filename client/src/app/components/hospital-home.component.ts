import { Component, OnInit } from '@angular/core';
import { JwtCookieService } from '../services/jwt-cookie.service';

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
    this.userRole = this.jwtCookieSvc.decodeMohToken(this.jwtCookieSvc.getJwt()).userRole;
    if(this.userRole == 'hospital'){
      this.facilityId = this.jwtCookieSvc.decodeHospitalToken(this.jwtCookieSvc.getJwt()).facilityId;
    }

  }

}
