import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom, tap } from 'rxjs';
import { JwtCookieService } from './jwt-cookie.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HospitalService {

  constructor(private http:HttpClient, private jwtCookieSvc: JwtCookieService) { }

  HOSPITAL_URL = environment.apiHospitalUrl;
  token!: string | null;

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

  getHospitals(state:string, city:string, name:string, offset:number, sortByRating:boolean, descending:boolean){
    
    const headers = new HttpHeaders().set('Accept','application/json');
    let url;

    if(state && city && name){

      console.log('in state && city && name');

      url = `${this.HOSPITAL_URL}/search/${state}/${city}`;
      const params = new HttpParams().set('name', name).set('offset',offset).set('sortByRating',sortByRating).set('descending',descending);
      return this.http.get(url, { headers , params });
    }

    if(state && city && !name){

      console.log('in state && city && !name');

      url = `${this.HOSPITAL_URL}/search/${state}/${city}`;
      const params = new HttpParams().set('offset',offset).set('sortByRating',sortByRating).set('descending',descending);
      return this.http.get(url, { headers, params });
    }

    if(state && !city && name){
      console.log('in state && !city && name');

      url = `${this.HOSPITAL_URL}/search/${state}`;
      const params = new HttpParams().set('name', name).set('offset',offset).set('sortByRating',sortByRating).set('descending',descending);
      return this.http.get(url, { headers , params });
    }

    if(state && !city && !name){
      console.log('in state && !city && !name');

      url = `${this.HOSPITAL_URL}/search/${state}`;
      const params = new HttpParams().set('offset',offset).set('sortByRating',sortByRating).set('descending',descending);
      return this.http.get(url, { headers, params });
    }

    if(!state && !city && name){
      console.log('in !state && !city && name');

      url = `${this.HOSPITAL_URL}/search`;
      const params = new HttpParams().set('name', name).set('offset',offset).set('sortByRating',sortByRating).set('descending',descending);
      return this.http.get(url, { headers, params });
    }

    if(!state && !city && !name){
      console.log('in !state && !city && !name');

      url = `${this.HOSPITAL_URL}/search`;
      const params = new HttpParams().set('offset',offset).set('sortByRating',sortByRating).set('descending',descending);
      return this.http.get(url, { headers, params });
    }

    return;
  }

  getHospital(facilityId:string){
    // GET /api/hospitals/hospital/{facilityId}
    const headers = new HttpHeaders().set('Accept','application/json');

    return this.http.get(`${this.HOSPITAL_URL}/hospital/${facilityId}`, { headers });
  }

  getHospitalReview(facilityId:string){
    // GET /api/hospitals/hospital/{facilityId}/review
    const headers = new HttpHeaders().set('Accept','application/json');

    return this.http.get(`${this.HOSPITAL_URL}/hospital/${facilityId}/review`, { headers });
  }

  postHospitalReview(facilityId:string, review:any){

    // POST /api/hospitals/hospital/{facilityId}/review
    const headers = new HttpHeaders().set('Accept','application/json')
                                     .set('Content-Type', 'application/json');

    const payload = review 

    return this.http.post(`${this.HOSPITAL_URL}/hospital/${facilityId}/review`, payload , { headers });
  }

  registerHospital(form:any){

    const headers = new HttpHeaders().set('Content-Type','application/json')
                                     .set('Accept','application/json');

    return this.http.post(`${this.HOSPITAL_URL}/${form.countryCode}/register/hospital`, form, { headers });
  }

  /*
    Log In
    /POST /api/hospitals/authenticate
    Content-Type: application/json
    Accept: application/json
  */
  authenticate(form:any){
    const headers = new HttpHeaders().set('Content-Type','application/json')
                                      .set('Accept','application/json');
    
    return lastValueFrom(
      this.http.post(this.HOSPITAL_URL + '/authenticate', form, { headers , observe: 'response' })
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

  updateStatistic(form:any, password:string){

    // POST /api/hospitals/hospital/{facilityId}/review
    const headers = new HttpHeaders().set('Accept','application/json')
    .set('Content-Type', 'application/json');

    const payload = {
      statistic: form,
      accountPassword: password
    }

    console.log(payload); // debug

    return this.http.post(`${this.HOSPITAL_URL}/sg/statistic/update`, payload , { headers });
  }


}
