import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  constructor(private http:HttpClient) { }

  USER_URL = 'http://localhost:8080/api/user';

  /*
    New user registration
    /POST /api/user
    Content-Type: application/json
    Accept: application/json
  */
  registerNewUser(form:any):Promise<any>{

    const headers = new HttpHeaders().set('Content-Type','application/json')
                                     .set('Accept','application/json');
    
    return lastValueFrom(this.http.post(this.USER_URL, form, { headers }));                           

  }


}

