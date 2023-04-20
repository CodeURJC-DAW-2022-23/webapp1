import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-follow-count',
  templateUrl: './follow-count.component.html',
  styleUrls: ['./follow-count.component.css'],
})
export class FollowCountComponent implements OnInit {
  @Input() followCount: number | undefined;
  @Input() follow: boolean | undefined;
  followUrl: string = '';

  constructor(private router: Router) {}

  ngOnInit(): void {
    const profileUrl: string = this.router.url;
    if (this.follow) this.followUrl = `${profileUrl}/following`;
    else this.followUrl = `${profileUrl}/followers`;
  }
}
