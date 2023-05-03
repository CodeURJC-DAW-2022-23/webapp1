import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { map } from 'rxjs/operators';
import { Topic } from '../interfaces/topic.interface';


const BASE_URL = 'api/topics';

@Injectable({
  providedIn: "root",
})
export class TopicsService {
  constructor(private http: HttpClient) {}

  getTopics(){
    return this.http.get<Topic[]>(BASE_URL).pipe(
      map((topics: Topic[]) => {
        return topics.map((topic) => {
          return {
            id: topic.id,
            name: topic.name,
            description: topic.description
          };
        });
      })
    );
  }
}




