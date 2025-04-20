import { Component, OnInit } from '@angular/core';
import { NgForOf, NgIf } from "@angular/common";
import { ActivatedRoute, RouterLink } from "@angular/router";
import Swal from "sweetalert2";
import { Piece } from "../models/piece-model";
import { PieceService } from "../services/piece-service.service";
import { Equipment } from "../models/equipment-model";
import { EquipmentService } from "../services/equipment-service.service";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { CategorieService } from "../services/categorie.service";
import { Category } from "../models/category";
import { Location } from '@angular/common';
declare var bootstrap: any;

@Component({
  selector: 'app-piece',
  standalone: true,
  imports: [
    NgIf,
    RouterLink,
    NgForOf,
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './piece.component.html',
  styleUrl: './piece.component.css'
})
export class PieceComponent implements OnInit {
  piece!: Piece;
  pieceId!: any;
  pieces: Piece[] = [];
  filteredPieces: Piece[] = [];
  isEditing: boolean = false;

  //// PAGINATION
  currentPage: number = 1;
  pageSize: number = 5; // nombre de pièces par page
  totalPages: number = 1;
  ///////////

  id!: string;
  name!: string;
  sku!: string;
  price!: number;
  stockQuantity!: number;
  description!: string;
  expiryDate!: Date;
  updatedAt: Date=new Date();
  createdAt!: Date;
  equipmentId!: Equipment;
  categorieId!:Category


  equipements: Equipment[] = [];
  categories: Category[] = [];
  searchTerm: string = '';

  constructor(
    private route: ActivatedRoute,
    private categorieService: CategorieService,
    private pieceService: PieceService,
    private equipmentService: EquipmentService,
    private location:Location
  ) {}

  ngOnInit(): void {
    this.pieceId = this.route.snapshot.paramMap.get('id');
    this.getEquipements();
    this.getCategoriess();
    this.pieceService.getPieces().subscribe(data => {
      this.pieces = data;
      this.filteredPieces = data;
      this.updatePagination();
      if (this.pieceId) {
        this.isEditing = true;
        this.getPieceById(this.pieceId);
      }
    });
  }




// Met à jour les données paginées
  getPaginatedPieces(): Piece[] {
    const start = (this.currentPage - 1) * this.pageSize;
    const end = start + this.pageSize;
    return this.filteredPieces.slice(start, end);
  }

// Mettre à jour la page
  setPage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
    }
  }

// Mettre à jour totalPages à chaque mise à jour du tableau
  updatePagination(): void {
    this.totalPages = Math.ceil(this.filteredPieces.length / this.pageSize);
    if (this.currentPage > this.totalPages) {
      this.currentPage = this.totalPages;
    }
  }
  //////////////////////////////////////


  getPiecesByEquipmentId(id: string) {
    this.pieceService.getPiecesByEquipmentId(id).subscribe({
      next: (data) => {
          this.pieces = data;
          this.filteredPieces = data;
          this.updatePagination();
      },
      error: (err) => console.error(err)
    });
  }

  getPiecesByCategorieId(id: string) {
    this.pieceService.getPiecesByCategorieId(id).subscribe({
      next: (data) => {
        this.pieces = data;
        this.filteredPieces = data;
        this.updatePagination();
      },
      error: (err) => console.error(err)
    });
  }

  getPieces() {
    this.pieceService.getPieces().subscribe({
      next: (data) => {
        this.pieces = data;
        this.filteredPieces = data;
        this.updatePagination();
      },
      error: (err) => console.error(err)
    });
  }

  getEquipements() {
    this.equipmentService.getAllEquipment().subscribe({
      next: (res) => this.equipements = res,
      error: (err) => console.error(err)
    });
  }

  getCategoriess() {
    this.categorieService.getAllCategories().subscribe({
      next: (res) => this.categories=res,
      error: (err) => console.error(err)
    });
  }
/////////////////////////////////////FILTRATION DES PIECES//////////////////////////////
  filterPieces(): void {
    const term = this.searchTerm.toLowerCase();
    this.filteredPieces = this.pieces.filter(piece =>
      Object.values(piece).some(val =>
        val?.toString().toLowerCase().includes(term)
      )
    );
    this.currentPage = 1; // reset to first page
    this.updatePagination();
  }
  /////////////////////////////////////////////////////////////////////////////////////////

  deletePiece(id: string) {
    Swal.fire({
      title: 'Confirmation',
      text: `Voulez-vous vraiment supprimer ${id} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui, supprimer',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        this.pieceService.deletePiece(id).subscribe({
          next: () => {
            Swal.fire('Supprimée!', `${id} a été supprimée avec succès.`, 'success');
            this.getPieces(); // Refresh list
          },
          error: (err) => {
            Swal.fire('Erreur', err.error?.message || 'Erreur inconnue.', 'error');
          }
        });
      }
    });
  }

  getPieceById(id: string): void {
    this.pieceService.getPieceById(id).subscribe({
      next: (res) => {
        this.id = res.id;
        this.name = res.name;
        this.equipmentId = res.equipmentId;
        this.stockQuantity = res.stockQuantity;
        this.price = res.price;
        this.createdAt = res.createdAt;
        this.expiryDate = res.expiryDate;
        this.description = res.description;
        this.updatedAt = res.updatedAt;
        this.sku = res.sku;
        this.categorieId=res.categorieId
      },
      error: (err) => console.error(err)
    });

  }

  goBack(): void {
    this.location.back();
  }

}
