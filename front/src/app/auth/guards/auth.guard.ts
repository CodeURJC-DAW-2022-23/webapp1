import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateChild,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

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
  ): boolean {
    const isLoggedInUser: boolean = this.authService.isLoggedIn();
    if (isLoggedInUser) {
      const pageRole: string = route.data['role'];
      return this._redirectLoggedUser(pageRole, url);
    }
    if (this._isProfilePage(url)) this.router.navigate(['/sign-in']);
    else this.router.navigate(['/']);
    return false;
  }

  private _redirectLoggedUser(pageRole: string, url: string): boolean {
    const isAdminPage: boolean = pageRole === 'admin';
    if (isAdminPage) {
      const isAdminUser: boolean = this.authService.loggedUser!.admin;
      if (isAdminUser) return true;
      this.router.navigate(['/']);
      return false;
    }
    if (this._isProfilePage(url)) {
      const loggedUserUsername: string = this.authService.loggedUser!.username;
      this.router.navigate(['/user/' + loggedUserUsername]);
    }
    return true;
  }

  private _isProfilePage(url: string): boolean {
    return url.startsWith('/profile');
  }
}
