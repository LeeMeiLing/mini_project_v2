import { Component, OnInit } from '@angular/core';
import { HospitalSg } from '../models';
import { HospitalService } from '../services/hospital.service';
import { tap } from 'rxjs';

@Component({
  selector: 'app-moh-sg-home',
  templateUrl: './moh-sg-home.component.html',
  styleUrls: ['./moh-sg-home.component.css']
})
export class MohSgHomeComponent implements OnInit{

  hospitalsPendingVerify!:HospitalSg[];
  hospitalsPendingStatVerify!:HospitalSg[];
  allHospitalSg!:HospitalSg[];
  offset:number = 0;
  seeResponse!:any;
  count: any;


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

    this.searchSgHospitals()

  }

  searchSgHospitals(){

    this.hospSvc.getHospitalSgList('', '', this.offset, false, false ).pipe(
        tap((r:any) => {
          this.seeResponse = r
          this.allHospitalSg = r['results'] as HospitalSg[];
          this.count = r['count'];
        }),
      ).subscribe({
        next: ()=> {

        },
        error: err=>{
          this.allHospitalSg = [];
          this.count = 0;
          console.error(err)
        },
        complete:() => {
          console.log('completed search Sg Hospital') // debug
        }
      });
  }


  loadNextPage(){
    this.offset++;
    this.searchSgHospitals();
  }

  loadPreviousPage(){
    this.offset--;
    this.searchSgHospitals();

  }

}
