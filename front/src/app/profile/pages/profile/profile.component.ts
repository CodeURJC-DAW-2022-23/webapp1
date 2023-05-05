import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { AuthService } from '../../../auth/services/auth.service';
import { Follow, UserFollow } from '../../interfaces/follow.interface';
import { PostsService } from 'src/app/post/services/posts.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {
  profileUser: any | undefined;
  notGuest: boolean = false;
  ownProfile: boolean | undefined;
  followUser: UserFollow | undefined;

  constructor(
    private router: Router,
    private titleService: Title,
    private authService: AuthService,
    private userService: UserService,
    private postService: PostsService
  ) { }

  ngOnInit(): void {
    const profileUsername: string = this._getUsernameFormUrl(this.router.url);
    this.titleService.setTitle(`Alist | ${profileUsername}`);
    this.userService.getUser(profileUsername).subscribe({
      next: user => {
        this.profileUser = user;
        this._checkLoggedUser(profileUsername);
      },
      error: _ => this.router.navigate(['/error']),
    });
  }

  private _getUsernameFormUrl(url: string): string {
    return url.split('/')[2];
  }

  private _checkLoggedUser(profileUsername: string) {
    this.ownProfile = false;
    if (!this.authService.isLoggedIn()) return;
    this.notGuest = true;
    const loggedUserUsername: string = this.authService.loggedUser!.username;
    if (loggedUserUsername === profileUsername) this.ownProfile = true;
    this._checkFollow(loggedUserUsername, profileUsername);
  }

  private _checkFollow(loggedUserUsername: string, profileUsername: string) {
    this.userService.getFollowing(loggedUserUsername).subscribe({
      next: (follow: Follow) => {
        const followUsers: UserFollow[] = follow.users;
        this.followUser = followUsers.find((followUser: UserFollow) => {
          if (followUser.username === profileUsername) return followUser;
          return;
        });
        if (!this.followUser) {
          this.followUser = { username: profileUsername, follow: false };
        }
      },
      error: _ => (this.followUser = undefined),
    });
  }

  fetchImage(id: number) {
    return this.postService.downloadImage(id);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
