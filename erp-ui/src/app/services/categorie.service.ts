import { Injectable } from '@angular/core';
import {ApiService} from "./api.service";
import {Observable} from "rxjs";
import {Category} from "../models/category";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CategorieService {

  private apiUrl = "http://localhost:5050/api/categories"

  constructor(private http: HttpClient, private apiService: ApiService) {
  }

  //GET ALL DEPARTMENTS
  getAllCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.apiUrl}/all`, {
      headers: this.apiService.getHeader(),
    });
  }

  //Create Department
  createCategory(Category: Category): Observable<any> {
    return this.http.post(`${this.apiUrl}/add`, Category, {
      headers: this.apiService.getHeader(),
    });
  }

  //Update department
  updateCategory(id: string, dCategory: Category): Observable<Category> {
    return this.http.put<any>(`${this.apiUrl}/update/${id}`, Category, {
      headers: this.apiService.getHeader(),
    });
  }

  //delete department
  deleteCategory(id: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/delete/${id}`, {
      headers: this.apiService.getHeader(),
    })
  }

  getCategoryById(id: string): Observable<Category> {
    return this.http.get<Category>(`${this.apiUrl}/${id}`, {
      headers: this.apiService.getHeader(),
    })
  }

}
