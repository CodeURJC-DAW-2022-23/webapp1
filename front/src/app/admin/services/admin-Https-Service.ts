import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, catchError, of, throwError } from 'rxjs';
import { Topic } from '../interfaces/topic.interface';
import { StaredUser } from '../interfaces/staredUser.interface';


@Injectable({ providedIn: 'root' })

export class AdminHttpsService {
  constructor(private httpClient: HttpClient) { }

  getTopicsMapped(): Observable<Map<string, number>> {
    return this.httpClient.get<Map<string, number>>("api/topics/chart");
  }

  getTopic(topicName: String): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>("api/topics/byName/" + topicName);
  }

  deleteTopic(topicName: String): Observable<Topic> {
    return this.httpClient.delete<Topic>("api/topics/byName/" + topicName);
  }

  addTopic(topicName: string, topicDescription: string): Observable<Topic> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    const body = { name: topicName, description: topicDescription };

    return this.httpClient.post<Topic>("api/topics/", body, { headers });
  }

  banUser(userStared: StaredUser): Observable<StaredUser> {
    const params = new HttpParams()
      .set('operation', 'ban');
    return this.httpClient.put<StaredUser>("api/users/" + userStared?.id, params);
  }

  getUser(username: String): Observable<StaredUser[]> {
    return this.httpClient.get<StaredUser[]>("api/users/names/" + username);
  }





}
