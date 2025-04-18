import {EventEmitter, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import * as CryptoJS from 'crypto-js';
import {User} from "../models/user-model";

@Injectable({
  providedIn: 'root',

})
export class ApiService {

  authStatuschanged = new EventEmitter<void>();
  private static BASE_URL = 'http://localhost:5050/api';
  private static ENCRYPTION_KEY = "phegon-dev-inventory";

  constructor(private http: HttpClient) {}

  // Encrypt data and save to localStorage
  encryptAndSaveToStorage(key: string, value: string): void {
    const encryptedValue = CryptoJS.AES.encrypt(value, ApiService.ENCRYPTION_KEY).toString();
    localStorage.setItem(key, encryptedValue);
  }

  // Retreive from localStorage and Decrypt
  getFromStorageAndDecrypt(key: string): any {
    try {
      const encryptedValue = localStorage.getItem(key);
      if (!encryptedValue) return null;
      return CryptoJS.AES.decrypt(encryptedValue, ApiService.ENCRYPTION_KEY).toString(CryptoJS.enc.Utf8);
    } catch (error) {
      return null;
    }
  }

  clearAuth() {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
  }


  getHeader(): HttpHeaders {
    const token = this.getFromStorageAndDecrypt("token");
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }


  /***AUTH & USERS API METHODS */

  registerUser(body: any): Observable<any> {
    return this.http.post(`${ApiService.BASE_URL}/auth/register`,body);
  }

  loginUser(body: any): Observable<any> {
    return this.http.post(`${ApiService.BASE_URL}/auth/login`, body);
  }

  getLoggedInUserInfo(): Observable<User> {
    return this.http.get<User>(`${ApiService.BASE_URL}/users/current`, {
      headers: this.getHeader(),
    });
  }
  logout():void{
    this.clearAuth()
  }

  isAuthenticated():boolean{
    const token = this.getFromStorageAndDecrypt("token");
    return !!token;
  }

  isAdmin():boolean {
    const role = this.getFromStorageAndDecrypt("role");
    return role === "ADMIN";
  }


  addUser(formData: FormData) {

  }

  updateUser(formData: FormData) {

  }
}
