import { Component } from '@angular/core';
import { NavigationEnd, Router, Event } from '@angular/router';
import { filter } from 'rxjs';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css'],
})
export class NavComponent {
  compassIconGlow: boolean;
  heartIconGlow: boolean;
  profileIconGlow: boolean;
  currentRoute: string;
  filtered: boolean;

  constructor(private router: Router) {
    this.filtered = false;
    this.currentRoute = '';
    this.compassIconGlow = false;
    this.heartIconGlow = false;
    this.profileIconGlow = false;
    this.router.events
      .pipe(
        filter((e: Event): e is NavigationEnd => e instanceof NavigationEnd)
      )
      .subscribe(e => {
        this.currentRoute = e.url;
        this.toggleOff();
        if (this.currentRoute === '/profile') {
          this.profileIconGlow = true;
        } else if (this.currentRoute === '/explore') {
          this.compassIconGlow = true;
        }
      });
  }

  toggleHeartIcon = () => {
    this.toggleOff();
    this.filtered = true;
    this.heartIconGlow = true;
  };

  toggleOff = () => {
    this.compassIconGlow = false;
    this.heartIconGlow = false;
    this.profileIconGlow = false;
    this.heartIconGlow = false;
    this.filtered = false;
  };

  ngOnInit() {}
}
