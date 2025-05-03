import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {ParameterType} from "../models/parameterType";


@Injectable({
  providedIn: 'root'
})
export class ParameterTypeService {
  private apiUrl = 'http://localhost:8080/api/parameterTypes'; // à adapter

  constructor(private http: HttpClient) {}

  getAll(): Observable<ParameterType[]> {
    return this.http.get<ParameterType[]>(`${this.apiUrl}/all`);
  }

  getById(id: number): Observable<ParameterType> {
    return this.http.get<ParameterType>(`${this.apiUrl}/${id}`);
  }

  getByEquipmentId(id: string): Observable<ParameterType[]> {
    return this.http.get<ParameterType[]>(`${this.apiUrl}/equipment/${id}`);
  }
  getByDepartmentId(id: string): Observable<ParameterType[]> {
    return this.http.get<ParameterType[]>(`${this.apiUrl}/by-department/${id}`);
  }

  create(data: ParameterType): Observable<ParameterType> {
    return this.http.post<ParameterType>(`${this.apiUrl}`, data);
  }

  update(id: number, data: ParameterType): Observable<ParameterType> {
    return this.http.put<ParameterType>(`${this.apiUrl}/${id}`, data);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}

