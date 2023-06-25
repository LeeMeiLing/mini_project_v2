import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login.component';
import { SignUpComponent } from './components/sign-up.component';
import { SearchHospitalComponent } from './components/search-hospital.component';
import { HospitalComponent } from './components/hospital.component';
import { HospitalReviewComponent } from './components/hospital-review.component';
import { SignUpHospitalComponent } from './components/sign-up-hospital.component';
import { HospitalHomeComponent } from './components/hospital-home.component';
import { StatisticComponent } from './components/statistic.component';
import { ShowStatisticComponent } from './components/show-statistic.component';
import { MohSgHomeComponent } from './components/moh-sg-home.component';
import { MohUsHomeComponent } from './components/moh-us-home.component';
import { StatisticListComponent } from './components/statistic-list.component';
import { ReviewListComponent } from './components/review-list.component';

const routes: Routes = [
  { path:'', component:LoginComponent},
  { path:'signup', component:SignUpComponent},
  { path:'signupHospital', component:SignUpHospitalComponent},
  { path:'home/hospital', component:HospitalHomeComponent},
  { path:'home/moh/sg', component:MohSgHomeComponent},
  { path:'home/moh/us', component:MohUsHomeComponent},
  { path:'searchHospital', component:SearchHospitalComponent},
  { path:'hospital/:hospitalCountry/:facilityId',component:HospitalComponent, 
    children:[
      { path:'review-list',component:ReviewListComponent },
      { path:'statistic-list',component:StatisticListComponent }
    ]
  },
  { path:'reviewHospital/:hospitalCountry/:facilityId',component:HospitalReviewComponent},
  { path:'statistic',component:StatisticComponent},
  { path:'statistic/:facilityId/:statIndex',component:ShowStatisticComponent},
  { path:'**', redirectTo:'/', pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
