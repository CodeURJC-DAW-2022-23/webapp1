import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Follow, UserFollow } from '../../interfaces/follow.interface';
import { UserService } from '../../services/user.service';
import { PostsService } from 'src/app/post/services/posts.service';
import { AuthService } from 'src/app/auth/services/auth.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-follow',
  templateUrl: './follow.component.html',
  styleUrls: ['./follow.component.css'],
})
export class FollowComponent implements OnInit {
  profileUser!: any;
  profileFollowUsers!: UserFollow[];
  follow: string = '';
  notGuest: boolean = false;
  loggedUserUsername: string = '';
  loggedUserFollow: UserFollow[] | undefined;

  constructor(
    private router: Router,
    private titleService: Title,
    private authService: AuthService,
    private userService: UserService,
    private postService: PostsService
  ) { }

  ngOnInit(): void {
    const url: string = this.router.url;
    if (url.includes('following')) this.follow = 'following';
    else this.follow = 'followers';
    const username: string = this._getUsernameFromUrl(url);
    this.titleService.setTitle(`Alist | ${username} ${this.follow}`);
    this._getUser(username);
  }

  private _getUsernameFromUrl(url: string): string {
    return url.split('/')[2];
  }

  private _getUser(username: string) {
    this.userService.getUser(username).subscribe({
      next: user => {
        this.profileUser = user;
        this._getFollowUsers(username);
        this._checkLoggedUser();
      },
      error: _ => this.router.navigate(['/error']),
    });
  }

  private _getFollowUsers(username: string) {
    if (this.follow === 'following') {
      this.userService.getFollowing(username).subscribe({
        next: (following: Follow) => this.profileFollowUsers = following.users,
      });
    } else if (this.follow === 'followers') {
      this.userService.getFollowers(username).subscribe({
        next: (followers: Follow) => this.profileFollowUsers = followers.users,
      });
    }
  }

  private _checkLoggedUser() {
    this.notGuest = this.authService.isLoggedIn();
    if (!this.notGuest) return;
    this.loggedUserUsername = this.authService.loggedUser!.username;
    this.userService.getFollowing(this.loggedUserUsername).subscribe({
      next: (following: Follow) => this.loggedUserFollow = following.users,
    });
  }

  getLoggedUserFollow(profileUsername: string): UserFollow | undefined {
    if (!this.loggedUserFollow) return undefined;
    const profileUserFollow = this.loggedUserFollow.find((user: UserFollow) => user.username === profileUsername);
    if (!profileUserFollow) return { username: profileUsername, follow: false };
    return profileUserFollow;
  }

  fetchImage(id: number) {
    return this.postService.downloadImage(id);
  }
}
