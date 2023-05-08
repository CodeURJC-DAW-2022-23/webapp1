import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs';
import { Comment } from 'src/app/models/comment.model';
import { CommentForm } from '../components/comment/comment-form/CommentForm';

const BASE_URL = '/api/comments';

@Injectable({
  providedIn: 'root',
})
export class CommentsService {
  constructor(private http: HttpClient) { }

  getComments(id: number, page: number) {
    const params = new HttpParams().set('id', id).set('page', page);
    return this.http
      .get<Comment[]>(BASE_URL + '/posts', { params })
      .pipe(map(res => res));
  }

  postComment(postId: number, commentForm: CommentForm) {
    return this.http.post(BASE_URL + '/' + postId, commentForm);
  }
}
