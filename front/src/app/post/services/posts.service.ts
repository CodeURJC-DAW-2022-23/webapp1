import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { Post } from 'src/app/models/post.model';

const BASE_URL = 'https://localhost:8443/api/posts';

@Injectable({
  providedIn: 'root',
})
export class PostsService {
  constructor(private http: HttpClient) {}

  getPosts(page: number): Observable<any> {
    return this.http.get(
      BASE_URL + '?page=' + page.toString()
    ) as Observable<any>;

  }
}
