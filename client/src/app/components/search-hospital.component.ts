import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { HospitalService } from '../services/hospital.service';
import { Hospital } from '../models';
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
  hospitals!:Hospital[];
  offset:number = 0;
  descending:boolean = false;
  sortByRating:boolean = false;
  disableSort:boolean = true;

  constructor(private fb:FormBuilder, private hospitalSvc:HospitalService) {}
  
  ngOnInit(): void {

    this.form = this.createForm();
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

  onChange(){
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

  createForm():FormGroup{
    return this.fb.group({
      state:this.fb.control<string>(''),
      city:this.fb.control<string>(''),
      hospitalName:this.fb.control<string>('')
    });
  }


  searchHospitals(){

    console.log('in searchHospital()');

    console.log('state', this.form.value['state']);
    console.log('city', this.form.value['city']);
    console.log('name', this.form.value['hospitalName']);

    this.hospitalSvc.getHospitals( this.form.value['state'], this.form.value['city'], 
      this.form.value['hospitalName'], this.offset, this.sortByRating, this.descending )?.pipe(
        tap(r => this.hospitals = r as Hospital[])
      ).subscribe({
        next: ()=> {
          console.log(this.hospitals)
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
    this.searchHospitals();
  }

  undoSort(){
    this.sortByRating = false;
    this.disableSort = !this.disableSort;
    console.log('undoSort()', 'sortByRating: ',this.sortByRating,'descending: ', this.descending);
    this.searchHospitals();
  }

}
