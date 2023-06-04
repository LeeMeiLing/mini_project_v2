import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  constructor(private http:HttpClient) { }

  USER_URL = 'http://localhost:8080/api/user';
  token!: string | null;

  /*
    New user registration
    /POST /api/user/register
    Content-Type: application/json
    Accept: application/json
  */
  async registerNewUser(form:any):Promise<any>{

    const headers = new HttpHeaders().set('Content-Type','application/json')
                                     .set('Accept','application/json');
    
    return lastValueFrom(this.http.post(this.USER_URL + '/register', form, { headers }));                           

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
              //  .pipe(
              //     map( response => { this.token = response.headers.get('Authorization') })
              //  )
    );                                                   

  }


}

