import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { HospitalService } from '../services/hospital.service';
import { Hospital } from '../models';

@Component({
  selector: 'app-search-hospital',
  templateUrl: './search-hospital.component.html',
  styleUrls: ['./search-hospital.component.css']
})
export class SearchHospitalComponent implements OnInit {

  form!:FormGroup
  // selectedOption!:string;
  hospitals!:Hospital[];

  constructor(private fb:FormBuilder, private activatedRoute:ActivatedRoute, private hospitalSvc:HospitalService) {}

  ngOnInit(): void {
    this.form = this.createForm();
    this.hospitalSvc.getHospitalList(this.form.value['hospital']).subscribe({
      next: (results: any) => {
        this.hospitals = results 
      },
      error: (err) => {
        console.error(err)
      }
    })
  }

  // ngOnChanges(changes: SimpleChanges): void {

  // }

  createForm():FormGroup{
    return this.fb.group({
      hospital:this.fb.control<string>('A')
    });
  }

  loadMoreOptions(){
    this.hospitalSvc.getHospitalList(this.hospitals[0].facilityName)
  }

  searchHospital(){

  }

}
