import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map, filter } from 'rxjs';
import { Post } from 'src/app/models/post.model';

const BASE_URL = 'api/posts';

@Injectable({
  providedIn: 'root',
})
export class PostsService {
  constructor(private http: HttpClient) {}
  getPosts(page: number, filter: boolean): Observable<any> {
    const params = new HttpParams()
      .set('page', page)
      .set('filter', filter);
    return this.http.get(BASE_URL, {params}) as Observable<any>;

  }
}
