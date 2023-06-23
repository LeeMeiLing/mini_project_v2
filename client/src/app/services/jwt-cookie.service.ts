import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import jwt_decode from "jwt-decode";
import { HospitalDecodedToken, MohDecodedToken } from '../models';

@Injectable({
  providedIn: 'root'
})
export class JwtCookieService {

  private readonly jwtCookieName = 'authToken';

  constructor(private cookieService:CookieService) { }

  public getJwt(): string {
    return this.cookieService.get(this.jwtCookieName);
  }

  public setJwt(jwt: string): void {
    this.cookieService.set(this.jwtCookieName, jwt);
  }

  public deleteJwt(): void {
    this.cookieService.delete(this.jwtCookieName);
  }

  public decodeHospitalToken(token:string):HospitalDecodedToken{

    return jwt_decode(token)
  
  }

  public decodeMohToken(token:string):MohDecodedToken{

    return jwt_decode(token)
  
  }
}
