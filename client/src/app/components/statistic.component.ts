import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HospitalService } from '../services/hospital.service';

@Component({
  selector: 'app-statistic',
  templateUrl: './statistic.component.html',
  styleUrls: ['./statistic.component.css']
})
export class StatisticComponent implements OnInit{

  form!:FormGroup
  accountPassword = this.fb.control<string>('', [Validators.required]);

  constructor(private fb:FormBuilder, private hospSvc:HospitalService){}

  ngOnInit(): void {
    this.form = this.createForm();
  }

  createForm():FormGroup{

    return this.fb.group({ 

      mortality: this.fb.control<number | undefined>( undefined ,[Validators.required]),
      patientSafety: this.fb.control<number | undefined>( undefined ,[Validators.required]),
      readmission: this.fb.control<number | undefined>( undefined ,[Validators.required]),
      patientExperience: this.fb.control<number | undefined>( undefined ,[Validators.required]),
      effectiveness: this.fb.control<number | undefined>( undefined ,[Validators.required]),
      timeliness: this.fb.control<number | undefined>( undefined ,[Validators.required]),
      medicalImagingEfficiency: this.fb.control<number | undefined>( undefined ,[Validators.required]),

    })

  }


  updateStatistic(){
    // TODO: use mat dialog for password
    this.hospSvc.updateStatistic(this.form.value, this.accountPassword.value!).subscribe();
  }

}
