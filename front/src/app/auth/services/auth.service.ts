import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { RegisterForm, SignInForm } from '../interfaces/forms.interface';
import { LoggedUser } from '../interfaces/loggedUser.interface';

const BASE_URL: string = '/api/auth';

interface registerRequest {
  username: string;
  email: string;
  password: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private _loggedUser: LoggedUser | undefined;

  get loggedUser(): LoggedUser | undefined {
    if (!this._loggedUser) return this._getLoggedInUser();
    return this._loggedUser;
  }

  constructor(private http: HttpClient) {}

  register(registerForm: RegisterForm) {
    const registerRequest: registerRequest = {
      username: registerForm.username,
      email: registerForm.email,
      password: registerForm.passwords.password,
    };
    return this.http.post(BASE_URL + '/register', registerRequest);
  }

  login(signInForm: SignInForm): Observable<HttpResponse<any>> {
    return this.http
      .post(BASE_URL + '/sign-in', signInForm, { observe: 'response' })
      .pipe(
        map((response: HttpResponse<any>) => {
          this._getLoggedInUser();
          const headers: HttpHeaders = response.headers;
          const token: string | null = headers.get('authorization');
          this._saveToken(token);
          return response;
        })
      );
  }

  logout() {
    localStorage.removeItem('token');
    this._loggedUser = undefined;
    this.http.post(BASE_URL + '/sign-out', null);
  }

  isLoggedIn(): boolean {
    return this.loggedUser !== undefined;
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  private _getLoggedInUser(): LoggedUser | undefined {
    this.http
      .get<LoggedUser>(BASE_URL + '/logged-user')
      .subscribe(loggedUser => {
        this._loggedUser = loggedUser;
        return loggedUser;
      });
    return;
  }

  private _saveToken(token: string | null) {
    if (token) localStorage.setItem('token', token);
  }
}
