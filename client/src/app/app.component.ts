import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtCookieService } from './services/jwt-cookie.service';
import { HospitalDecodedToken, MohDecodedToken } from './models';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent{
  title = 'client';

  userRole!:string;
  countryCode!:string;
  userFacilityId!:string;

  constructor(private router:Router, private jwtCookieSvc:JwtCookieService){}

  goToHomePage(){

    this.userRole = this.jwtCookieSvc.decodeToken(this.jwtCookieSvc.getJwt()).userRole;

    if(this.userRole == 'moh'){
      const decodedToken = this.jwtCookieSvc.decodeToken(this.jwtCookieSvc.getJwt()) as MohDecodedToken;
      this.countryCode =decodedToken.countryCode.toLowerCase();
    }
    //  if hospital can access hospital component !
    if(this.userRole == 'hospital'){
      const decodedToken = this.jwtCookieSvc.decodeToken(this.jwtCookieSvc.getJwt()) as HospitalDecodedToken;
      this.userFacilityId = decodedToken.facilityId;
    }

    if(this.userRole == 'user'){
      this.router.navigate(['/searchHospital'])
    }

    if(this.userRole == 'hospital'){
      this.router.navigate(['/home/hospital'])
    }

    if(this.userRole == 'moh'){
      if(this.countryCode == 'sg'){
        this.router.navigate(['/home/moh/sg'])
      }
    }

  }

  logout(){
    this.userRole = this.jwtCookieSvc.decodeToken(this.jwtCookieSvc.getJwt()).userRole;

    if(this.userRole){
      this.jwtCookieSvc.deleteJwt();
      this.router.navigate(['/']);
    }
  }
}
