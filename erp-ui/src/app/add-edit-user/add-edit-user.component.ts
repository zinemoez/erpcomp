import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Poste } from '../models/poste-model';
import { UserRole } from '../models/userRole-model';
import { User } from '../models/user-model';
import { Department } from '../models/department-model';
import { DepartmentService } from '../services/department-service.service';
import { UserService } from '../services/user-service.service';
import {FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';
import { DatePipe, NgForOf, NgIf } from '@angular/common';
import Swal from "sweetalert2";
import { Location } from '@angular/common';

@Component({
  selector: 'app-add-edit-user',
  standalone: true,
  imports: [FormsModule, NgForOf, NgIf, RouterLink, DatePipe, ReactiveFormsModule],
  templateUrl: './add-edit-user.component.html',
  styleUrls: ['./add-edit-user.component.css']
})
export class AddEditUserComponent implements OnInit {
  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    protected router: Router,
    private departmentService: DepartmentService,
    private location: Location
  ) {}

  userId!: number;
  isEditing: boolean = false;
  user!: User
  postes: Poste[] = [];
  roles: UserRole[] = [];
  departments!: Department[];
  message: string = '';

  // Form fields
  id!:any;
  name: string = '';
  surname: string = '';
  password: string = '';
  email: string = '';
  naissance!: Date ;
  cin!:any;
  departmentId!:any;
  poste!:any;
  categorie: string = '';
  phoneNumber: string = '';
  role!:any;
  adresse!:string;

  ngOnInit(): void {
    this.userId = Number(this.route.snapshot.paramMap.get('id'));
    this.postes = this.getPostes();
    console.log(this.roles)
    this.roles = this.getRoles();
    this.getDepartments();
    if (this.userId) {
      this.isEditing = true;
      this.getUserById(this.userId);
    }
  }

  getPostes(): Poste[] {
    return Object.values(Poste);
  }

  goBack(): void {
    this.location.back();
  }

  getRoles(): UserRole[] {
    return Object.values(UserRole);
  }

  getDepartments(): void {
    this.departmentService.getAllDepartments().subscribe({
      next: (res) => (this.departments = res),
      error: (err) => console.error(err.error.message),
    });
  }

  getUserById(userId: number): void {
    this.userService.getUserById(userId).subscribe({
      next: (res) => {
        this.user = res;
        // Populate form fields from existing user data
        this.id = res.id;
        this.name = res.name;
        this.surname = res.surname;
        this.password = res.password;
        this.email = res.email;
        this.naissance = res.naissance;
        this.cin = res.cin;
        this.departmentId = res.departmentId;
        this.poste = res.poste;
        this.categorie = res.categorie;
        this.phoneNumber = res.phoneNumber;
        this.role = res.role;
        this.adresse=res.adresse
      },
      error: (err) => console.error(err),
    });
  }

  handleSubmit(event: Event): void {
    event.preventDefault();
    // Create user object
    const user = new User()
    user.id = this.id
    user.name = this.name
    user.surname = this.surname
    user.cin = this.cin
    user.departmentId = this.departmentId
    user.email = this.email
    user.categorie = this.categorie
    user.role = this.role
    user.poste = this.poste
    user.phoneNumber=this.phoneNumber
    user.password=this.password
    user.adresse=this.adresse
    user.naissance=this.naissance


    // Decide whether to create or update
    if (this.isEditing) {

      Swal.fire({
        title: 'Confirmation',
        text: `Voulez-vous vraiment modifier  ${user.id} ?`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Oui, Modifier',
        cancelButtonText: 'Annuler'
      }).then((result) => {
        if (result.isConfirmed) {
          this.userService.updateUser(this.userId, user).subscribe({
            next: () => {
              Swal.fire('Modifier!', 'Le personnel a été modifié avec succès.', 'success');
            },
            error: (err) => {
              Swal.fire('Erreur', err.error?.message || 'Erreur inconnue lors de la suppression.', 'error');
            }
          });
        }
      });
      console.log(user)

      // Add this
    } else {
      Swal.fire({
        title: 'Confirmation',
        text: `Voulez-vous vraiment crier  ${user.id} ?`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Oui, Ajouter',
        cancelButtonText: 'Annuler'
      }).then((result) => {
        if (result.isConfirmed) {
          this.userService.createUser(user).subscribe({
            next: () => {
              Swal.fire('Ajouter!', 'Le personnel a été ajouté avec succès.', 'success');
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
}
