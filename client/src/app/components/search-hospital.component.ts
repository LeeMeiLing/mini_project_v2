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
  totalPage!:number;

  mohList!:Moh[];
  countryCodeForm = this.fb.group({countryCode: this.fb.control('')})
  showSgForm!:boolean;
  showUsForm!:boolean;
  buttonClick = 1; // 1: disable, 2: sort desc, 3: sort asc

  constructor(private fb:FormBuilder, private hospitalSvc:HospitalService) {}
  
  ngOnInit(): void {

    this.hospitalSvc.getMohList().subscribe({
      next: (r:any)=>{
        this.mohList = r as Moh[];
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

    if(this.countryCodeForm.value['countryCode']?.toLowerCase() == 'SG'.toLowerCase()){
      this.showSgForm = true;
      this.form = this.createSgForm();
    }
    
    if(this.countryCodeForm.value['countryCode']?.toLowerCase() == 'US'.toLowerCase()){

      this.showUsForm = true;
      this.form = this.createUsForm();

      this.hospitalSvc.getStates().pipe(
        tap(r => this.states = r as string[])
      ).subscribe({
        next: ()=> {
        },
        error: err=>{
          console.error(err)
        },
        complete:() => {
          console.log('completed get states') 
        }
      });
    }
  }

  onStateChange(){


    this.form.patchValue({city:''}); // reset city to ''

    
    if(this.form.value['state']){

      this.hospitalSvc.getCities(this.form.value['state']).pipe(
        tap(r => this.cities = r as string[])
      ).subscribe({
        next: ()=> {
        },
        error: err=>{
          console.error(err)
        },
        complete:() => {
          console.log('completed get cities') 
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
          this.totalPage = Math.ceil(this.count/20);
        },
        error: err=>{
          this.hospitals = [];
          this.count = 0;
          console.error(err)
        },
        complete:() => {
          console.log('completed search Sg Hospital') 
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
        this.offset = 0;
        this.hospitals = null;
        this.hospitalSgList = null;

      }
    );

    this.hospitalSvc.getHospitals( this.form.value['state'], this.form.value['city'], 
      this.form.value['hospitalName'], this.offset, this.sortByRating, this.descending )?.pipe(
        tap((r:any) => {
          this.seeResponse = r
          this.hospitals = r['results'] as Hospital[];
          this.count = r['count'];
        }),
      ).subscribe({
        next: ()=> {
          this.totalPage = Math.ceil(this.count/20);

        },
        error: err=>{
          this.hospitals = [];
          this.count = 0;
          console.error(err);
        },
        complete:() => {
          console.log('completed search Hospital') 
        }
      });
  
  }

  sort(){

    this.buttonClick++;
    if(this.buttonClick == 4){
      this.buttonClick = 1;
    }

    if(this.buttonClick == 1){
      this.disableSort = true;
      this.sortByRating = false;
      this.descending = false;
    }

    if(this.buttonClick == 2){
      this.disableSort = false;
      this.sortByRating = true;
      this.descending = true;
    }

    if(this.buttonClick == 3){
      this.disableSort = false;
      this.sortByRating = true;
      this.descending = false;;
    }
    

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
