import { Component, OnInit } from '@angular/core';
import { Topic } from '../../interfaces/topic.interface';
import { HttpClient } from '@angular/common/http';



@Component({
  selector: 'app-manage-topics',
  templateUrl: './manage-topics.component.html',
  styleUrls: ['./manage-topics.component.css'],
})
export class ManageTopicsComponent {
  topicsURL = "/api/topics/";
  topic: Topic | undefined;

  constructor(private httpClient: HttpClient, private array_topics:Array<Topic>) { }

  deleteTopic(title: string) {
    
  }

  getTopic(username: String) {
    this.httpClient.get<Topic>(this.topicsURL+ username).subscribe(
      response => {
        this.topic = response; 
      }
    )
  }




}
