import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {DailyParameter} from "../models/dailyParameter";
import {ApiService} from "./api.service";


@Injectable({
  providedIn: 'root'
})
export class DailyParameterService {
  private apiUrl = 'http://localhost:5050/api/dailyParameters'; // à adapter

  constructor(private http: HttpClient,private apiService:ApiService) {}

  getAll(): Observable<DailyParameter[]> {
    return this.http.get<DailyParameter[]>(`${this.apiUrl}/all`, {
      headers: this.apiService.getHeader(),
    });
  }
  getByDepartementId(departementId: string): Observable<DailyParameter[]> {
    return this.http.get<DailyParameter[]>(`${this.apiUrl}/department/${departementId}`, {
      headers: this.apiService.getHeader(),
    });
  }

  getById(id: number): Observable<DailyParameter> {
    return this.http.get<DailyParameter>(`${this.apiUrl}/${id}`, {
      headers: this.apiService.getHeader(),
    });
  }

  getByEquipmentId(id: string): Observable<DailyParameter[]> {
    return this.http.get<DailyParameter[]>(`${this.apiUrl}/equipment/${id}`, {
      headers: this.apiService.getHeader(),
    });
  }

  getByDate(date: string): Observable<DailyParameter[]> {
    return this.http.get<DailyParameter[]>(`${this.apiUrl}/date/${date}`, {
      headers: this.apiService.getHeader(),
    });
  }

  getByEquipmentIdAndDate(equipmentId: string, date: string): Observable<DailyParameter[]> {
    return this.http.get<DailyParameter[]>(`${this.apiUrl}/equipment/${equipmentId}/date/${date}`);
  }

  create(data: DailyParameter): Observable<DailyParameter> {
    return this.http.post<DailyParameter>(`${this.apiUrl}`, data);
  }

  update(id: number, data: DailyParameter): Observable<DailyParameter> {
    return this.http.put<DailyParameter>(`${this.apiUrl}/${id}`, data, {
      headers: this.apiService.getHeader(),
    });
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, {
      headers: this.apiService.getHeader(),
    });
  }

  getByParameterTypeId(id: number):Observable<DailyParameter[]> {
    return this.http.get<DailyParameter[]>(`${this.apiUrl}/parameter-type/${id}`, {
      headers: this.apiService.getHeader(),
    });
  }
}
