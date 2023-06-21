import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-moh-home',
  templateUrl: './moh-home.component.html',
  styleUrls: ['./moh-home.component.css']
})
export class MohHomeComponent implements OnInit{

  param$!:Subscription;
  countryCode!: string;

  constructor(private activatedRoute:ActivatedRoute){}

  ngOnInit(): void {
    this.param$ = this.activatedRoute.params.subscribe({
      next: (params)=>{
        this.countryCode = params['countryCode']
      }
    })
  }

}
