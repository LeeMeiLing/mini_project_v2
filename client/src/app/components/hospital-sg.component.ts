import { Component, OnInit } from '@angular/core';
import { JwtCookieService } from '../services/jwt-cookie.service';

@Component({
  selector: 'app-hospital-sg',
  templateUrl: './hospital-sg.component.html',
  styleUrls: ['./hospital-sg.component.css']
})
export class HospitalSgComponent implements OnInit{

  userRole!:string;

  constructor(private jwtCookieSvc:JwtCookieService){}

  ngOnInit(): void {
    this.userRole = this.jwtCookieSvc.decodeToken(this.jwtCookieSvc.getJwt()).userRole;
  }

}
