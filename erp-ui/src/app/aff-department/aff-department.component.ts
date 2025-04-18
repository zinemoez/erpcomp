import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, RouterLink} from "@angular/router";
import {DepartmentService} from "../services/department-service.service";
import {Department} from "../models/department-model";
import {DatePipe, JsonPipe, NgForOf, NgIf} from "@angular/common";
import {EquipmentService} from "../services/equipment-service.service";
import Swal from 'sweetalert2';
import {UserService} from "../services/user-service.service";
import {User} from "../models/user-model";
import {FormsModule} from "@angular/forms";
import {Equipment} from "../models/equipment-model";

@Component({
  selector: 'app-aff-department',
  standalone: true,
  imports: [
    NgForOf,
    DatePipe,
    NgIf,
    JsonPipe,
    RouterLink,
    FormsModule
  ],
  providers:[DepartmentService],
  templateUrl: './aff-department.component.html',
  styleUrl: './aff-department.component.css'
})
export class AffDepartmentComponent implements OnInit{
  department!: Department;
  users!:User[];
  equipments!:Equipment[]
  id!: string;
  departments: any[] = [];
  user!:User
  constructor(private route: ActivatedRoute,private userService:UserService, private departmentService: DepartmentService, private equipmentService:EquipmentService) {
    this.id = this.route.snapshot.paramMap.get('id')!;
  }
  ngOnInit(): void {
    this.getDepartment(this.id);
    this.getUsersByDepartmentId(this.id)
    this.getEquipmentsByDepartmentId(this.id)
  }

  getEquipmentsByDepartmentId(id:string){
    this.equipmentService.getEquipmentByDepartmentId(id).subscribe({
      next:(res)=>this.equipments=res,
      error:(err)=>err.error.message()
    })
  }
  getDepartment(id:any) {
    this.departmentService.getDepartmentById(id).subscribe({
      next: (res) => {
        console.log('Réponse backend reçue :', res);
        this.department = res;
      },
      error: (err) => {
        console.error('Erreur de récupération du département :', err);
      }
    });
  }
  search() {

  }
  getUsersByDepartmentId(id:string){
    this.userService.getUsersByDepartmentId(id).subscribe({
      next:(res)=>this.users=res,
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
        this.getEquipmentsByDepartmentId(this.id)
      }
    });
  }

  supprimerersonnel(id: number) {
    Swal.fire({
      title: 'Confirmation',
      text: `Voulez-vous vraiment supprimer  ${id} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui, supprimer',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        this.userService.deleteUser(id).subscribe({
            next: () => {
              Swal.fire('Supprimé!', `${id} a été supprimé avec succès.`, 'success');
            },
          error: (err) => {
            Swal.fire('Erreur', err.error?.message || 'Erreur inconnue lors de la suppression.', 'error');
          }

        });
        this.getUsersByDepartmentId(this.id)
      }
    });
  }

  getAllDepartments() {
    this.departmentService.getAllDepartments().subscribe({
      next: (res) => {
        this.departments = res;
      },
      error: (err) => {
        console.error("Error getting departments:", err.message);
      }
    });
  }
}
