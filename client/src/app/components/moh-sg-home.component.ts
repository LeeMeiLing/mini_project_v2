import { Component, OnInit } from '@angular/core';
import { HospitalSg } from '../models';
import { HospitalService } from '../services/hospital.service';

@Component({
  selector: 'app-moh-sg-home',
  templateUrl: './moh-sg-home.component.html',
  styleUrls: ['./moh-sg-home.component.css']
})
export class MohSgHomeComponent implements OnInit{

  hospitalsPendingVerify!:HospitalSg[];
  hospitalsPendingStatVerify!:HospitalSg[];
  allHospitalSg!:HospitalSg[];

  constructor(private hospSvc:HospitalService){}

  ngOnInit(): void {
    this.hospSvc.getHospitalSgByPendingVerify().subscribe({
      next:(r:any)=>{
        this.hospitalsPendingVerify = r as HospitalSg[];
      },
      error: (err)=>{
        console.error(err)
      },
      complete:()=>{
        console.log('completed getHospitalSgByPendingVerify')
      }
    });

    this.hospSvc.getHospitalSgByStatPendingVerify().subscribe({
      next:(r:any)=>{
        this.hospitalsPendingStatVerify = r as HospitalSg[];
      },
      error: (err)=>{
        console.error(err)
      },
      complete:()=>{
        console.log('completed getHospitalSgByStatPendingVerify')
      }
    });

    // this.hospSvc.getHospitalSgList().subscribe({
    //   next:(r:any)=>{
    //     this.allHospitalSg = r as HospitalSg[];
    //   },
    //   error: (err)=>{
    //     console.error(err)
    //   },
    //   complete:()=>{
    //     console.log('completed getAllHospitalSg')
    //   }
    // });


  }

}
