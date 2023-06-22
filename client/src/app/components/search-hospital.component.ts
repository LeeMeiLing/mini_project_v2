import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { HospitalService } from '../services/hospital.service';
import { Hospital, Moh } from '../models';
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
  hospitals!:Hospital[] | null;
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

  
    // this.hospitalSvc.getStates().pipe(
    //   tap(r => this.states = r as string[])
    // ).subscribe({
    //   next: ()=> {
    //     console.log(this.states)
    //   },
    //   error: err=>{
    //     console.error(err)
    //   },
    //   complete:() => {
    //     console.log('completed get states') // debug
    //   }
    // });

  }

  onCountryCodeChange(){

    this.offset = 0;
    this.hospitals = null;
     
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
      city:this.fb.control<string>(''),
      hospitalName:this.fb.control<string>('')
    });
  }

  searchSgHospitals(){

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
      }
    );

    console.log('in searchHospital()');

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
          console.error(err)
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
    this.searchUsHospitals();
  }

  undoSort(){
    this.sortByRating = false;
    this.disableSort = !this.disableSort;
    console.log('undoSort()', 'sortByRating: ',this.sortByRating,'descending: ', this.descending);
    this.searchUsHospitals();
  }

  loadNextPage(){
    this.offset++;
    this.searchUsHospitals();
  }

  loadPreviousPage(){
    this.offset--;
    this.searchUsHospitals();
  }

}
