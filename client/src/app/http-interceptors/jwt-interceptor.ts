import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';

import { JwtCookieService } from '../services/jwt-cookie.service';


@Injectable()
export class JwtInterceptor implements HttpInterceptor {

    constructor(private jwtCookieSvc: JwtCookieService) {}

    intercept(req: HttpRequest<any>, next: HttpHandler) {
      // Get the jwt token from the JwtCookieService
      const authToken = this.jwtCookieSvc.getJwt();
  
      // Clone the request and replace the original headers with
      // cloned headers, updated with the authorization.
      const authReq = req.clone({
        headers: req.headers.set('Authorization', authToken)
      });
  
      // send cloned request with header to the next handler.
      return next.handle(authReq);
    }
}