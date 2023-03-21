import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProfileRoutingModule } from './profile-routing.module';

import { ProfileComponent } from './pages/profile/profile.component';
import { FollowComponent } from './pages/follow/follow.component';

@NgModule({
  declarations: [ProfileComponent, FollowComponent],
  imports: [CommonModule, ProfileRoutingModule],
})
export class ProfileModule {}
