import { TestBed } from '@angular/core/testing';

import { JwtCookieService } from './jwt-cookie.service';

describe('JwtCookieService', () => {
  let service: JwtCookieService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JwtCookieService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
