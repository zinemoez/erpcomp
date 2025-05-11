import {Component, OnInit} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {RouterLink} from "@angular/router";
import {InterventionService} from "../services/intervention-service.service";
import {Intervention} from "../models/intervention-model";
import {EquipmentService} from "../services/equipment-service.service";
import {Equipment} from "../models/equipment-model";
import {Piece} from "../models/piece-model";
import Swal from "sweetalert2";
import {User} from "../models/user-model";
import {ApiService} from "../services/api.service";

@Component({
  selector: 'app-intervention',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    NgIf,
    RouterLink
  ],
  templateUrl: './intervention.component.html',
  styleUrl: './intervention.component.css'
})
export class InterventionComponent implements OnInit {
  equipments!: Equipment[] ;
  interventions: Intervention[]=[];
  intervention!: Intervention
  isEditing = false
  currentUser!:User

  searchTerm: string = '';
  filteredInterventions: Intervention[] = [];
  //// PAGINATION
  currentPage: number = 1;
  pageSize: number = 5; // nombre de pièces par page
  totalPages: number = 1;

  constructor(private interventionService: InterventionService,private apiService:ApiService, private equipmentService: EquipmentService) {

  }


  ngOnInit(): void {
    this.getEquipements()
    this.getInterventions()
    this.getCurrentUser()
  }

  getCurrentUser(){
    this.apiService.getLoggedInUserInfo().subscribe({
      next: (res) => {this.currentUser=res;
        console.log("current user:", this.currentUser); },
      error: (err) => console.error(err.error.message),


    })
  }

  filterInterventions() {
    const term = this.searchTerm.toLowerCase();
    this.filteredInterventions = this.interventions.filter(inter=>
      Object.values(inter).some(val =>
        val?.toString().toLowerCase().includes(term)
      )
    );
    this.currentPage = 1; // reset to first page
    this.updatePagination();
  }

  getInterventionsByEquipmentId(id:string) {
    this.interventionService.getInterventionsByEquipmentId(id).subscribe({
        next:data=>{this.interventions=data;
          this.filteredInterventions=data;
          this.updatePagination()}   ,
      error: (err) => {
        console.error('Erreur lors de la récupération :', err.error?.message || err.message || err);
      }
    })

  }

  getInterventions() {
    this.interventionService.getAllInterventions().subscribe({
      next: data => {this.interventions=data;

      this.filteredInterventions=data;
        this.updatePagination();},
      error: (err) => {
        console.error('Erreur lors de la récupération :', err.error?.message || err.message || err);
      }
    })

  }



  getInterventionById(id:number) {
    this.interventionService.getInterventionById(id).subscribe({
      next: data => {this.intervention =data
        },
      error: (err) => {
        console.error('Erreur lors de la récupération :', err.error?.message || err.message || err);
      }
    })


  }
  getPaginatedInterventions() : Intervention[] {
    const start = (this.currentPage - 1) * this.pageSize;
    const end = start + this.pageSize;
    return this.filteredInterventions.slice(start, end);
  }

  // Mettre à jour la page
  setPage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
    }
  }

// Mettre à jour totalPages à chaque mise à jour du tableau
  updatePagination(): void {
    this.totalPages = Math.ceil(this.filteredInterventions.length / this.pageSize);
    if (this.currentPage > this.totalPages) {
      this.currentPage = this.totalPages;
    }
  }




  deleteIntervention(id: number) {
    Swal.fire({
      title: 'Confirmation',
      text: `Voulez-vous vraiment supprimer ${id} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui, supprimer',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        this.interventionService.deleteIntervention(id).subscribe({
          next: () => {
            Swal.fire('Supprimée!', `${id} a été supprimée avec succès.`, 'success');
            this.getInterventions(); // Refresh list
          },
          error: (err) => {
            Swal.fire('Erreur', err.error?.message || 'Erreur inconnue.', 'error');
          }
        });
      }
    });
  }

  getEquipements() {
    this.equipmentService.getAllEquipment().subscribe({
      next: (res) => this.equipments = res,
      error: (err) => {
        console.error('Erreur lors de la récupération :', err.error?.message || err.message || err);
      }
    });
  }
getInterventionsByApprovedBy(id:number){
    this.interventionService.getInterventionsByApprovedId(id).subscribe({
      next: data => {this.interventions=data,

        this.filteredInterventions=data,
        this.updatePagination();},
      error: (err) => {
        console.error('Erreur lors de la récupération :', err.error?.message || err.message || err);
      }
    })
}

}
