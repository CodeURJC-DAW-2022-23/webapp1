import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Follow } from '../interfaces/follow.interface';
import { User } from 'src/app/models/user.model';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) { }

  getUser(username: string) {
    return this.http.get<User>(`/api/users/${username}`);
  }

  getFollowing(username: string) {
    return this.http.get<Follow>(`/api/users/${username}/following`);
  }

  getFollowers(username: string) {
    return this.http.get<Follow>(`/api/users/${username}/followers`);
  }

  follow(username: string) {
    return this.http.put(`/api/users/${username}/follow`, {});
  }
}
