import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Intervention} from '../models/intervention-model';
import {ApiService} from "./api.service";  // Response model if needed

@Injectable({
  providedIn: 'root'
})
export class InterventionService {
  private apiUrl = 'http://localhost:5050/api/inter'; // Change if needed

  constructor(private http: HttpClient, private apiService:ApiService) {}

  // Get all interventions
  getAllInterventions(): Observable<Intervention[]> {
    return this.http.get<Intervention[]>(`${this.apiUrl}/all`, {
      headers: this.apiService.getHeader(),
    });
  }

  // Get intervention by ID
  getInterventionById(id: number): Observable<Intervention> {
    return this.http.get<Intervention>(`${this.apiUrl}/${id}`, {
      headers: this.apiService.getHeader(),
    });
  }

  // Get interventions by equipment ID
  getInterventionsByEquipmentId(id: string): Observable<Intervention[]> {
    return this.http.get<Intervention[]>(`${this.apiUrl}/equi/${id}`, {
      headers: this.apiService.getHeader(),
    });
  }

  // Get interventions created by a specific user (creator)
  getInterventionsByCreatedId(id: number): Observable<Intervention[]> {
    return this.http.get<Intervention[]>(`${this.apiUrl}/createdBy/${id}`, {
      headers: this.apiService.getHeader(),
    });
  }

  // Get interventions approved by a specific user
  getInterventionsByApprovedId(id: number): Observable<Intervention[]> {
    return this.http.get<Intervention[]>(`${this.apiUrl}/approuvedBy/${id}`, {
      headers: this.apiService.getHeader(),
    });
  }

  // Add new intervention (Only for Admins)
  addIntervention(intervention: Intervention): Observable<Intervention> {
    return this.http.post<Intervention>(`${this.apiUrl}/add`, intervention, {
      headers: this.apiService.getHeader(),
    });
  }

  // Update intervention (Only for Admins)
  updateIntervention(id: number, intervention: Intervention): Observable<Intervention> {
    return this.http.put<Intervention>(`${this.apiUrl}/update/${id}`, intervention, {
      headers: this.apiService.getHeader(),
    });
  }

  // Delete intervention (Only for Admins)
  deleteIntervention(id: number): Observable<Intervention> {
    return this.http.delete<Intervention>(`${this.apiUrl}/delete/${id}`, {
      headers: this.apiService.getHeader(),
    });
  }
  getInterventionsByPieceId(id: string): Observable<Intervention[]> {
    return this.http.get<Intervention[]>(`${this.apiUrl}/piece/${id}`, {
      headers: this.apiService.getHeader(),
    });
  }


}
