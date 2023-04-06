import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { SignInForm } from '../interfaces/forms.interface';
import { Observable, map } from 'rxjs';

const BASE_URL: string = '/api/auth';

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor(private http: HttpClient) {}

  login(signInForm: SignInForm): Observable<HttpResponse<any>> {
    return this.http
      .post(BASE_URL + '/sign-in', signInForm, { observe: 'response' })
      .pipe(
        map((response: HttpResponse<any>) => {
          const headers: HttpHeaders = response.headers;
          const token: string | null = headers.get('authorization');
          this.saveToken(token);
          return response;
        })
      );
  }

  logout(): Observable<Object> {
    localStorage.removeItem('token');
    return this.http.post(BASE_URL + '/sign-out', null);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  private saveToken(token: string | null) {
    if (token) localStorage.setItem('token', token);
  }
}
