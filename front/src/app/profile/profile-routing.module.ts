import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ProfileComponent } from './pages/profile/profile.component';
import { FollowComponent } from './pages/follow/follow.component';
import { AuthGuard } from '../auth/guards/auth.guard';

const routes: Routes = [
  {
    path: '',
    component: ProfileComponent,
  },
  {
    path: 'following',
    component: FollowComponent,
    title: 'Alist | User Following',
    canActivate: [AuthGuard],
    data: { role: 'guest' },
  },
  {
    path: 'followers',
    component: FollowComponent,
    title: 'Alist | User Followers',
    canActivate: [AuthGuard],
    data: { role: 'guest' },
  },
  {
    path: '**',
    redirectTo: '',
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ProfileRoutingModule {}
