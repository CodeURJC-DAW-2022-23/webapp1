import { Component, HostListener, Input, OnInit } from '@angular/core';
import { PostsService } from '../../services/posts.service';
import { Post } from 'src/app/models/post.model';
import { distinctUntilChanged, take } from 'rxjs';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css'],
})
export class FeedComponent implements OnInit {
  posts: Post[] = [];
  followedUsers: string[] = [];
  page: number = 0;
  loading: boolean = true;
  endOfFeed: boolean | undefined;

  @Input() profileUsername: any | undefined;

  constructor(private postService: PostsService) { }

  ngOnInit(): void {
    if (this.profileUsername) this._getUserPosts();
    else {
      this.postService
        .getFilter()
        .pipe(distinctUntilChanged())
        .subscribe(_ => {
          this.endOfFeed = false;
          this.posts = [];
          this.page = 0;
          this._getPosts();
        });
    }
  }

  private _getUserPosts() {
    this.postService
      .getUserPosts(this.page, this.profileUsername)
      .pipe(take(1))
      .subscribe(data => this._processData(data));
  }

  private _getPosts() {
    this.postService
      .getPosts(this.page)
      .pipe(take(1))
      .subscribe(data => this._processData(data));
  }

  private _processData(data: any) {
    const postsData: Post[] = data.content;
    console.log(postsData.length);
    const noPostsUser: boolean = postsData.length === 0;
    if (noPostsUser && this.profileUsername && this.page == 0) {
      this.loading = false;
      this.endOfFeed = undefined;
    } else if (noPostsUser) {
      this.loading = false;
      this.endOfFeed = true;
    }
    else if (postsData.length <= 1) {
      this.endOfFeed = true;
      this.loading = false;
    } else this.endOfFeed = false;
    this.posts.push(...postsData);
  }

  @HostListener('window:scroll', ['$event'])
  onWindowScroll() {
    if (this.posts.length == 0) return;
    const maxHeight: number = document.documentElement.scrollHeight - 1;
    const heightPos: number =
      (document.documentElement.scrollTop || document.body.scrollTop) +
      document.documentElement.offsetHeight;
    if (heightPos >= maxHeight) {
      this.page++;
      if (this.profileUsername) this._getUserPosts();
      else this._getPosts();
      this.loading = false;
    }
  }
}
