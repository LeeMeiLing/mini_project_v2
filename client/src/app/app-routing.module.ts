import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login.component';
import { SignUpComponent } from './components/sign-up.component';
import { HomeComponent } from './components/home.component';
import { SearchHospitalComponent } from './components/search-hospital.component';
import { SearchHospitalResultComponent } from './components/search-hospital-result.component';

const routes: Routes = [
  { path:'', component:LoginComponent},
  { path:'signup', component:SignUpComponent},
  { path:'home', component:HomeComponent},
  { path:'searchHospital', component:SearchHospitalComponent,
    children:[
      { path:'result/:facilityId',component:SearchHospitalResultComponent}
    ]
  },
  { path:'**', redirectTo:'/', pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
