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

  getStates(){
    // GET /api/hospitals/states
    const headers = new HttpHeaders().set('Accept','application/json');
    return this.http.get(this.HOSPITAL_URL + '/states',{ headers } );
  }

  getCities(state:string){
    // GET /api/hospitals/{state}/cities
    const headers = new HttpHeaders().set('Accept','application/json');
    return this.http.get(`${this.HOSPITAL_URL}/${state}/cities`,{ headers } );
  }

  getHospitals(state:string, city:string, name:string, offset:number){
    
    const headers = new HttpHeaders().set('Accept','application/json');
    let url;

    if(state && city && name){
      console.log('in state && city && name');
      url = `${this.HOSPITAL_URL}/search/${state}/${city}`;
      const params = new HttpParams().set('name', name).set('offset',offset);
      return this.http.get(url, { headers , params });
    }

    if(state && city && !name){
      console.log('in state && city && !name');
      const params = new HttpParams().set('offset',offset);
      url = `${this.HOSPITAL_URL}/search/${state}/${city}`;
      return this.http.get(url, { headers, params });
    }

    // if(state && name){
    //   url = `${this.HOSPITAL_URL}/${state}`;
    //   const params = new HttpParams().set('name', name).set('offset',offset);
    //   return this.http.get(url, { headers , params });
    // }

    // if(state){
    //   url = `${this.HOSPITAL_URL}/${state}`;
    //   const params = new HttpParams().set('offset',offset);
    //   return this.http.get(url, { headers });
    // }

    return;
    
  }
}
