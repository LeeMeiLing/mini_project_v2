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
  HOSPITAL_SG_URL = environment.apiHospitalSgUrl;
  // HOSPITAL_URL = 'https://dispensable-ladybug-production.up.railway.app/api/hospitals'
  // HOSPITAL_SG_URL = 'https://dispensable-ladybug-production.up.railway.app/api/hospitals/sg';
  token!: string | null;

  // getHospitalList(name:string){
    
  //   // GET /api/hospitals?name=name&limit=20
  //   const headers = new HttpHeaders().set('Accept','application/json');
  //   const params = new HttpParams().set('name',name).set('limit', 20);

  //   return this.http.get(this.HOSPITAL_URL,{ headers, params });
  // }

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

  getHospitalReview(countryCode:string|null, facilityId:string|null){
    // GET /api/hospitals/hospital/{facilityId}/review
    const headers = new HttpHeaders().set('Accept','application/json');


    if(countryCode == 'us'){
      return this.http.get(`${this.HOSPITAL_URL}/hospital/${facilityId}/review`, { headers });
    }

    if(countryCode == 'sg'){
      return this.http.get(`${this.HOSPITAL_SG_URL}/hospital/${facilityId}/review`, { headers });
    }

    return;
  }

  postHospitalReview(countryCode:string, facilityId:string, review:any){

    // POST /api/hospitals/hospital/{facilityId}/review
    const headers = new HttpHeaders().set('Accept','application/json')
                                     .set('Content-Type', 'application/json');

    const payload = review 

    if(countryCode == 'us'){
      return this.http.post(`${this.HOSPITAL_URL}/hospital/${facilityId}/review`, payload , { headers });
    }

    if(countryCode == 'sg'){
      return this.http.post(`${this.HOSPITAL_SG_URL}/hospital/${facilityId}/review`, payload , { headers });
    }

    return;

  }

  ////////////////////////////////////

  registerHospital(form:any){

    const headers = new HttpHeaders().set('Content-Type','application/json')
                                     .set('Accept','application/json');

    return this.http.post(`${this.HOSPITAL_URL}/${form.countryCode}/register/hospital`, form, { headers });
  }

  registerMoh(form:any){

    const headers = new HttpHeaders().set('Content-Type','application/json')
                                     .set('Accept','application/json');

    return this.http.post(`${this.HOSPITAL_SG_URL}/register/moh`, form, { headers });
  }

  /*
    Log In
    /POST /api/hospitals/authenticate
    Content-Type: application/json
    Accept: application/json
  */
  authenticateHospital(form:any){
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

  /*
    Log In
    /POST /api/hospitals/authenticate/moh
    Content-Type: application/json
    Accept: application/json
  */
    authenticateMoh(form:any){
      const headers = new HttpHeaders().set('Content-Type','application/json')
                                        .set('Accept','application/json');
      
      return lastValueFrom(
        this.http.post(this.HOSPITAL_URL + '/authenticate/moh', form, { headers , observe: 'response' })
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

    getMohList(){
      const headers = new HttpHeaders().set('Accept','application/json');
  
      return this.http.get(`${this.HOSPITAL_URL}/moh`, { headers });
    }

  //////////////////////////////////////////////

  // //GET /api/hospitals/sg/hospital/{ethAddress}
  // getHospitalSgByEthAddress(ethAddress:string){
  //   const headers = new HttpHeaders().set('Accept','application/json');

  //   return this.http.get(`${this.HOSPITAL_SG_URL}/hospital/${ethAddress}`, { headers });
  // }

  // GET api/hospitals/sg/hospital/{facilityId}
  getHospitalSgByFacilityId(facilityId:string){
    const headers = new HttpHeaders().set('Accept','application/json');

    return this.http.get(`${this.HOSPITAL_SG_URL}/hospital/${facilityId}`, { headers });
  }


  updateStatistic(form:any, password:string){

    // POST /api/hospitals/sg/statistic/update
    const headers = new HttpHeaders().set('Accept','application/json')
    .set('Content-Type', 'application/json');

    const payload = {
      statistic: form,
      accountPassword: password
    }

    console.log(payload); // debug

    return this.http.post(`${this.HOSPITAL_SG_URL}/statistic/update`, payload , { headers });
  }

  getStatistic(statIndex:number, facilityId?:string){

    // GET /api/hospitals/sg/statistic/{statIndex}?facilityId=
    const headers = new HttpHeaders().set('Accept','application/json');

    let params;

    if(facilityId){
      params = new HttpParams().set('facilityId', facilityId);
    }

    return this.http.get(`${this.HOSPITAL_SG_URL}/statistic/${statIndex}`, { headers, params });

  }

  // GET /api/hospitals/sg/statistic-list/{facilityId}
  getStatisticListByHospital(facilityId: string | null) {
    const headers = new HttpHeaders().set('Accept','application/json');

    return this.http.get(`${this.HOSPITAL_SG_URL}/statistic-list/${facilityId}`, { headers });

  }

  // POST /api/hospitals/sg/hospital/{facilityId}/statistic/{statIndex}
  verifyStatistic(facilityId:string | null, statIndex:number, accountPassword:string){

    const headers = new HttpHeaders().set('Accept','application/json')
      .set('Content-Type', 'application/json');
    const payload = { 'accountPassword': accountPassword }

    return this.http.post(`${this.HOSPITAL_SG_URL}/hospital/${facilityId}/statistic/${statIndex}`, payload ,{ headers, observe: 'response' });

  }

  // GET /api/hospitals/sg/pending-verified
  getHospitalSgByPendingVerify(){
    const headers = new HttpHeaders().set('Accept','application/json');

    return this.http.get(`${this.HOSPITAL_SG_URL}/pending-verify`, { headers });
  }

  getHospitalSgByStatPendingVerify(){
    const headers = new HttpHeaders().set('Accept','application/json');

    return this.http.get(`${this.HOSPITAL_SG_URL}/statistic/pending-verify`, { headers });
  }

  // GET /api/hospitals/sg?hospitalOwnership=xx&name=xx&offset=0&sortByRating=true&descending=true
  getHospitalSgList(hospitalOwnership:string, name:string, offset:number, sortByRating:boolean, descending:boolean){
    const headers = new HttpHeaders().set('Accept','application/json');
    let params;

    if(hospitalOwnership && name){
      params = new HttpParams().set('hospitalOwnership', hospitalOwnership).set('name',name).set('offset',offset).set('sortByRating',sortByRating).set('descending',descending);
    }
    if(hospitalOwnership && !name){
      params = new HttpParams().set('hospitalOwnership', hospitalOwnership).set('offset',offset).set('sortByRating',sortByRating).set('descending',descending);
    }
    if(!hospitalOwnership && name){
      params = new HttpParams().set('name',name).set('offset',offset).set('sortByRating',sortByRating).set('descending',descending);
    }
    if(!hospitalOwnership && !name){
      params = new HttpParams().set('offset',offset).set('sortByRating',sortByRating).set('descending',descending);
    }

    return this.http.get(`${this.HOSPITAL_SG_URL}`, { headers, params });
  }

  // POST /api/hospitals/sg/hospital/{facilityId}/verify-credentials
  verifyCredentials(facilityId:string, accountPassword:string, toVerify:string){

    const headers = new HttpHeaders().set('Accept','application/json')
      .set('Content-Type', 'application/json');
    const payload = { 
                      'accountPassword': accountPassword,
                      "toVerify": toVerify
                    }
    return this.http.post(`${this.HOSPITAL_SG_URL}/hospital/${facilityId}/verify-credentials`, payload ,{ headers, observe: 'response' });
  }

  // POST /api/hospitals/sg/hospital/{facilityId}/update-frequency-penalty
  setFrequencyAndPenalty(facilityId: string, accountPassword: string, updateFrequency: string, penalty: string) {
    const headers = new HttpHeaders().set('Accept','application/json').set('Content-Type', 'application/json');
    const payload = { 
      'accountPassword': accountPassword,
      'updateFrequency': updateFrequency,
      'penalty': penalty,
    }
    return this.http.post(`${this.HOSPITAL_SG_URL}/hospital/${facilityId}/update-frequency-penalty`, payload ,{ headers, observe: 'response' });

  }

  // GET /api/hospitals/sg/hospital/{facilityId}/update-frequency-penalty
  getCurrentUpdateFrequencyAndPenalty(facilityId:string){
    const headers = new HttpHeaders().set('Accept','application/json');
  
    return this.http.get(`${this.HOSPITAL_SG_URL}/hospital/${facilityId}/update-frequency-penalty` ,{ headers });

  }

}
