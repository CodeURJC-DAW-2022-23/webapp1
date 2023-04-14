import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/services/auth.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {
  user: any;
  notGuest: boolean = false;
  ownProfile: boolean = false;

  constructor(
    private router: Router,
    private authService: AuthService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    const username: string = this._getUsernameFormURL(this.router.url);
    this._checkLoggedUser(username);
    this.userService.getUser(username).subscribe({
      next: user => (this.user = user),
      error: _ => this.router.navigate(['/error']),
    });
  }

  private _getUsernameFormURL(url: string): string {
    return url.split('/')[2];
  }

  private _checkLoggedUser(username: string) {
    if (!this.authService.isLoggedIn()) return;
    this.notGuest = true;
    const loggedUser = this.authService.loggedUser;
    if (loggedUser!.username === username) this.ownProfile = true;
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
