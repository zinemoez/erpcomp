import { TestBed } from '@angular/core/testing';

import { ParameterTypeService } from './parameter-type.service';

describe('ParameterTypeService', () => {
  let service: ParameterTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ParameterTypeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
