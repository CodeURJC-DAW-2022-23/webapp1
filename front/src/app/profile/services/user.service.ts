import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) {}

  getUser(username: string) {
    return this.http.get(`/api/users/${username}`);
  }

  getFollowing(username: string) {
    return this.http.get(`/api/users/${username}/following`);
  }

  getFollowers(username: string) {
    return this.http.get(`/api/users/${username}/followers`);
  }
}