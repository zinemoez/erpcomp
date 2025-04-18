import {Component, OnInit} from '@angular/core';
import {DatePipe, NgForOf, NgIf} from "@angular/common";
import {ActivatedRoute,RouterLink} from "@angular/router";
import {Equipment} from "../models/equipment-model";
import {EquipmentService} from "../services/equipment-service.service";
import Swal from "sweetalert2";
import {PieceService} from "../services/piece-service.service";
import {Piece} from "../models/piece-model";
import {Intervention} from "../models/intervention-model";
import {InterventionService} from "../services/intervention-service.service";
declare var bootstrap: any;

@Component({
  selector: 'app-equipment',
  standalone: true,
  imports: [
    DatePipe,
    NgIf,
    RouterLink,
    NgForOf
  ],
  templateUrl: './equipment.component.html',
  styleUrl: './equipment.component.css'
})
export class EquipmentComponent implements OnInit{
  equipment!:Equipment
  id!:any
  pieces!:Piece[]
  interventions!:Intervention[]
  constructor(private equipmentService:EquipmentService,private interventionService:InterventionService,private pieceService:PieceService, private route:ActivatedRoute) {

  }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.getEquipmpent(this.id);
    this.getPiecesByEquipment(this.id)
    this.getInterventionsByEquipment(this.id)
  }


getEquipmpent(id:string){
    this.equipmentService.getEquipmentById(id).subscribe({
      next:(res)=>this.equipment=res,
      error:(err)=>err.error.message()
    })
}

  supprimerEquipment(id: string): void {
    Swal.fire({
      title: 'Confirmation',
      text: `Voulez-vous vraiment supprimer  ${id} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui, supprimer',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        this.equipmentService.deleteEquipment(id).subscribe({
          next: () => {
            Swal.fire('Supprimé!', 'L’équipement a été supprimé avec succès.', 'success');
          },
          error: (err) => {
            Swal.fire('Erreur', err.error?.message || 'Erreur inconnue lors de la suppression.', 'error');
          }
        });
      }
    });
  }

    getPiecesByEquipment(id: string) {
    this.pieceService.getPiecesByEquipmentId(id).subscribe({
      next:(res)=>this.pieces=res,
      error:(err)=>err.error.message()
    })
      const modal = new bootstrap.Modal(document.getElementById('piecesModal'));
      modal.show();
    }

  getInterventionsByEquipment(id: string) {
    this.interventionService.getInterventionsByEquipmentId(id).subscribe({
      next:(res)=>this.interventions=res,
      error:(err)=>err.error.message()
    })
    const modal = new bootstrap.Modal(document.getElementById('interventionsModal'));
    modal.show();
  }

}
