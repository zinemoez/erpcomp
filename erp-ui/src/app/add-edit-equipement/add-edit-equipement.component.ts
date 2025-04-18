import {Component, OnInit} from '@angular/core';
import {DatePipe, NgForOf, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {EquipmentService} from "../services/equipment-service.service";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {DepartmentService} from "../services/department-service.service";
import {Equipment} from "../models/equipment-model";
import {Department} from "../models/department-model";
import {Piece} from "../models/piece-model";
import {Intervention} from "../models/intervention-model";
import Swal from "sweetalert2";
import { Location } from '@angular/common';
@Component({
  selector: 'app-add-edit-equipement',
  standalone: true,
  imports: [
    DatePipe,
    FormsModule,
    NgForOf,
    NgIf,
    RouterLink
  ],
  templateUrl: './add-edit-equipement.component.html',
  styleUrl: './add-edit-equipement.component.css'
})
export class AddEditEquipementComponent  implements OnInit{
  constructor(
    private equipmentService: EquipmentService,
    private route: ActivatedRoute,
    protected router: Router,
    private departmentService: DepartmentService,
    private location: Location
  ) {}

  equipmentId:any;
  isEditing: boolean = false;
  equipment: Equipment=new Equipment()
  departments: Department[] = [];
  message: string = '';

  // Form fields
  id!:string;
  name!:string;
  description!:string;
  dateMiseEnService!:Date;
  departmentId!:Department;
  pieces!:Piece[];
  interventions!:Intervention[]

  ngOnInit(): void {
    this.equipmentId = this.route.snapshot.paramMap.get('id');
    this.getDepartments();
    this.getPiecesByEquipemntId(this.equipmentId);
    this.getInterventionsByequipmentId(this.equipmentId)
    if (this.equipmentId) {
      this.isEditing = true;
      this.getEquipmentById(this.equipmentId);
    }
  }



  getDepartments(): void {
    this.departmentService.getAllDepartments().subscribe({
      next: (res) => (this.departments = res),
      error: (err) => console.error(err.error.message),
    });
  }
// RECUPERER  UN EQUIPEMENT BY ID
  getEquipmentById(equipmentId: string): void {
    this.equipmentService.getEquipmentById(equipmentId).subscribe({
      next: (res) => {
        this.equipment = res;
        this.id=res.id;
        this.name=res.name;
       this.departmentId= res.departmentId;
       this.pieces= res.pieces;
        this.interventions=res.interventions;
        this.description=res.description;
        this.dateMiseEnService=res.dateMiseEnService;
      },
      error: (err) => console.error(err),
    });
  }
//MODIFIER OU AJOUTER UN EQUIPEMENT
  handleSubmit(event: Event): void {
    event.preventDefault();
    const res = new Equipment()
    res.id=this.id;
    res.name=this.name;
    res.departmentId=this.departmentId;
    res.pieces=this.pieces;
    res.interventions= this.interventions;
    res.description=this.description;
    res.dateMiseEnService=this.dateMiseEnService;
console.log('l equipment es', res)
    // Decide whether to create or update
    if (this.isEditing) {
      console.log('Updating equipment:', res);

      Swal.fire({
        title: 'Confirmation',
        text: `Voulez-vous vraiment modifier  ${res.id} ?`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Oui, Modifier',
        cancelButtonText: 'Annuler'
      }).then((result) => {
        if (result.isConfirmed) {
          this.equipmentService.updateEquipment(this.equipmentId, res).subscribe({
            next: () => {
              Swal.fire('Modifier!', 'L equipement a été modifié avec succès.', 'success');
            },
            error: (err) => {
              Swal.fire('Erreur', err.error?.message || 'Erreur inconnue lors de la suppression.', 'error');
            }
          });
        }
      });

      // Add this
    } else {
      Swal.fire({
        title: 'Confirmation',
        text: `Voulez-vous vraiment crier  ${res.id} ?`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Oui, Ajouter',
        cancelButtonText: 'Annuler'
      }).then((result) => {
        if (result.isConfirmed) {
          this.equipmentService.createEquipment(res).subscribe({
            next: () => {
              Swal.fire('Ajouter!', 'L equipement a été ajouté avec succès.', 'success');
            },
            error: (err) => {
              Swal.fire('Erreur', err.error?.message || 'Erreur inconnue lors de la suppression.', 'error');
            }
          });
        }
      });

    }

  }
  showMessage(message: string): void {
    this.message = message;
    setTimeout(() => {
      this.message = '';
    }, 4000);
  }
  goBack(): void {
    this.location.back();
  }


  private getInterventionsByequipmentId(equipmentId: string) {

  }

  private getPiecesByEquipemntId(equipmentId: string) {

  }
}
