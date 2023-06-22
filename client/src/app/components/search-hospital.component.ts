import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { HospitalService } from '../services/hospital.service';
import { Hospital, HospitalSg, Moh } from '../models';
import { tap } from 'rxjs';

@Component({
  selector: 'app-search-hospital',
  templateUrl: './search-hospital.component.html',
  styleUrls: ['./search-hospital.component.css']
})
export class SearchHospitalComponent implements OnInit{

  form!:FormGroup;
  states!:string[];
  cities!:string[];
  hospitals!:Hospital[] |null;
  hospitalSgList!:HospitalSg[] |null;
  offset:number = 0;
  descending:boolean = false;
  sortByRating:boolean = false;
  disableSort:boolean = true;
  count!:number;
  seeResponse!:any;

  mohList!:Moh[];
  countryCodeForm = this.fb.group({countryCode: this.fb.control('')})
  showSgForm!:boolean;
  showUsForm!:boolean;

  constructor(private fb:FormBuilder, private hospitalSvc:HospitalService) {}
  
  ngOnInit(): void {

    // this.form = this.createForm();

    this.hospitalSvc.getMohList().subscribe({
      next: (r:any)=>{
        this.mohList = r as Moh[];
        console.log('mohList ', this.mohList)
      },
      error: (err)=>{
        console.error(err)
      },
      complete: ()=>{
        console.log('completed getMohList')
      }
    });

  }

  onCountryCodeChange(){

    this.offset = 0;
    this.hospitals = null;
    this.hospitalSgList = null;


    this.showSgForm = false;
    this.showUsForm = false;

    if(this.countryCodeForm.value['countryCode'] == 'SG'){
      this.showSgForm = true;
      this.form = this.createSgForm();
    }
    
    if(this.countryCodeForm.value['countryCode'] == 'US'){

      this.showUsForm = true;
      this.form = this.createUsForm();

      this.hospitalSvc.getStates().pipe(
        tap(r => this.states = r as string[])
      ).subscribe({
        next: ()=> {
          console.log(this.states)
        },
        error: err=>{
          console.error(err)
        },
        complete:() => {
          console.log('completed get states') // debug
        }
      });
    }
  }

  onStateChange(){
    console.log("onChange()")
    console.log('state', this.form.value['state']);
    console.log('city', this.form.value['city']);
    console.log('name', this.form.value['hospitalName']);

    this.form.patchValue({city:''}); // reset city to ''

    console.log('state', this.form.value['state']);
    console.log('city', this.form.value['city']);
    console.log('name', this.form.value['hospitalName']);
    
    if(this.form.value['state']){

      this.hospitalSvc.getCities(this.form.value['state']).pipe(
        tap(r => this.cities = r as string[])
      ).subscribe({
        next: ()=> {
          console.log(this.cities)
        },
        error: err=>{
          console.error(err)
        },
        complete:() => {
          console.log('completed get cities') // debug
        }
      });

    }
    
  }

  createSgForm(): FormGroup {
    return this.fb.group({
      hospitalOwnership:this.fb.control<string>(''),
      hospitalName:this.fb.control<string>('')
    });
  }

  searchSgHospitals(){

    this.form.valueChanges.subscribe(
      (value) => { 
        console.log('Form value changed:', value);
        this.offset = 0;
        this.hospitals = null;
        this.hospitalSgList = null;

      }
    );

    console.log('in searchSgHospital()');

    this.hospitalSvc.getHospitalSgList
    (this.form.value['hospitalOwnership'], this.form.value['hospitalName'], this.offset, this.sortByRating, this.descending )?.pipe(
        tap((r:any) => {
          this.seeResponse = r
          this.hospitalSgList = r['results'] as HospitalSg[];
          this.count = r['count'];
        }),
      ).subscribe({
        next: ()=> {
          console.log(this.seeResponse);
          console.log(this.hospitals);
          console.log(this.count);
          console.log('count/20 ', this.count/20)
        },
        error: err=>{
          this.hospitals = [];
          this.count = 0;
          console.error(err)
        },
        complete:() => {
          console.log('completed search Sg Hospital') // debug
        }
      });
  }

  createUsForm():FormGroup{
    return this.fb.group({
      state:this.fb.control<string>(''),
      city:this.fb.control<string>(''),
      hospitalName:this.fb.control<string>('')
    });
  }

  searchUsHospitals(){

    this.form.valueChanges.subscribe(
      (value) => { 
        console.log('Form value changed:', value);
        this.offset = 0;
        this.hospitals = null;
        this.hospitalSgList = null;

      }
    );

    console.log('in searchUsHospital()');

    console.log('state', this.form.value['state']);
    console.log('city', this.form.value['city']);
    console.log('name', this.form.value['hospitalName']);

    this.hospitalSvc.getHospitals( this.form.value['state'], this.form.value['city'], 
      this.form.value['hospitalName'], this.offset, this.sortByRating, this.descending )?.pipe(
        tap((r:any) => {
          this.seeResponse = r
          this.hospitals = r['results'] as Hospital[];
          this.count = r['count'];
        }),
      ).subscribe({
        next: ()=> {
          console.log(this.seeResponse);
          console.log(this.hospitals);
          console.log(this.count);
          console.log('count/20 ', this.count/20)
        },
        error: err=>{
          this.hospitals = [];
          this.count = 0;
          console.error(err);
        },
        complete:() => {
          console.log('completed search Hospital') // debug
        }
      });
  
  }

  sort(){
    this.sortByRating = true;
    this.descending = !this.descending;
    console.log('sort()', 'sortByRating: ',this.sortByRating,'descending: ', this.descending);

    if(this.countryCodeForm.value['countryCode']!.toLowerCase() == 'sg'.toLowerCase()){
      this.searchSgHospitals();
    }
    else if(this.countryCodeForm.value['countryCode']!.toLowerCase() == 'us'.toLowerCase()){
      this.searchUsHospitals();
    }
    
  }

  undoSort(){
    this.sortByRating = false;
    this.disableSort = !this.disableSort;
    console.log('undoSort()', 'sortByRating: ',this.sortByRating,'descending: ', this.descending);

    if(this.countryCodeForm.value['countryCode']!.toLowerCase() == 'sg'.toLowerCase()){
      this.searchSgHospitals();
    }
    else if(this.countryCodeForm.value['countryCode']!.toLowerCase() == 'us'.toLowerCase()){
      this.searchUsHospitals();
    }
  }

  loadNextPage(){
    this.offset++;
     if(this.countryCodeForm.value['countryCode']!.toLowerCase() == 'sg'.toLowerCase()){
      this.searchSgHospitals();
    }
    else if(this.countryCodeForm.value['countryCode']!.toLowerCase() == 'us'.toLowerCase()){
      this.searchUsHospitals();
    }
  }

  loadPreviousPage(){
    this.offset--;
    if(this.countryCodeForm.value['countryCode']!.toLowerCase() == 'sg'.toLowerCase()){
      this.searchSgHospitals();
    }
    else if(this.countryCodeForm.value['countryCode']!.toLowerCase() == 'us'.toLowerCase()){
      this.searchUsHospitals();
    }
  }

}
