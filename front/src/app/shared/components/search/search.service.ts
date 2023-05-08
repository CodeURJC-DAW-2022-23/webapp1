import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/user.model';
import { HttpClient } from '@angular/common/http';
import { Post } from 'src/app/models/post.model';


@Injectable({
  providedIn: 'root'
})
export class SearchService {


  constructor(private httpClient: HttpClient) { }

  getUser(username: String): Observable<User[]> {
    return this.httpClient.get<User[]>("/api/users/names/" + username);
  }

  getPost(username: String): Observable<Post[]> {
    return this.httpClient.get<Post[]>("/api/posts/names/" + username);
  }



}
