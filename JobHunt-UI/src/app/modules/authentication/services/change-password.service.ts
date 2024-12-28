import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../../authentication/services/authentication.service'; // Import AuthenticationService

@Injectable({
  providedIn: 'root'
})
export class ChangePasswordService {

  private apiUrl = 'http://localhost:8888/api/v1/userservice/change-password';

  constructor(
    private http: HttpClient,
    private authService: AuthenticationService // Inject AuthenticationService
  ) {}

  changePassword(oldPassword: string, newPassword: string): Observable<any> {
    const body = { oldPassword, newPassword };

    // Use AuthenticationService to get the token
    const token = this.authService.getToken();

    // Add authorization header with token
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`, // Use the token from AuthenticationService
      'Content-Type': 'application/json'
    });

    return this.http.post(this.apiUrl, body, { headers });
  }
}
