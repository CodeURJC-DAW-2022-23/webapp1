import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Follow, UserFollow } from '../../interfaces/follow.interface';
import { UserService } from '../../services/user.service';
import { User } from 'src/app/models/user.model';
import { PostsService } from 'src/app/post/services/posts.service';
import { map } from 'rxjs';

@Component({
  selector: 'app-follow',
  templateUrl: './follow.component.html',
  styleUrls: ['./follow.component.css'],
})
export class FollowComponent implements OnInit {
  profileUser!: any;
  profileFollowUsers!: UserFollow[];
  follow: string = '';

  constructor(
    private router: Router,
    private userService: UserService,
    private postService: PostsService
  ) { }

  ngOnInit(): void {
    const url: string = this.router.url;
    if (url.includes('following')) this.follow = 'following';
    else this.follow = 'followers';
    const username: string = this._getUsernameFromUrl(url);
    this._getUser(username);
  }

  private _getUsernameFromUrl(url: string): string {
    return url.split('/')[2];
  }

  private _getUser(username: string) {
    this.userService.getUser(username).subscribe({
      next: user => {
        this.profileUser = user
        this._getFollowUsers(username);
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

  fetchImage(user: User) {
    return this.postService.downloadImage(user);
  }

  fetchImageString(username: string) {
    return this.userService.getUser(username).pipe(
      map((user: User) => {
        return this.postService.downloadImage(user);
      }));
  }
}
