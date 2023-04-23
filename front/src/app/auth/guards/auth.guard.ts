import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateChild,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable, catchError, map, of } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { LoggedUser } from '../interfaces/loggedUser.interface';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate, CanActivateChild {
  constructor(private router: Router, private authService: AuthService) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    const url: string = state.url;
    return this._checkLoggedUserPerms(route, url);
  }

  canActivateChild(
    childRoute: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    return this.canActivate(childRoute, state);
  }

  private _checkLoggedUserPerms(
    route: ActivatedRouteSnapshot,
    url: string
  ): Observable<boolean> {
    return this.authService.getLoggedInUserSubscription().pipe(
      map((loggedUser: LoggedUser) => {
        const pageRole: string = this._getPageRole(route);
        return this._redirectLoggedUser(loggedUser, pageRole, url);
      }),
      catchError(_ => {
        if (this._getPageRole(route) === 'guest') return of(true);
        if (this._isProfilePage(url)) this.router.navigate(['/sign-in']);
        else this.router.navigate(['/']);
        return of(false);
      })
    );
  }

  private _getPageRole(route: ActivatedRouteSnapshot): string {
    return route.data['role'];
  }

  private _redirectLoggedUser(
    loggedUser: LoggedUser,
    pageRole: string,
    url: string
  ): boolean {
    const isAdminPage: boolean = pageRole === 'admin';
    if (isAdminPage) {
      if (loggedUser.admin) return true;
      this.router.navigate(['/']);
      return false;
    }
    if (this._isProfilePage(url)) {
      this.router.navigate(['/user/' + loggedUser.username]);
    }
    return true;
  }

  private _isProfilePage(url: string): boolean {
    return url === '/profile';
  }
}
