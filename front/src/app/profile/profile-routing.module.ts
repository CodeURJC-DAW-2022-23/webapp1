import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ProfileComponent } from './pages/profile/profile.component';
import { FollowComponent } from './pages/follow/follow.component';

const routes: Routes = [
  {
    path: '',
    component: ProfileComponent,
    children: [
      {
        path: 'following',
        component: FollowComponent,
        title: 'Alist | User Following',
      },
      {
        path: 'followers',
        component: FollowComponent,
        title: 'Alist | User Followers',
      },
      {
        path: '**',
        redirectTo: '',
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ProfileRoutingModule {}
