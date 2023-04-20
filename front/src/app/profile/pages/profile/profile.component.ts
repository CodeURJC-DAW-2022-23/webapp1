import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { AuthService } from '../../../auth/services/auth.service';
import { Follow, UserFollow } from '../../interfaces/follow.interface';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {
  user: any;
  notGuest: boolean = false;
  ownProfile: boolean | undefined;
  followed: boolean | undefined;

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
    if (loggedUserUsername === profileUsername) {
      this.ownProfile = true;
      this.followed = false;
    } else {
      this.ownProfile = false;
      this._checkFollow(loggedUserUsername, profileUsername);
    }
  }

  private _checkFollow(loggedUserUsername: string, profileUsername: string) {
    this.userService.getFollowing(loggedUserUsername).subscribe({
      next: (follow: Follow) => {
        const followUsers: UserFollow[] = follow.users;
        console.log(followUsers);
        this.followed = followUsers.some((followUser: UserFollow) => {
          if (followUser.username === profileUsername) return true;
          else return false;
        });
      },
      error: _ => (this.followed = false),
    });
  }

  follow() {}

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
