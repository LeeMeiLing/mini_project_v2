import { NgModule, isDevMode } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ServiceWorkerModule } from '@angular/service-worker';
import { LoginComponent } from './components/login.component';
import { SignUpComponent } from './components/sign-up.component';


import { CookieService  } from 'ngx-cookie-service';
import { JwtCookieService } from './services/jwt-cookie.service';
import { JwtInterceptor } from './http-interceptors/jwt-interceptor';
import { HomeComponent } from './components/home.component';
import { HospitalComponent } from './components/hospital.component';
import { SearchHospitalComponent } from './components/search-hospital.component';
import { HospitalReviewComponent } from './components/hospital-review.component';
import { SignUpHospitalComponent } from './components/sign-up-hospital.component';
import { HospitalHomeComponent } from './components/hospital-home.component';
import { StatisticComponent } from './components/statistic.component';
import { ShowStatisticComponent } from './components/show-statistic.component';
import { MohSgHomeComponent } from './components/moh-sg-home.component';
import { MohUsHomeComponent } from './components/moh-us-home.component';
import { HospitalSgComponent } from './components/hospital-sg.component';
import { HospitalUsComponent } from './components/hospital-us.component';
import { StatisticListComponent } from './components/statistic-list.component';
import { ReviewListComponent } from './components/review-list.component';
import { PasswordComponent } from './components/password.component';
import { FrequencyPenaltyComponent } from './components/frequency-penalty.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignUpComponent,
    HomeComponent,
    HospitalComponent,
    SearchHospitalComponent,
    HospitalReviewComponent,
    SignUpHospitalComponent,
    HospitalHomeComponent,
    StatisticComponent,
    ShowStatisticComponent,
    MohSgHomeComponent,
    MohUsHomeComponent,
    HospitalSgComponent,
    HospitalUsComponent,
    StatisticListComponent,
    ReviewListComponent,
    PasswordComponent,
    FrequencyPenaltyComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ServiceWorkerModule.register('ngsw-worker.js', {
      enabled: !isDevMode(),
      // Register the ServiceWorker as soon as the application is stable
      // or after 30 seconds (whichever comes first).
      registrationStrategy: 'registerWhenStable:30000'
    })
  ],
  providers: [
    CookieService,
    JwtCookieService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true
    }
   ],
  bootstrap: [AppComponent],

})
export class AppModule { }
