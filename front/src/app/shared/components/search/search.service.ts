import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/user.model';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class SearchService {


  constructor(private httpClient: HttpClient) { }

  getUser(username: String): Observable<User[]> {
    return this.httpClient.get<User[]>("api/users/names/" + username);
  }


}
