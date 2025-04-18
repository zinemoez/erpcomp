import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiService} from "./api.service";
import {Observable} from "rxjs";
import {Piece} from "../models/piece-model";

@Injectable({
  providedIn: 'root'
})
export class PieceService {
  private apiUrl="http://localhost:5050/api/piece"

  constructor(private http:HttpClient,private apiService:ApiService) { }

  getPieceById(id:string):Observable<Piece>{
    return this.http.get<Piece>(`${this.apiUrl}/${id}`, {
      headers: this.apiService.getHeader(),
    })
  }
  getPieces():Observable<Piece[]>{
    return this.http.get<Piece[]>(`${this.apiUrl}/all`, {
      headers: this.apiService.getHeader(),
    })
  }

  updatePiece(id:string,piece:Piece):Observable<any>{
    return this.http.put<any>(`${this.apiUrl}/update/${id}`,piece, {
      headers: this.apiService.getHeader(),
    })
  }
  deletePiece(id:string):Observable<any>{
    return this.http.delete<any>(`${this.apiUrl}/delete/${id}`, {
      headers: this.apiService.getHeader(),
    })
  }
  createPiece(piece:Piece):Observable<any>{
    return this.http.post(`${this.apiUrl}/add`,piece, {
      headers: this.apiService.getHeader(),
    })
  }

  getPiecesByEquipmentId(id:string):Observable<any>{
    return this.http.get<Piece[]>(`${this.apiUrl}/equipment/${id}`, {
      headers: this.apiService.getHeader(),})}

  getPiecesByCategorieId(id:string):Observable<any>{
    return this.http.get<Piece[]>(`${this.apiUrl}/category/${id}`, {
      headers: this.apiService.getHeader(),})}

}
