import {Component, OnInit} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {Location, NgForOf, NgIf} from "@angular/common";
import {UserService} from "../services/user-service.service";
import {ActivatedRoute, Router} from "@angular/router";
import {User} from "../models/user-model";
import Swal from "sweetalert2";
import {Intervention} from "../models/intervention-model";
import {Piece} from "../models/piece-model";
import {Type} from "../models/type-model";
import {Priority} from "../models/priority-model";
import {Equipment} from "../models/equipment-model";
import {EquipmentService} from "../services/equipment-service.service";
import {InterventionService} from "../services/intervention-service.service";
import {PieceService} from "../services/piece-service.service";
import {ApiService} from "../services/api.service";
import {Status} from "../models/etat-model";
import { NgSelectModule } from '@ng-select/ng-select';

@Component({
  selector: 'app-add-edit-intervention',
  standalone: true,
    imports: [
        FormsModule,
        NgForOf,
        NgIf
    ],
  templateUrl: './add-edit-intervention.component.html',
  styleUrl: './add-edit-intervention.component.css'
})
export class AddEditInterventionComponent implements OnInit{
  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    protected router: Router,
    private equipmentService: EquipmentService,
    private location: Location,
    private interventionService:InterventionService,
    private pieceService:PieceService,
    private apiService:ApiService
  ) {


  }

  interventionId!: number;
  isEditing: boolean = false;
  intervention!:Intervention
  piecess:Piece[]=[] ;
  equipments!: Equipment[] ;
  users!: User[] ;
  types!:Type[]
  statuss!:Status[]
  priorities!:Priority[]
  currentUser!:User



  message: string = '';

  // Form fields
  id!:number
  title!: string;
  equipmentId!: Equipment;
  approvedBy!: User
  status!: Status;
  type!: Type;
  staffIds: User[]= [];
  pieces:Piece[]=[]
  description!: string;
  createdAt!: string;
  createdBy: User=this.currentUser;
  updatedAt!: string;
  updatedBy!: User
  priority!: Priority;
  dataReady = false;

  ngOnInit(): void {
    this.interventionId = Number(this.route.snapshot.paramMap.get('id'));

    // Chargements parallèles
    Promise.all([
      this.loadEquipments(),
      this.loadUsers(),
      this.getPieces(),
      this.getCurrentUser(),
     this.statuss=this.getEtat(),
      this.types=this.getType(),
    this.priorities=this.getPriority(),
    ]).then(() => {
      this.dataReady = true;

      if (this.interventionId) {
        this.isEditing = true;
        this.getInterventionById(this.interventionId);
      }
    });
  }
  loadEquipments(): Promise<void> {
    return new Promise((resolve, reject) => {
      this.equipmentService.getAllEquipment().subscribe({
        next: (res) => {
          this.equipments = res;
          resolve();
        },
        error: (err) => reject(err)
      });
    });
  }

  loadUsers(): Promise<void> {
    return new Promise((resolve, reject) => {
      this.userService.getAllUsers().subscribe({
        next: (res) => {
          this.users = res;
          resolve();
        },
        error: (err) => reject(err)
      });
    });
  }
  getPriority(): Priority[] {
    return Object.values(Priority);
  }
  getType(): Type[] {
    return Object.values(Type);
  }
  getEtat(): Status[] {
    return Object.values(Status);
  }


  goBack(): void {
    this.location.back();
  }



getEquipments(): any {
    this.equipmentService.getAllEquipment().subscribe({
      next: (res) => (this.equipments = res),
      error: (err) => console.error(err.error.message),
    });
  }

  getUsers(): any {
    this.userService.getAllUsers().subscribe({
      next: (res) => {
        this.users = res;
        console.log("Users loaded:", this.users);
      },
      error: (err) => console.error(err.error.message),
    });
  }

  getCurrentUser(){
    this.apiService.getLoggedInUserInfo().subscribe({
      next: (res) => {this.currentUser=res;
        console.log("current user:", this.currentUser); },
      error: (err) => console.error(err.error.message),
    })
  }

  getPieces(): any{
    this.pieceService.getPieces().subscribe({
      next: (res) => (this.piecess = res),
      error: (err) => console.error(err.error.message),
    });
  }
  getInterventionById(interventionId: number): void {
    this.interventionService.getInterventionById(interventionId).subscribe({
      next: (res) => {
        this.id = res.id;
        this.title = res.title;
        this.priority = res.priority;
        this.status = res.status;
        this.type = res.type;
        this.createdAt = this.formatDate(res.createdAt);
        this.updatedAt = this.formatDate(res.updatedAt);
        this.description = res.description;
        this.staffIds = res.staffIds;
        this.pieces = res.pieces;

        // 🛠️ Voici la clé : on trouve l'objet exact dans la liste
        this.equipmentId = this.equipments.find(e => e.id === res.equipmentId.id)!;
        this.approvedBy = this.users.find(u => u.id === res.approvedBy.id)!;
        this.updatedBy = this.users.find(u => u.id === res.updatedBy.id)!;
      },
      error: (err) => console.error(err)
    });
  }

  handleSubmit(event: Event): void {
    event.preventDefault();
    // Create user object
    const res = new Intervention()
    res.id = this.id;
    res.title = this.title;
    res.priority = this.priority;
    res.status = this.status;
    res.type = this.type;
    res.createdAt = new Date(this.createdAt);
    res.updatedAt=new Date(this.updatedAt)
    res.createdBy = this.createdBy;
    res.approvedBy = this.approvedBy;
    res.description = this.description;
    res.staffIds = this.staffIds;
    res.pieces = this.pieces;
    res.equipmentId = this.equipmentId;
    res.updatedBy = this.updatedBy;


    // Decide whether to create or update
    if (this.isEditing) {
      res.updatedAt = new Date();
      res.updatedBy=this.currentUser


      Swal.fire({
        title: 'Confirmation',
        text: `Voulez-vous vraiment modifier  ${res.id} ?`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Oui, Modifier',
        cancelButtonText: 'Annuler'
      }).then((result) => {
        if (result.isConfirmed) {
          this.interventionService.updateIntervention(this.interventionId, res).subscribe({
            next: () => {
              console.log(res);

              Swal.fire('Modifier!', 'Le personnel a été modifié avec succès.', 'success');
            },
            error: (err) => {
              Swal.fire('Erreur', err.error?.message || 'Erreur inconnue lors de la suppression.', 'error');
            }
          });
        }
      });
      console.log(res)

      // Add this
    } else {
      res.createdAt=new Date()
      res.createdBy=this.currentUser
      Swal.fire({
        title: 'Confirmation',
        text: `Voulez-vous vraiment crier  ${res.id} ?`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Oui, Ajouter',
        cancelButtonText: 'Annuler'
      }).then((result) => {
        if (result.isConfirmed) {
          this.interventionService.addIntervention(res).subscribe({

            next: () => {
              console.log(res);

              Swal.fire('Ajouter!', 'Le bon a été ajouté avec succès.', 'success');
            },
            error: (err) => {
              Swal.fire('Erreur', err.error?.message || 'Erreur inconnue lors de la suppression.', 'error');
            }
          });
        }
      });

    }

  }

// Vérifie si l'utilisateur est sélectionné
  isUserSelected(user: User): boolean {
    return this.staffIds?.some(u => u.id === user.id);
  }


// Ajoute ou retire l'utilisateur de la sélection
  onUserCheckboxChange(event: Event, user: User): void {
    const checkbox = event.target as HTMLInputElement;

    if (checkbox.checked) {
      const alreadyExists = this.staffIds?.some(u => u.id === user.id);
      if (!alreadyExists) {
        this.staffIds?.push(user);
      }
    } else {
      this.staffIds = this.staffIds?.filter(u => u.id !== user.id);
    }
  }




  isPieceSelected(piece: Piece): boolean {
    return this.pieces?.some(p => p.id === piece.id) ?? false;
  }
  onPieceCheckboxChange(event: Event, piece: Piece): void {
    const checkbox = event.target as HTMLInputElement;

    if (checkbox.checked) {
      const alreadyExists = this.pieces?.some(p => p.id === piece.id);
      if (!alreadyExists) {
        this.pieces?.push(piece); // Ajoute l'objet Piece à pieceIds
      }
    } else {
      this.pieces = this.pieces?.filter(p => p.id !== piece.id); // Supprime l'objet Piece de pieceIds
    }
  }




  formatDate(dateString: string | Date): string {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = (`0${date.getMonth() + 1}`).slice(-2);
    const day = (`0${date.getDate()}`).slice(-2);
    return `${year}-${month}-${day}`;
  }


}
