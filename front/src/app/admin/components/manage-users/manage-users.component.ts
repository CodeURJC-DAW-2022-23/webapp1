import { Component } from '@angular/core';
import { StaredUser } from '../../interfaces/staredUser.interface';
import { AdminHttpsService } from '../../services/admin-Https-Service';






@Component({
  selector: 'app-manage-users',
  templateUrl: './manage-users.component.html',
  styleUrls: ['./manage-users.component.css'],

})

export class ManageUsersComponent {
  userStared: StaredUser[] = [];
  isVisible = false;
  userNotFound = false;
  banAction = "";

  constructor(private httpService: AdminHttpsService) { }

  getUser(username: String) {
    this.httpService.getUser(username).subscribe(
      response => {
        this.userStared = response;
        this.isVisible = true;
        this.userNotFound = false;

      },
      error => {
        this.userNotFound = true;
      }
    )
  }

  banUser(i: number) {
    this.httpService.banUser(this.userStared[i] as StaredUser).subscribe(
      response => {
        this.userStared[i] = response as StaredUser
        this._banNotification(response.locked);
      },
      error => console.error(error)
    );
  }


  private _banNotification(state: boolean) {
    if (state) {
      this.banAction = "banned"
    }
    else {
      this.banAction = "unbanned"

    }
    //Notification of topic created
    if (Notification.permission === 'granted') {
      new Notification('User ' + this.banAction, {
        body: 'The user has been ' + this.banAction,
      });
    } else if (Notification.permission !== 'denied') {
      Notification.requestPermission().then(permission => {
        if (permission === 'granted') {
          new Notification('User ' + this.banAction, {
            body: 'The topic has been ' + this.banAction,
          });
        }
      });
    }
  }


}
