import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Department} from '../models/department-model';
import {ApiService} from "./api.service";


@Injectable({
  providedIn: 'root'
})
export class DepartmentService {
private apiUrl="http://localhost:5050/api/dep"
  constructor(private http:HttpClient, private apiService:ApiService) { }

  //GET ALL DEPARTMENTS
  getAllDepartments():Observable<Department[]> {
    return this.http.get<Department[]>(`${this.apiUrl}/all`);
  }

  //Create Department
  createDepartment(department:Department):Observable<any>{
  return this.http.post(`${this.apiUrl}/add`,department,{
    headers: this.apiService.getHeader(),
  });
  }

  //Update department
  updateDepartment(id:string, department:Department):Observable<Department>{
  return this.http.put<any>(`${this.apiUrl}/update/${id}`,department, {
    headers: this.apiService.getHeader(),
  });
  }
  //delete department
  deleteDepartment(id:string):Observable<any>{
  return this.http.delete(`${this.apiUrl}/delete/${id}`, {
    headers: this.apiService.getHeader(),
  })
  }

  getDepartmentById(id:string):Observable<Department>{
    return this.http.get<Department>(`${this.apiUrl}/${id}`, {
      headers: this.apiService.getHeader(),
    })
  }



}
