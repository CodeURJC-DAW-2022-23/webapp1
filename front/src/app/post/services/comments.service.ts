import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs';
import { Comment } from 'src/app/models/comment.model';

const BASE_URL = 'api/comments';

@Injectable({
  providedIn: 'root',
})
export class CommentsService {
  constructor(private http: HttpClient) {}

  getCommentsByPostId(postId: number) {
    return this.http.get(BASE_URL + '/posts/' + postId);
  }

  getComments(id: number, page: number) {
    const params = new HttpParams().set('id', id).set('page', page);
    return this.http
      .get<Comment[]>(BASE_URL + '/posts', { params })
      .pipe(map(res => res));
  }
}
