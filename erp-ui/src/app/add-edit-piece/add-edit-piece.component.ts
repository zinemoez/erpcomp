import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { Location, NgForOf, NgIf } from "@angular/common";
import { Piece } from "../models/piece-model";
import Swal from "sweetalert2";
import { Equipment } from "../models/equipment-model";
import { Category } from "../models/category";
import { ActivatedRoute } from "@angular/router";
import { CategorieService } from "../services/categorie.service";
import { PieceService } from "../services/piece-service.service";
import { EquipmentService } from "../services/equipment-service.service";
import {Intervention} from "../models/intervention-model";
import {InterventionService} from "../services/intervention-service.service";

@Component({
  selector: 'app-add-edit-piece',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    NgIf,
    ReactiveFormsModule
  ],
  templateUrl: './add-edit-piece.component.html',
  styleUrl: './add-edit-piece.component.css'
})
export class AddEditPieceComponent implements OnInit {
  piece!: Piece;
  pieceId!: any;
  pieces: Piece[] = [];
  filteredPieces: Piece[] = [];
  isEditing: boolean = false;

  currentPage: number = 1;
  pageSize: number = 5;
  totalPages: number = 1;

  id: string='';
  name!: string;
  sku!: string;
  price!: number;
  stockQuantity!: number;
  description!: string;
  expiryDate!: string;   // changed from Date to string
  updatedAt: string = ''; // changed from Date to string
  createdAt!: string;     // changed from Date to string
  equipmentId!: string;
  categorieId!: string;
  interventions!:Intervention[]

  equipements: Equipment[] = [];
  categories: Category[] = [];
  searchTerm: string = '';

  constructor(
    private route: ActivatedRoute,
    private categorieService: CategorieService,
    private pieceService: PieceService,
    private equipmentService: EquipmentService,
    private location: Location,
    private interventionService:InterventionService
  ) {}

  ngOnInit(): void {
    this.pieceId = this.route.snapshot.paramMap.get('id');
    this.getEquipements();
    this.getCategoriess();
    this.getInterventionsByPiece(this.pieceId)

    this.pieceService.getPieces().subscribe(data => {
      this.pieces = data;
      this.filteredPieces = data;
      if (this.pieceId) {
        this.isEditing = true;
        this.getPieceById(this.pieceId);
      }
    });
  }

  goBack(): void {
    this.location.back();
  }

  getEquipements() {
    this.equipmentService.getAllEquipment().subscribe({
      next: (res) => this.equipements = res,
      error: (err) => console.error(err)
    });
  }

  getCategoriess() {
    this.categorieService.getAllCategories().subscribe({
      next: (res) => this.categories = res,
      error: (err) => console.error(err)
    });
  }

  handleSubmit(event: Event): void {
    event.preventDefault();

    const res = new Piece();
    res.id = this.id;
    res.name = this.name;
    res.equipmentId = this.equipmentId;
    res.stockQuantity = this.stockQuantity;
    res.price = this.price;
    res.createdAt = new Date(this.createdAt);
    res.expiryDate = new Date(this.expiryDate);
    res.description = this.description;
    res.updatedAt = new Date(this.updatedAt);
    res.sku = this.sku;
    res.categorieId = this.categorieId;
    console.log(res)

    if (this.isEditing) {
      Swal.fire({
        title: 'Confirmation',
        text: `Voulez-vous vraiment modifier ${res.id} ?`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Oui, Modifier',
        cancelButtonText: 'Annuler'
      }).then((result) => {
        if (result.isConfirmed) {
          this.pieceService.updatePiece(res.id, res).subscribe({
            next: () => {
              console.log(res)
              Swal.fire('Modifiée!', 'La pièce a été modifiée avec succès.', 'success');
            },
            error: (err) => {
              Swal.fire('Erreur', err.error?.message || 'Erreur inconnue.', 'error');
            }
          });
        }
      });
    } else {
      Swal.fire({
        title: 'Confirmation',
        text: `Voulez-vous vraiment ajouter cette pièce ?`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Oui, Ajouter',
        cancelButtonText: 'Annuler'
      }).then((result) => {
        if (result.isConfirmed) {
          this.pieceService.createPiece(res).subscribe({
            next: () => {
              console.log(res)
              Swal.fire('Ajoutée!', 'La pièce a été ajoutée avec succès.', 'success');
            },
            error: (err) => {
              Swal.fire('Erreur', err.error?.message || 'Erreur inconnue.', 'error');
            }
          });
        }
      });
    }
  }

  getPieceById(id: string): void {
    this.pieceService.getPieceById(id).subscribe({
      next: (res) => {
        this.id = res.id;
        this.name = res.name;
        this.equipmentId = res.equipmentId;
        this.stockQuantity = res.stockQuantity;
        this.price = res.price;
        this.createdAt = this.formatDate(res.createdAt);
        this.expiryDate = this.formatDate(res.expiryDate);
        this.description = res.description;
        this.updatedAt = this.formatDate(res.updatedAt);
        this.sku = res.sku;
        this.categorieId = res.categorieId;
        console.log(res)
      },
      error: (err) => console.error(err)
    });
  }

  formatDate(dateString: string | Date): string {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = (`0${date.getMonth() + 1}`).slice(-2);
    const day = (`0${date.getDate()}`).slice(-2);
    return `${year}-${month}-${day}`;
  }

  getInterventionsByPiece(pieceId: any) {
    this.interventionService.getInterventionsByPieceId(pieceId).subscribe({
      next: (res) => {this.interventions=res},
      error:(err)=>err.error.message()
    })

  }
}
