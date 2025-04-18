import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditPieceComponent } from './add-edit-piece.component';

describe('AddEditPieceComponent', () => {
  let component: AddEditPieceComponent;
  let fixture: ComponentFixture<AddEditPieceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddEditPieceComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddEditPieceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
