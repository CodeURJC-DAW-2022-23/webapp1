import { CommentForm } from './CommentForm';
import { AuthService } from './../../../../auth/services/auth.service';
import { CommentsService } from './../../../services/comments.service';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { PostsService } from 'src/app/post/services/posts.service';

@Component({
  selector: 'app-comment-form',
  templateUrl: './comment-form.component.html',
  styleUrls: ['./comment-form.component.css'],
})
export class CommentFormComponent {
  @Input() postId!: number;
  @Output() newComment: EventEmitter<Comment> = new EventEmitter<Comment>();

  commentForm: FormGroup;
  commentCreated: boolean = false;
  content = '';

  constructor(
    private formBuilder: FormBuilder,
    private commentsService: CommentsService,
    private route: ActivatedRoute,
    private authService: AuthService,
    private postsService: PostsService
  ) {
    this.commentForm = this.formBuilder.group({
      username: this.authService.loggedUser?.username,
      content: ['', [Validators.required, Validators.minLength(1)]],
      date: new Date(),
    });
  }

  onSubmit() {
    this.commentCreated = false;
    if (this.commentForm.valid) {
      const commentForm: CommentForm = this.commentForm.value;
      this.commentsService
        .postComment(this.postId, commentForm)
        .subscribe(response => {
          this.commentCreated = true;
          this.newComment.emit(response as Comment);
        });
    }
    this.content = '';
  }

  fetchUserImage() {
    const userID = this.authService.loggedUser?.id as number;
    return this.postsService.downloadImage(userID);
  }
}
