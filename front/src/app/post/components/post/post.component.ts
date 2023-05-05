import { Component, Input, OnInit } from '@angular/core';
import { Post } from 'src/app/models/post.model';
import { PostsService } from '../../services/posts.service';
import { distinctUntilChanged } from 'rxjs';
import { AuthService } from 'src/app/auth/services/auth.service';
import { PostItem } from 'src/app/models/postItem.model';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css'],
})
export class PostComponent implements OnInit {
  @Input() post!: Post;
  filterState: boolean = false;
  followingList: string[] = [];
  filter: boolean = false;
  upvoted: boolean = false;
  downvoted: boolean = false;

  constructor(
    private postsService: PostsService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.checkIfVoted();
    this.postsService
      .getFilter()
      .pipe(distinctUntilChanged())
      .subscribe(data => {
        this.filterState = data;
        this.filter = this.filterState; //&& !this.post.followingAuthor
      });
  }

  checkIfVoted() {
    if (this.authService.isLoggedIn()) {
      if (this.authService.loggedUser?.username !== undefined) {
        let username = this.authService.loggedUser?.username;
        if (this.post.downVotesUsernames.includes(username))
          this.downvoted = true;
        else if (this.post.upVotesUsernames.includes(username))
          this.upvoted = true;
      }
    }
  }

  upvotePost() {
    if (this.authService.isLoggedIn()) {
      if (this.upvoted) this.upvoted = false;
      else {
        this.upvoted = true;
        if (this.downvoted) this.downvoted = false;
      }
      this.postsService
        .upvotePost(this.post.id)
        .subscribe(response => this.updateVotes(response as number[]));
    }
  }

  downvotePost() {
    if (this.authService.isLoggedIn()) {
      if (this.downvoted) this.downvoted = false;
      else {
        this.downvoted = true;
        if (this.upvoted) this.upvoted = false;
      }

      this.postsService
        .downvotePost(this.post.id)
        .subscribe(response => this.updateVotes(response as number[]));
    }
  }

  updateVotes(pair: number[]) {
    let upvotes = pair.at(0);
    let downvotes = pair.at(1);

    if (upvotes !== undefined) this.post.numUpvotes = upvotes;
    if (downvotes !== undefined) this.post.numDownvotes = downvotes;
  }

  fetchImage(id: number) {
    return this.postsService.downloadImage(id);
  }

  //TODO: UPVOTES AND DOWNVOTES
  //upvoted: boolean = this.post.upVotes.find;
  //downvoted: boolean = this.post.downVotes.
}
