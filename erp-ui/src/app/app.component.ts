import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {BrowserModule} from "@angular/platform-browser";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {CommonModule, NgIf} from "@angular/common";
import {MenuComponent} from "./menu/menu.component";
import {ApiService} from "./services/api.service";
import {DepartmentComponent} from "./department/department.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, MenuComponent, NgIf, DepartmentComponent],
  providers:[ApiService],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'erp-ui';
constructor(private apiService: ApiService,) {
}

  isAuth():boolean{
    return this.apiService.isAuthenticated();
    }


}
