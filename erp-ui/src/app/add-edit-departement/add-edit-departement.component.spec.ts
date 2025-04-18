import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditDepartementComponent } from './add-edit-departement.component';

describe('AddEditDepartementComponent', () => {
  let component: AddEditDepartementComponent;
  let fixture: ComponentFixture<AddEditDepartementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddEditDepartementComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddEditDepartementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
