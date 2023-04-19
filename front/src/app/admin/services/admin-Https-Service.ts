import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, of, throwError } from 'rxjs';
import { Topic } from '../interfaces/topic.interface';


@Injectable({ providedIn: 'root' })

export class AdminHttpsService {
  constructor(private httpClient: HttpClient) { }

  getTopicsMapped(): Observable<Map<string, number>> {
    return this.httpClient.get<Map<string, number>>("api/topics/chart").pipe(
      catchError(error => {
        console.error(error);
        return of(new Map<string, number>());
      })
    );
  }

  getTopic(topicName: String): Observable<Topic> {
    return this.httpClient.get<Topic>("api/topics/" + "/byName/" + topicName).pipe(
      catchError(error => {
        console.error(error);
        return throwError(error);
      })
    );
  }

  deleteTopic(topicName: String): Observable<Topic> {
    return this.httpClient.delete<Topic>("api/topics/" + "/byName/" + topicName).pipe(
      catchError(error => {
        console.error(error);
        return throwError(error);
      })
    );
  }

  addTopic(topicName: string, topicDescription: string): Observable<Topic> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    const body = { name: topicName, description: topicDescription };

    return this.httpClient.post<Topic>("api/topics/", body, { headers }).pipe(
      catchError(error => {
        console.error(error);
        return throwError(error);
      })
    );
  }







}
