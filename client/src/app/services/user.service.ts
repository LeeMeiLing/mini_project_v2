import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom, tap } from 'rxjs';
import { JwtCookieService } from './jwt-cookie.service';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  constructor(private http:HttpClient, private jwtCookieSvc: JwtCookieService) { }

  // USER_URL = environment.apiUserUrl;
  USER_URL = 'https://health-trust-project-production.up.railway.app/api/user';
  token!: string | null;

  /*
    New user registration
    /POST /api/user/register
    Content-Type: application/json
    Accept: application/json
  */
  registerNewUser(form:any){

    const headers = new HttpHeaders().set('Content-Type','application/json')
                                     .set('Accept','application/json');
    
    return this.http.post(this.USER_URL + '/register/public', form, { headers });                           

  }

  /*
    Log In
    /POST /api/user/authenticate
    Content-Type: application/json
    Accept: application/json
  */
  authenticate(form:any){
    const headers = new HttpHeaders().set('Content-Type','application/json')
                                     .set('Accept','application/json');
    
    return lastValueFrom(
      this.http.post(this.USER_URL + '/authenticate', form, { headers , observe: 'response' })
               .pipe(
                  tap( 
                    response => { this.token = response.headers.get('Authorization');
                                  if(this.token){
                                    this.jwtCookieSvc.setJwt(this.token)
                                    console.log(this.jwtCookieSvc.getJwt()); // debug
                                  }
                                  
                  })
               )
    );                                                   

  }

}

