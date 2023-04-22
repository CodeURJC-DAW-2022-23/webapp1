import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { UserFollow } from '../../interfaces/follow.interface';
import { LoggedUser } from 'src/app/auth/interfaces/loggedUser.interface';

@Component({
  selector: 'app-follow',
  templateUrl: './follow.component.html',
  styleUrls: ['./follow.component.css'],
})
export class FollowComponent implements OnInit {
  profileUser: LoggedUser | undefined;
  followUsers: UserFollow[] | undefined;
  follow: string = '';

  constructor(private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    if (this.router.url.includes('following')) this.follow = 'following';
    else this.follow = 'followers';
    const paramsMap: ParamMap = this.route.snapshot.paramMap;
    console.log(paramsMap.get('profileUser'));
    console.log(paramsMap.get('followUsers'));
    console.log(this.profileUser);
    console.log(this.followUsers);
  }
}
