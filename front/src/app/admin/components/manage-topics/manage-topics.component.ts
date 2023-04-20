import { Component, DoCheck, OnInit } from '@angular/core';
import { Topic } from '../../interfaces/topic.interface';
import { AdminHttpsService } from '../../services/admin-Https-Service';
import { FormGroup, AbstractControl, FormBuilder, Validators } from '@angular/forms';


@Component({
  selector: 'app-manage-topics',
  templateUrl: './manage-topics.component.html',
  styleUrls: ['./manage-topics.component.css'],
})

export class ManageTopicsComponent implements DoCheck {
  topicsURL = "api/topics/";
  topic: Topic | undefined;
  newTopic: Topic | undefined;
  option: String | undefined;
  alreadyTaken = false;
  topicNotFound = false;
  invalidFields = false;
  newTopicForm: FormGroup = this.formBuilder.group({
    newTopicName: [, [Validators.required, Validators.minLength(3)]],
    newTopicDescription: [, [Validators.required, Validators.minLength(3)]],

  })


  constructor(
    private formBuilder: FormBuilder,
    private httpService: AdminHttpsService) {
  }
  getTopic(topicName: String) {
    this.httpService.getTopic(topicName).subscribe(
      response => {
        this.topic = response;
      },
      error => {
        console.log(error.status)
        this.topicNotFound = true;
      }
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
        this.topic = undefined;
      }
    )
  }

  addTopic(topicName: string, topicDescription: string) {
    if (!this._fieldsValidator(topicName, topicDescription)) {
      this.invalidFields = true;
      return;
    }
    this.httpService.addTopic(topicName, topicDescription).subscribe(
      response => {
        console.log(response)
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
      },
      error => { if (error.status == "409") this.alreadyTaken = true }

    )
  }

  optionChose(option: String) {
    this.option = option;

  }

  private _fieldsValidator(name: String, description: String): boolean {
    return name.length >= 2 && description.length >= 2;
  }

  ngDoCheck(): void {
    if (this.newTopicForm.get('newTopicName')?.touched) {


    }
    if (this.newTopicForm.get('newTopicDescription')?.touched) {

    }
  }






}
