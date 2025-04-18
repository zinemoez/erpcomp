import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditInterventionComponent } from './add-edit-intervention.component';

describe('AddEditInterventionComponent', () => {
  let component: AddEditInterventionComponent;
  let fixture: ComponentFixture<AddEditInterventionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddEditInterventionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddEditInterventionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
