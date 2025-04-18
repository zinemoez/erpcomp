import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AffDepartmentComponent } from './aff-department.component';

describe('AffDepartmentComponent', () => {
  let component: AffDepartmentComponent;
  let fixture: ComponentFixture<AffDepartmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AffDepartmentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AffDepartmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
