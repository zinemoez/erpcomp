import {Component, OnInit} from '@angular/core';
import {DatePipe, NgIf} from "@angular/common";
import {ActivatedRoute, RouterLink} from "@angular/router";
import {User} from "../models/user-model";
import {UserService} from "../services/user-service.service";
import {DepartmentService} from "../services/department-service.service";
import {EquipmentService} from "../services/equipment-service.service";
import Swal from "sweetalert2";

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [
    NgIf,
    DatePipe,
    RouterLink
  ],
  templateUrl: './users.component.html',
  styleUrl: './users.component.css'
})
export class UsersComponent  implements OnInit{
  user!:User
  id!: number;
  constructor(private route: ActivatedRoute,private userService:UserService) {}
  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.getUsers(this.id);
  }

   getUsers(id:number){
    this.userService.getUserById(id).subscribe({
      next:(res)=>this.user=res,
      error:(err)=>err.error.message()
    })
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
      }
    });
  }
}
