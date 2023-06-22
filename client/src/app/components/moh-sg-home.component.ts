import { Component, OnInit } from '@angular/core';
import { HospitalSg } from '../models';
import { HospitalService } from '../services/hospital.service';

@Component({
  selector: 'app-moh-sg-home',
  templateUrl: './moh-sg-home.component.html',
  styleUrls: ['./moh-sg-home.component.css']
})
export class MohSgHomeComponent implements OnInit{

  hospitalsPendingVerified!:HospitalSg[];

  constructor(private hospSvc:HospitalService){}

  ngOnInit(): void {
    this.hospSvc.getHospitalSgByPendingVerified().subscribe({
      next:(r:any)=>{
        this.hospitalsPendingVerified = r as HospitalSg[];
      },
      error: (err)=>{
        console.error(err)
      },
      complete:()=>{
        console.log('completed getHospitalSgByPendingVerified', this.hospitalsPendingVerified)
      }
    });
    // this.hospSvc.getHospitalSgByStatPendingVerification().subscribe({
    //   next:(r:any)=>{
    //     this.hospitalsPendingVerified = r as HospitalSg[];
    //   },
    //   error: (err)=>{
    //     console.error(err)
    //   },
    //   complete:()=>{
    //     console.log('completed getHospitalSgByPendingVerified')
    //   }
    // });
    // this.hospSvc.getHospitalSgByPendingVerified().subscribe({
    //   next:(r:any)=>{
    //     this.hospitalsPendingVerified = r as HospitalSg[];
    //   },
    //   error: (err)=>{
    //     console.error(err)
    //   },
    //   complete:()=>{
    //     console.log('completed getHospitalSgByPendingVerified')
    //   }
    // });


  }

}
