import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { HospitalService } from '../services/hospital.service';
import { Statistic } from '../models';

@Component({
  selector: 'app-show-statistic',
  templateUrl: './show-statistic.component.html',
  styleUrls: ['./show-statistic.component.css']
})
export class ShowStatisticComponent implements OnInit, OnDestroy{
  
  param$!:Subscription;
  statIndex!: number;
  statistic!:Statistic;
  dateUpdated!:Date;

  constructor(private activateRoute:ActivatedRoute, private hospSvc:HospitalService){}

  ngOnDestroy(): void {
    this.param$.unsubscribe();
  }

  ngOnInit(): void {
    this.param$ = this.activateRoute.params.subscribe({
      next: async (params) => {
        this.statIndex = params['statIndex'];
        await this.getStatistic();
      }
    })
  }


  async getStatistic(){

    this.hospSvc.getStatistic(this.statIndex).subscribe({
      next:(r:any)=>{
        this.statistic = r as Statistic;
        this.timestampToDate();
      },
      error: (err)=>{
        console.error('error getStatistic: ', err);
      },
      complete: ()=>{
        console.log('complete getStatistic: ', this.statistic);
      }
    });

  }

  timestampToDate(){

    // Convert Solidity timestamp to Date object
    this.dateUpdated = new Date(parseInt(this.statistic['timestamp']) * 1000); // Multiply by 1000 to convert from seconds to milliseconds

  }

}
