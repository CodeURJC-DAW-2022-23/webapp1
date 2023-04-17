import { Component, OnInit } from '@angular/core';
import { Topic } from '../../interfaces/topic.interface';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';



@Component({
  selector: 'app-manage-topics',
  templateUrl: './manage-topics.component.html',
  styleUrls: ['./manage-topics.component.css'],
})
export class ManageTopicsComponent {
  topicsURL = "api/topics/";
  topic: Topic | undefined;
  newTopic: Topic | undefined;
  option: String | undefined;
  

  constructor(private httpClient: HttpClient) {

  }
  getTopic(topicName: String) {
    this.httpClient.get<Topic>(this.topicsURL +"/byName/" + topicName).subscribe(
      response => {
        this.topic = response; 
      }
    )
  }

  deleteTopic(topicName: String) {
    this.httpClient.delete(this.topicsURL +"/byName/"+ topicName).subscribe(
      response => {
        let answer = response;
      }
    )
  }

 addTopic(topicName: string, topicDescription: string) {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    const body = { name: topicName, description: topicDescription };

    this.httpClient.post<Topic>(this.topicsURL, body, { headers }).subscribe(
      response => {
        this.newTopic = response;
      }
    )
  }

  optionChose(option: String) {
    this.option = option;
    
  }

 

  




}
