import { Component, HostListener, OnInit } from '@angular/core';
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
  endOfFeed: boolean = false;

  constructor(private postsService: PostsService) {}

  ngOnInit(): void {
    //this.initializeFollowersList();
    this.postsService
      .getFilter()
      .pipe(distinctUntilChanged())
      .subscribe(data => {
        this.page = 0;
        this.posts = [];
        this.getPosts();
        console.log('FILTERING POSTS: ', data);
      });
  }

  getPosts() {
    this.postsService
      .getPosts(this.page)
      .pipe(take(1))
      .subscribe(response => {
        let data: any = response;
        if (data === null) this.endOfFeed = true;
        else {
          for (var i = 0; i < data.content.length; i++) {
            let post = data.content[i];
            //check author
            /* if (this.authService.isLoggedIn()) {
                post.followingAuthor = this.followedUsers.includes(
                  post.authorName
                );
              } */
            this.posts.push(post);
          }
        }
      });
  }

  /* initializeFollowersList() {
    if (this.authService.isLoggedIn())
      this.userService
        .getFollowing(this.authService.loggedUser!.username)
        .pipe()
        .subscribe(response => {
          let usernames: any = response;
          for (var i = 0; i < usernames.users.length; i++) {
            let username = usernames.users[i].username;
            if (username !== this.authService.loggedUser?.username)
              this.followedUsers.push(username);
          }
        });
    console.log(this.followedUsers);
  } */

  @HostListener('window:scroll', ['$event'])
  onWindowScroll() {
    //In chrome and some browser scroll is given to body tag
    let pos =
      (document.documentElement.scrollTop || document.body.scrollTop) +
      document.documentElement.offsetHeight;
    let max = document.documentElement.scrollHeight;
    // pos/max will give you the distance between scroll bottom and and bottom of screen in percentage.
    if (pos == max) {
      this.page++;
      this.getPosts();
      this.loading = false;
    }
  }
}
