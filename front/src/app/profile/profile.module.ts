import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProfileRoutingModule } from './profile-routing.module';

import { ProfileComponent } from './pages/profile/profile.component';
import { FollowComponent } from './pages/follow/follow.component';
import { FollowButtonComponent } from './components/follow-button/follow-button.component';
import { FollowCountComponent } from './components/follow-count/follow-count.component';

@NgModule({
  declarations: [
    ProfileComponent,
    FollowComponent,
    FollowButtonComponent,
    FollowCountComponent,
  ],
  imports: [CommonModule, ProfileRoutingModule],
})
export class ProfileModule {}
