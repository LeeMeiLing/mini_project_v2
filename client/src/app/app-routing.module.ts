import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login.component';
import { SignUpComponent } from './components/sign-up.component';
import { HomeComponent } from './components/home.component';
import { SearchHospitalComponent } from './components/search-hospital.component';
import { HospitalComponent } from './components/hospital.component';
import { HospitalReviewComponent } from './components/hospital-review.component';
import { SignUpHospitalComponent } from './components/sign-up-hospital.component';
import { SignUpMohComponent } from './components/sign-up-moh.component';
import { HospitalHomeComponent } from './components/hospital-home.component';
import { StatisticComponent } from './components/statistic.component';

const routes: Routes = [
  { path:'', component:LoginComponent},
  { path:'signup', component:SignUpComponent},
  { path:'signupHospital', component:SignUpHospitalComponent},
  { path:'signupMOH', component:SignUpMohComponent},
  { path:'home', component:HomeComponent},
  { path:'home/hospital', component:HospitalHomeComponent},
  { path:'searchHospital', component:SearchHospitalComponent},
  { path:'hospital/:facilityId',component:HospitalComponent},
  { path:'reviewHospital/:facilityId',component:HospitalReviewComponent},
  { path:'statistic',component:StatisticComponent},
  { path:'**', redirectTo:'/', pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
