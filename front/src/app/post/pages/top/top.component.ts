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
  post!: Post;
  postId!: number;

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
    this.commentsService
      .getCommentsByPostId(this.postId)
      .subscribe(response => (this.comments = response as Comment[]));
  }
}
