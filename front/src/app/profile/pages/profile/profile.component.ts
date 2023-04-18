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
  ownProfile: boolean | undefined;
  followed: boolean = false;

  constructor(
    private router: Router,
    private authService: AuthService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    const profileUsername: string = this._getUsernameFormUrl(this.router.url);
    this._checkLoggedUser(profileUsername);
    this.userService.getUser(profileUsername).subscribe({
      next: user => (this.user = user),
      error: _ => this.router.navigate(['/error']),
    });
  }

  private _getUsernameFormUrl(url: string): string {
    return url.split('/')[2];
  }

  private _checkLoggedUser(profileUsername: string) {
    if (!this.authService.isLoggedIn()) return;
    this.notGuest = true;
    const loggedUserUsername: string = this.authService.loggedUser!.username;
    if (loggedUserUsername === profileUsername) this.ownProfile = true;
    else {
      this.ownProfile = false;
      this._checkFollow(loggedUserUsername, profileUsername);
    }
  }

  private _checkFollow(loggedUserUsername: string, profileUsername: string) {
    this.userService.getFollowing(loggedUserUsername).subscribe(follow => {
      console.log(follow);
    });
  }

  follow() {}

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
