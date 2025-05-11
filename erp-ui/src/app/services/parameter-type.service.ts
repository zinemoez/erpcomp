import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {ParameterType} from "../models/parameterType";
import {ApiService} from "./api.service";


@Injectable({
  providedIn: 'root'
})
export class ParameterTypeService {
  private apiUrl = 'http://localhost:5050/api/parameterTypes'; // à adapter

  constructor(private http: HttpClient, private apiService: ApiService) {
  }

  getAll(): Observable<ParameterType[]> {
    return this.http.get<ParameterType[]>(`${this.apiUrl}/all`, {
      headers: this.apiService.getHeader(),
    });
  }

  getById(id: number): Observable<ParameterType> {
    return this.http.get<ParameterType>(`${this.apiUrl}/${id}`, {
      headers: this.apiService.getHeader(),
    });
  }

  getByEquipmentId(id: string): Observable<ParameterType[]> {
    return this.http.get<ParameterType[]>(`${this.apiUrl}/equipment/${id}`, {
      headers: this.apiService.getHeader(),
    });
  }


  getByDepartmentId(id: string): Observable<ParameterType[]> {
    return this.http.get<ParameterType[]>(`${this.apiUrl}/by-department/${id}`, {
      headers: this.apiService.getHeader(),
    });
  }


  create(data: ParameterType): Observable<ParameterType> {
    return this.http.post<ParameterType>(`${this.apiUrl}/add`, data, {
      headers: this.apiService.getHeader(),
    });
  }

  update(id: number, data: ParameterType): Observable<ParameterType> {
    return this.http.put<ParameterType>(`${this.apiUrl}/${id}`, data, {
      headers: this.apiService.getHeader(),
    });
  }


  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, {
      headers: this.apiService.getHeader(),
    });
  }
}
