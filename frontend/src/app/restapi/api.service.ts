import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormGroup } from '@angular/forms';
import { environment } from './../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  constructor(private http: HttpClient) {}

  getSupervisors() {
    return this.http.get<string[]>(`${environment.apiUrl}/supervisors`);
  }

  postForm(formGroup: FormGroup) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const body = JSON.stringify(formGroup.value);
    return this.http.post(`${environment.apiUrl}/submit`, body, { headers: headers, responseType: 'text' });
  }
}
