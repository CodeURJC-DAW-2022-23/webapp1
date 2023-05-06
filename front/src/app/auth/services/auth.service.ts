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
  loggedUser: LoggedUser | undefined;
  logged: boolean = false;

  constructor(private http: HttpClient) {
    // TODO: delete this & add const assing to getLoggedInUserSubscription
    this._getLoggedInUser();
  }

  register(registerForm: RegisterForm) {
    const registerRequest: registerRequest = {
      username: registerForm.username,
      email: registerForm.email,
      password: registerForm.passwords.password,
    };
    return this.http.post(BASE_URL + '/register', registerRequest, {
      withCredentials: true,
    });
  }

  login(signInForm: SignInForm): Observable<HttpResponse<any>> {
    return this.http
      .post(BASE_URL + '/sign-in', signInForm, {
        withCredentials: true,
        observe: 'response',
      })
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
    this.http
      .post(BASE_URL + '/sign-out', { withCredentials: true })
      .subscribe(_ => {
        localStorage.removeItem('token');
        this.loggedUser = undefined;
        this.logged = false;
      });
  }

  isLoggedIn(): boolean {
    return this.logged;
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  private _getLoggedInUser() {
    this.http
      .get<LoggedUser>(BASE_URL + '/logged-user', { withCredentials: true })
      .subscribe({
        next: (loggedUser: LoggedUser) => {
          this.loggedUser = loggedUser;
          this.logged = true;
        },
        error: _ => {
          this.loggedUser = undefined;
          this.logged = false;
        },
      });
  }

  getLoggedInUserSubscription() {
    return this.http.get<LoggedUser>(BASE_URL + '/logged-user', {
      withCredentials: true,
    });
  }

  private _saveToken(token: string | null) {
    if (token) localStorage.setItem('token', token);
  }
}
