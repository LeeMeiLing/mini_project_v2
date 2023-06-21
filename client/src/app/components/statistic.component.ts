import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HospitalService } from '../services/hospital.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-statistic',
  templateUrl: './statistic.component.html',
  styleUrls: ['./statistic.component.css']
})
export class StatisticComponent implements OnInit{

  form!:FormGroup
  accountPassword = this.fb.control<string>('', [Validators.required]);
  statIndex!:number;

  constructor(private fb:FormBuilder, private hospSvc:HospitalService, private router:Router){}

  ngOnInit(): void {

    this.form = this.createForm();
  }

  createForm():FormGroup{

    return this.fb.group({ 

      mortality: this.fb.control<number | undefined>( undefined ,[Validators.required, Validators.min(0), Validators.max(100)]),
      patientSafety: this.fb.control<number | undefined>( undefined ,[Validators.required, Validators.min(0), Validators.max(100)]),
      readmission: this.fb.control<number | undefined>( undefined ,[Validators.required, Validators.min(0), Validators.max(100)]),
      patientExperience: this.fb.control<number | undefined>( undefined ,[Validators.required, Validators.min(0), Validators.max(100)]),
      effectiveness: this.fb.control<number | undefined>( undefined ,[Validators.required, Validators.min(0), Validators.max(100)]),
      timeliness: this.fb.control<number | undefined>( undefined ,[Validators.required, Validators.min(0), Validators.max(100)]),
      medicalImagingEfficiency: this.fb.control<number | undefined>( undefined ,[Validators.required, Validators.min(0), Validators.max(100)]),

    })

  }


  updateStatistic(){

    // TODO: use mat dialog for password
    this.hospSvc.updateStatistic(this.form.value, this.accountPassword.value!).subscribe({
      next: (r:any) => {
        this.statIndex = r['statIndex'];
      },
      error:(err)=> console.error('Failed to update statistic: ', err),
      complete:() => {
        console.log('completed updateStatistic')
        this.router.navigate(['/statistic',this.statIndex]);
      }
    });
  }

}
