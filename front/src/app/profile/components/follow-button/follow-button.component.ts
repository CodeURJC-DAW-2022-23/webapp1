import { Component, Input, OnInit } from '@angular/core';
import { UserFollow } from '../../interfaces/follow.interface';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-follow-button',
  templateUrl: './follow-button.component.html',
  styleUrls: ['./follow-button.component.css'],
})
export class FollowButtonComponent implements OnInit {
  @Input() followUser: UserFollow | undefined;
  followed: boolean | undefined;

  constructor(private router: Router, private userService: UserService) {}

  ngOnInit(): void {
    this.followed = this.followUser?.follow;
  }

  follow() {
    this.userService.follow(this.followUser!.username).subscribe({
      next: _ => {
        this.followed = !this.followed;
        console.log(this.router.url);
      },
    });
  }
}
