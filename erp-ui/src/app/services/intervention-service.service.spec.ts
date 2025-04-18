import { TestBed } from '@angular/core/testing';

import { InterventionServiceService } from './intervention-service.service';

describe('InterventionServiceService', () => {
  let service: InterventionServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InterventionServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
