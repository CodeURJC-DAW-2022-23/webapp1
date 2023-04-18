import { Component, OnInit, HostListener } from '@angular/core';
import { PostsService } from '../../services/posts.service';
import { Post } from 'src/app/models/post.model';
import { take } from 'rxjs';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css'],
})
export class FeedComponent implements OnInit {
  posts: Post[] = [];
  page: number = 0;
  loading: boolean = true;
  endOfFeed: boolean = false;

  constructor(private postsService: PostsService) {}

  ngOnInit(): void {
    this.page = 0;
    this.getPosts();
  }

  getPosts() {
    this.postsService
      .getPosts(this.page, false)
      .pipe(take(1))
      .subscribe(response => {
        let data: any = response;
        if (data === null) this.endOfFeed = true;
        else {
          for (var i = 0; i < data.content.length; i++) {
            let post = data.content[i];
            this.posts.push(post);
          }
        }
      });
  }

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
