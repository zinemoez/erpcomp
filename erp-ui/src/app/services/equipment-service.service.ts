import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Equipment} from '../models/equipment-model';
import {ApiService} from "./api.service";


@Injectable({
  providedIn: 'root'
})
export class EquipmentService {
  private apiUrl="http://localhost:5050/api/equi"

  constructor(private http:HttpClient, private apiService:ApiService) { }

  getAllEquipment():Observable<Equipment[]>{
    return this.http.get<Equipment[]>(`${this.apiUrl}/all`, {
      headers: this.apiService.getHeader(),
    })
  }
  getEquipmentById(id:String):Observable<Equipment>{
    return this.http.get<Equipment>(`${this.apiUrl}/${id}`, {
      headers: this.apiService.getHeader(),
    })
  }
  createEquipment(equipment:Equipment):Observable<any>{
    return this.http.post<any>(`${this.apiUrl}/add`,equipment, {
      headers: this.apiService.getHeader(),
    })
  }
  updateEquipment(id:string,equipment:Equipment):Observable<any>{
    return this.http.put<any>(`${this.apiUrl}/update/${id}`,equipment, {
      headers: this.apiService.getHeader(),
    })
  }
  deleteEquipment(id:string):Observable<any>{
    return this.http.delete<any>(`${this.apiUrl}/delete/${id}`, {
      headers: this.apiService.getHeader(),
    })
  }

  getEquipmentByDepartmentId(id: string): Observable<Equipment[]> {
    return this.http.get<Equipment[]>(`${this.apiUrl}/dep/${id}`, {
      headers: this.apiService.getHeader(),
    });
  }
}
