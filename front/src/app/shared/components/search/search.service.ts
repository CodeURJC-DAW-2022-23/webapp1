import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/user.model';
import { HttpClient } from '@angular/common/http';
import { Post } from 'src/app/models/post.model';


@Injectable({
  providedIn: 'root'
})
export class SearchService {
  private apiUrl = '/api/posts/names/';
  private apiUrlUsers = '/api/users/names/';

  constructor(private httpClient: HttpClient) { }

  getUser(username: String): Observable<User[]> {
    return this.httpClient.get<User[]>(this.apiUrlUsers + username);
  }

  getPost(username: String): Observable<Post[]> {
    return this.httpClient.get<Post[]>(this.apiUrl + username);
  }



}
