import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoggedUser } from '../../interfaces/staredUser.interface';





@Component({
  selector: 'app-manage-users',
  templateUrl: './manage-users.component.html',
  styleUrls: ['./manage-users.component.css'],
  
})
  
export class ManageUsersComponent  {
  userStared: LoggedUser | undefined;
  username = '';

  constructor(private httpClient: HttpClient) { }
  
  getUser(username: String) {
    this.httpClient.get<LoggedUser>("https://localhost:8443/api/users/" + username).subscribe(
      response => {
        this.userStared = response; 
      }
    )
  }

  banUser() {
    this.httpClient.put("https://localhost:8443/api/users/" + this.userStared?.id, this.userStared).subscribe(
      response => console.log(response),
      error => console.error(error)
    );
  }
  

}
