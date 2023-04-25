import { CommentsService } from './../../services/comments.service';
import { Component, OnInit } from '@angular/core';
import { PostsService } from '../../services/posts.service';
import { AuthService } from 'src/app/auth/services/auth.service';
import { Comment } from 'src/app/models/comment.model';
import { Post } from 'src/app/models/post.model';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-top',
  templateUrl: './top.component.html',
  styleUrls: ['./top.component.css'],
})
export class TopComponent implements OnInit {
  isLoggedIn: boolean = false;
  comments: Comment[] = [];
  page: number = 0;
  post!: Post;
  postId!: number;
  endOfComments: boolean = false;
  loading: boolean = false;

  constructor(
    private postsService: PostsService,
    private commentsService: CommentsService,
    private authService: AuthService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.isLoggedIn = this.authService.isLoggedIn();

    this.postId = parseInt(this.route.snapshot.paramMap.get('postId') || '-1');
    this.postsService
      .getPostById(this.postId)
      .subscribe(response => (this.post = response as Post));

    this.getComments();
  }

  getComments() {
    if (!this.endOfComments) {
      this.loading = true;
      this.commentsService.getComments(this.postId, this.page).subscribe(
        (fetchedComments: Comment[]) => {
          console.log('comments: ', this.comments);
          console.log('fetched: ', fetchedComments);

          const filteredFetchedComments = fetchedComments.filter(
            fetchedComment => {
              return !this.comments.some(
                comment => comment.id == fetchedComment.id
              );
            }
          );
          this.comments = [...this.comments, ...filteredFetchedComments];
        },
        error => (this.endOfComments = true)
      );
      this.page++;
      this.loading = false;
    }
  }

  receiveNewComment(data: any) {
    const newComment: Comment = data as Comment;
    this.comments.unshift(newComment);
  }
}
