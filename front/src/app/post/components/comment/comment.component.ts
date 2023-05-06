import { Component, Input } from '@angular/core';
import { Comment } from 'src/app/models/comment.model';
import { PostsService } from '../../services/posts.service';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css'],
})

export class CommentComponent {
  constructor(
    private postsService: PostsService) { }
  @Input() comment!: Comment;

  fetchImage(id: number) {
    return this.postsService.downloadImage(id);
  }
}


