import { Component } from '@angular/core';
import {Router, RouterLink} from "@angular/router";
import {ApiService} from "../services/api.service";
import {DepartmentComponent} from "../department/department.component";

@Component({
  selector: 'app-menu',
  standalone: true,
    imports: [
        RouterLink,
        DepartmentComponent,

    ],
  providers:[ApiService],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent {
  constructor(private apiService:ApiService, private route:Router) {
  }

  logout() {
    this.apiService.logout();
    this.route.navigate(["/login"])

  }
}
