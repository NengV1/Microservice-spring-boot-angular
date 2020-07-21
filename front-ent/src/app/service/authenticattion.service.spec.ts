import { TestBed } from '@angular/core/testing';

import { AuthenticattionService } from './authenticattion.service';

describe('AuthenticattionService', () => {
  let service: AuthenticattionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthenticattionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
