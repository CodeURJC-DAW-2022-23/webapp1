import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, of } from 'rxjs';


@Injectable({ providedIn: 'root' })

export class AdminHttpsService {
  constructor(private httpClient: HttpClient) { }

  topics: Map<string, number> | undefined; 

 getTopicsMapped(): Observable<Map<string, number>> {
  return this.httpClient.get<Map<string, number>>("api/topics/chart").pipe(
    catchError(error => {
      console.error(error);
      return of(new Map<string, number>());
    })
  );
}


}
