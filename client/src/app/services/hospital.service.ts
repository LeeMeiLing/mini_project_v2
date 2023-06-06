import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class HospitalService {

  constructor(private http:HttpClient) { }

  HOSPITAL_URL = 'http://localhost:8080/api/hospitals';

  getHospitalList(name:string){
    
    // GET /api/hospitals?name=name&limit=20
    const headers = new HttpHeaders().set('Accept','application/json');
    const params = new HttpParams().set('name',name).set('limit', 20);

    return this.http.get(this.HOSPITAL_URL,{ headers, params });
  }
}
