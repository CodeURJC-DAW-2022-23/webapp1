import { Component } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { StaredUser } from '../../interfaces/staredUser.interface';





@Component({
  selector: 'app-manage-users',
  templateUrl: './manage-users.component.html',
  styleUrls: ['./manage-users.component.css'],

})

export class ManageUsersComponent {
  usersURL = "api/users/";
  userStared: StaredUser | undefined;
  isVisible = false;

  constructor(private httpClient: HttpClient) { }

  getUser(username: String) {
    this.httpClient.get<StaredUser>(this.usersURL + username).subscribe(
      response => {
        this.userStared = response;
      }
    )
    this.isVisible = true;
  }

  banUser() {
    const params = new HttpParams()
      .set('operation', 'ban');
    this.httpClient.put(this.usersURL + this.userStared?.id, params).subscribe(
      response => this.userStared = response as StaredUser,
      error => console.error(error)
    );
    let username = (this.userStared?.username as String)
  }


}
