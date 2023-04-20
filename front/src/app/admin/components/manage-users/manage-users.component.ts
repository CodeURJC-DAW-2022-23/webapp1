import { Component } from '@angular/core';
import { StaredUser } from '../../interfaces/staredUser.interface';
import { AdminHttpsService } from '../../services/admin-Https-Service';






@Component({
  selector: 'app-manage-users',
  templateUrl: './manage-users.component.html',
  styleUrls: ['./manage-users.component.css'],

})

export class ManageUsersComponent {
  userStared: StaredUser | undefined;
  isVisible = false;

  constructor(private httpService: AdminHttpsService) { }

  getUser(username: String) {
    this.httpService.getUser(username).subscribe(
      response => {
        this.userStared = response;
      }
    )
    this.isVisible = true;
  }

  banUser() {
    this.httpService.banUser(this.userStared as StaredUser).subscribe(
      response => this.userStared = response as StaredUser,
      error => console.error(error)
    );
  }


}
