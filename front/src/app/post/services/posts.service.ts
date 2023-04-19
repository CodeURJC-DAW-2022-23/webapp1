import {
  HttpClient,
  HttpHeaders,
  HttpParams,
  HttpResponse,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map, filter, BehaviorSubject } from 'rxjs';
import { Post } from 'src/app/models/post.model';

const BASE_URL = 'api/posts';

@Injectable({
  providedIn: 'root',
})
export class PostsService {
  private filter = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient) {}

  getPosts(page: number): Observable<any> {
    const params = new HttpParams()
      .set('page', page)
      .set('filter', this.filter.getValue());
    return this.http.get(BASE_URL, { params }) as Observable<any>;
  }

  setFilter(filter: boolean) {
      this.filter.next(filter);
  }

  getFilter() {
    return this.filter.asObservable();
  }
}
