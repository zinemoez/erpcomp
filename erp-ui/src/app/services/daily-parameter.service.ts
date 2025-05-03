import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {DailyParameter} from "../models/dailyParameter";


@Injectable({
  providedIn: 'root'
})
export class DailyParameterService {
  private apiUrl = 'http://localhost:5050/api/dailyParameters'; // à adapter

  constructor(private http: HttpClient) {}

  getAll(): Observable<DailyParameter[]> {
    return this.http.get<DailyParameter[]>(`${this.apiUrl}/all`);
  }
  getByDepartementId(departementId: string): Observable<DailyParameter[]> {
    return this.http.get<DailyParameter[]>(`${this.apiUrl}/department/${departementId}`);
  }

  getById(id: number): Observable<DailyParameter> {
    return this.http.get<DailyParameter>(`${this.apiUrl}/${id}`);
  }

  getByEquipmentId(id: string): Observable<DailyParameter[]> {
    return this.http.get<DailyParameter[]>(`${this.apiUrl}/equipment/${id}`);
  }

  getByDate(date: string): Observable<DailyParameter[]> {
    return this.http.get<DailyParameter[]>(`${this.apiUrl}/date/${date}`);
  }

  getByEquipmentIdAndDate(equipmentId: string, date: string): Observable<DailyParameter[]> {
    return this.http.get<DailyParameter[]>(`${this.apiUrl}/equipment/${equipmentId}/date/${date}`);
  }

  create(data: DailyParameter): Observable<DailyParameter> {
    return this.http.post<DailyParameter>(`${this.apiUrl}`, data);
  }

  update(id: number, data: DailyParameter): Observable<DailyParameter> {
    return this.http.put<DailyParameter>(`${this.apiUrl}/${id}`, data);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
