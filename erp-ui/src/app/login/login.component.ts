import {Component, EventEmitter} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Router, RouterLink, RouterLinkActive} from '@angular/router';
import {ApiService} from "../services/api.service";
import {firstValueFrom, Observable} from "rxjs";
import {CommonModule, NgIf} from "@angular/common";
import {HttpClient, HttpClientModule, HttpHeaders} from "@angular/common/http";
import CryptoJS from "crypto-js";
import {User} from "../models/user-model";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    RouterLink,
    RouterLinkActive,
    NgIf,
      CommonModule,
    HttpClientModule
  ],
  providers:[ApiService],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  constructor( private router:Router,private http: HttpClient, private apiService:ApiService){}

  formData: any = {
    email: '',
    password: ''
  };

  message:string | null = null;

  async handleSubmit(){
    if(
        !this.formData.email ||
        !this.formData.password
    ){
      this.showMessage("All fields are required");
      return;
    }
    try {
      const response: any = await firstValueFrom(
          this.apiService.loginUser(this.formData)
      );
      if (response.status === 200) {
        this.apiService.encryptAndSaveToStorage('token', response.token);
        this.apiService.encryptAndSaveToStorage('role', response.role);
        await this.router.navigate(["/dashboard"]);
      }
    } catch (error:any) {
      console.log(error)
      this.showMessage(error?.error?.message || error?.message || "Unable to Login a user" + error)
    }
  }

  showMessage(message:string){
    this.message = message;
    setTimeout(() =>{
      this.message = null
    }, 4000)
  }


}
