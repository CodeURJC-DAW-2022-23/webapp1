import { PostsService } from './../../../post/services/posts.service';
import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router, Event } from '@angular/router';
import { filter } from 'rxjs';
import { AuthService } from 'src/app/auth/services/auth.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css'],
})
export class NavComponent implements OnInit {
  compassIconGlow: boolean = false;
  heartIconGlow: boolean = false;
  profileIconGlow: boolean = false;
  filtered: boolean = false;

  constructor(
    private router: Router,
    private authService: AuthService,
    private postService: PostsService
  ) { }

  ngOnInit(): void {
    this.router.events
      .pipe(
        filter(
          (event: Event): event is NavigationEnd =>
            event instanceof NavigationEnd
        )
      )
      .subscribe((event: NavigationEnd) => {
        this.disableAllIcons();
        const currentRoute: string = event.url;
        if (currentRoute === '/') {
          this.postService.getFilter().subscribe((filter: boolean) => {
            if (filter) this.heartIconGlow = true;
            else this._checkRoute(event, currentRoute);
          });
        } else this._checkRoute(event, currentRoute);
      });
  }

  private _checkRoute(event: NavigationEnd, currentRoute: string) {
    const redirectRoute: string = event.urlAfterRedirects;
    if (redirectRoute === '/error') return;
    if (currentRoute === '/') this.compassIconGlow = true
    else if (
      currentRoute.startsWith('/user') &&
      this.authService.isLoggedIn()
    ) {
      this.profileIconGlow = true;
    }
  }

  toggleCompassIcon() {
    this.disableAllIcons();
    this.compassIconGlow = true;
    this.filtered = false;
    this.postService.setFilter(this.filtered);
  }

  toggleHeartIcon() {
    if (!this.authService.isLoggedIn()) return;
    this.disableAllIcons();
    this.filtered = true;
    this.postService.setFilter(this.filtered);
    this.heartIconGlow = true;
  }

  disableAllIcons() {
    this.compassIconGlow = false;
    this.heartIconGlow = false;
    this.filtered = false;
    this.profileIconGlow = false;
  }
}
