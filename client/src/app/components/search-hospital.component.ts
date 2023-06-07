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
    console.log(this.form.value['state']);

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


  searchHospital(){

    this.hospitalSvc.getHospitals( this.form.value['state'], this.form.value['city'], 
      this.form.value['hospitalName'], this.offset )?.subscribe({});


    // GET /api/hospitals

    // GET /api/hospitals?name=name

    // GET /api/hospitals/{state}

    // GET /api/hospitals/{state}?name=name

    // GET /api/hospitals/{state}/{city}

    // GET /api/hospitals/{state}/{city}?name=name

    
  }

}
