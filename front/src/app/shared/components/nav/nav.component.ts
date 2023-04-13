import { Component } from '@angular/core';
import { NavigationEnd, Router, Event } from '@angular/router';
import { filter } from 'rxjs';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css'],
})
export class NavComponent {
  compassIconGlow: boolean = false;
  heartIconGlow: boolean = false;
  profileIconGlow: boolean = false;
  // TODO: make this thing work with something, now not used
  filtered: boolean = false;

  constructor(private router: Router) {
    this.router.events
      .pipe(
        filter(
          (event: Event): event is NavigationEnd =>
            event instanceof NavigationEnd
        )
      )
      .subscribe(event => {
        this.disableAllIcons();
        const redirectRoute: string = event.urlAfterRedirects;
        if (redirectRoute === '/error') return;
        const currentRoute: string = event.url;
        if (currentRoute === '/') {
          this.compassIconGlow = true;
        } else if (
          currentRoute === '/profile' &&
          redirectRoute.startsWith('/user')
        ) {
          this.profileIconGlow = true;
        }
      });
  }

  toggleCompassIcon() {
    this.disableAllIcons();
    this.compassIconGlow = true;
  }

  toggleHeartIcon() {
    this.disableAllIcons();
    this.filtered = true;
    this.heartIconGlow = true;
  }

  disableAllIcons() {
    this.compassIconGlow = false;
    this.heartIconGlow = false;
    this.filtered = false;
    this.profileIconGlow = false;
  }
}
