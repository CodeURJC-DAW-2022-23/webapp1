import { Component, OnInit } from '@angular/core';
import { Topic } from '../../interfaces/topic.interface';
import { AdminHttpsService } from '../../services/admin-Https-Service';


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


  constructor(private httpService: AdminHttpsService) {

  }
  getTopic(topicName: String) {
    this.httpService.getTopic(topicName).subscribe(
      response => {
        console.log(response);
        this.topic = response;
      },
      error => { }
    )
  }

  deleteTopic(topicName: String) {
    this.httpService.deleteTopic(topicName).subscribe(
      response => {

        //Notification of topic deleted
        if (Notification.permission === 'granted') {
          new Notification('topic deleted', {
            body: 'The topic has been deleted',
          });
        } else if (Notification.permission !== 'denied') {
          Notification.requestPermission().then(permission => {
            if (permission === 'granted') {
              new Notification('topic deleted', {
                body: 'The topic has been deleted',
              });
            }
          });
        }
      }
    )
  }

  addTopic(topicName: string, topicDescription: string) {

    this.httpService.addTopic(topicName, topicDescription).subscribe(
      response => {
        this.newTopic = response;

        //Notification of topic created
        if (Notification.permission === 'granted') {
          new Notification('topic created', {
            body: 'The topic has been created',
          });
        } else if (Notification.permission !== 'denied') {
          Notification.requestPermission().then(permission => {
            if (permission === 'granted') {
              new Notification('topic created', {
                body: 'The topic has been created',
              });
            }
          });
        }
      }
    )
  }

  optionChose(option: String) {
    this.option = option;

  }








}
