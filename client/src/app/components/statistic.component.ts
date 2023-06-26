import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HospitalService } from '../services/hospital.service';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { PasswordComponent } from './password.component';
import { JwtCookieService } from '../services/jwt-cookie.service';
import { HospitalDecodedToken } from '../models';

@Component({
  selector: 'app-statistic',
  templateUrl: './statistic.component.html',
  styleUrls: ['./statistic.component.css']
})
export class StatisticComponent implements OnInit{

  form!:FormGroup
  accountPassword!:string;
  statIndex!:number;
  waiting=false;

  userRole!:string;
  userFacilityId!:string;

  constructor(public dialog:MatDialog, private fb:FormBuilder, private hospSvc:HospitalService, private router:Router, 
    private jwtCookieSvc:JwtCookieService){}

  ngOnInit(): void {

    this.form = this.createForm();
    this.userRole = this.jwtCookieSvc.decodeToken(this.jwtCookieSvc.getJwt()).userRole;
          console.log('>> userRole: ', this.userRole)

    if(this.userRole == 'hospital'){
      const decodedToken = this.jwtCookieSvc.decodeToken(this.jwtCookieSvc.getJwt()) as HospitalDecodedToken;
      this.userFacilityId = decodedToken.facilityId;
    }

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

    const dialogRef = this.dialog.open(PasswordComponent, {
      width: '250px',
      data: {accountPassword: this.accountPassword}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.accountPassword = result;
      console.log("account password " , this.accountPassword)
      this.waiting = true;
      this.hospSvc.updateStatistic(this.form.value, this.accountPassword).subscribe({
        next: (r:any) => {
          this.statIndex = r['statIndex']; // TODO: loading message while waiting
        },
        error:(err)=> {
          console.error('Failed to update statistic: ', err)
          if(err.status == 401){
            alert(err.error.error)
          }
          this.accountPassword = '';
        },
        complete:() => {
          this.waiting = false;
          console.log('completed updateStatistic')
          this.accountPassword = '';
          this.router.navigate(['/statistic', this.userFacilityId, this.statIndex]);
        }
      });
    });
    

  }

}
