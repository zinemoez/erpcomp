import {Component, OnInit} from '@angular/core';
import {Router, RouterLink} from "@angular/router";
import {NgForOf} from "@angular/common";
import {Department} from "../models/department-model";
import {ApiService} from "../services/api.service";
import {DepartmentService} from "../services/department-service.service";
import {UserService} from "../services/user-service.service";
import {User} from "../models/user-model";

@Component({
  selector: 'app-department',
  standalone: true,
  imports: [
    RouterLink,
    NgForOf
  ],
  providers:[ApiService,DepartmentService],
  templateUrl: './department.component.html',
  styleUrl: './department.component.css'
})
export class DepartmentComponent implements OnInit{

  departments: Department[] = [];

  constructor(private apiService:ApiService,private router:Router, private departmentService:DepartmentService) {
  }
  ngOnInit(): void {
    this.getAllDepartments();
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
