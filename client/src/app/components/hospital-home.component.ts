import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-hospital-home',
  templateUrl: './hospital-home.component.html',
  styleUrls: ['./hospital-home.component.css']
})
export class HospitalHomeComponent implements OnInit{

  param$!:Subscription;
  // facilityId!:string;

  // constructor(private activatedRoute:ActivatedRoute){}

  ngOnInit(): void {
    // this.param$ = this.activatedRoute.params.subscribe({
    //   next: (params) => {
    //     this.facilityId = params['facilityId']
    //   }
    // })
  }

}
