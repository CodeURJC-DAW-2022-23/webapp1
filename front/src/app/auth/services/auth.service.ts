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
    this.loggedUser = undefined;
    this.http.post(BASE_URL + '/sign-out', null);
  }

  isLoggedIn(): boolean {
    console.log('inside', this.loggedUser);
    return this.loggedUser !== undefined;
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  private _getLoggedInUser() {
    this.http
      .get<LoggedUser>(BASE_URL + '/logged-user')
      .subscribe(loggedUser => {
        console.log('api', loggedUser);
        this.loggedUser = loggedUser;
      });
  }

  private _saveToken(token: string | null) {
    if (token) localStorage.setItem('token', token);
  }
}
