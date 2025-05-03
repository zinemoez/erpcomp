import { TestBed } from '@angular/core/testing';

import { DailyParameterService } from './daily-parameter.service';

describe('DailyParameterService', () => {
  let service: DailyParameterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DailyParameterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
