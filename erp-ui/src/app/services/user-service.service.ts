import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiService} from "./api.service";
import {Observable} from "rxjs";
import {Equipment} from "../models/equipment-model";
import {User} from "../models/user-model";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl="http://localhost:5050/api/users"

  constructor(private http:HttpClient,private apiService:ApiService) { }

  getUserById(id:number):Observable<User>{
    return this.http.get<User>(`${this.apiUrl}/${id}`, {
      headers: this.apiService.getHeader(),
    })
  }


  getAllUsers():Observable<User[]>{
    return this.http.get<User[]>(`${this.apiUrl}/all`, {
      headers: this.apiService.getHeader(),
    })
  }

  updateUser(id:number,body:any):Observable<any>{
    return this.http.put<any>(`${this.apiUrl}/update/${id}`,body, {
      headers: this.apiService.getHeader(),
    })
  }
  deleteUser(id:number):Observable<any>{
    return this.http.delete<any>(`${this.apiUrl}/delete/${id}`, {
      headers: this.apiService.getHeader(),
    })
  }
  createUser(body:any):Observable<any>{
    return this.http.post(`${this.apiUrl}/add`,body)
  }
  currentUser():Observable<any>{
    return this.http.get<User>(`${this.apiUrl}/current`, {
      headers: this.apiService.getHeader(),
    })
  }
  getUsersByDepartmentId(id:string):Observable<any>{
    return this.http.get<User[]>(`${this.apiUrl}/equipment/${id}`, {
      headers: this.apiService.getHeader(),})}

}
