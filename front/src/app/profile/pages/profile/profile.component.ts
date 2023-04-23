import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { AuthService } from '../../../auth/services/auth.service';
import { Follow, UserFollow } from '../../interfaces/follow.interface';
import { Post } from 'src/app/models/post.model';
import { PostsService } from 'src/app/post/services/posts.service';
import { take } from 'rxjs';
import { User } from 'src/app/models/user.model';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {
  profileUser: any;
  notGuest: boolean = false;
  ownProfile: boolean | undefined;
  followUser: UserFollow | undefined;
  followData: any;

  posts: Post[] = [];
  page: number = 0;
  loading: boolean = true;
  endOfFeed: boolean | undefined;

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
        this._getPosts(profileUsername);
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
        this._createFollowData();
      },
      error: _ => (this.followUser = undefined),
    });
  }

  private _createFollowData() {
    this.followData = {
      profileUser: this.profileUser,
      followUsers: this.followUser,
    };
    console.log(this.followData);
  }

  private _getPosts(profileUsername: string) {
    this.postService
      .getUserPosts(this.page, profileUsername)
      .pipe(take(1))
      .subscribe(data => {
        const postsData: Post[] = data.content;
        const noPostsUser: boolean =
          postsData.length === 0 && this.endOfFeed === undefined;
        if (noPostsUser) this.loading = false;
        else if (postsData.length <= 1) {
          this.endOfFeed = true;
          this.loading = false;
        } else this.endOfFeed = false;
        this.posts.push(...postsData);
      });
  }

  @HostListener('window:scroll', ['$event'])
  onWindowScroll() {
    if (!this.loading) return;
    const maxHeight: number = document.documentElement.scrollHeight;
    const heightPos: number =
      (document.documentElement.scrollTop || document.body.scrollTop) +
      document.documentElement.offsetHeight;
    if (heightPos >= maxHeight) {
      this.page++;
      this._getPosts(this.profileUser.username);
    }
  }

  fetchImage(src: User) {
    return this.postService.downloadImage(src);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
