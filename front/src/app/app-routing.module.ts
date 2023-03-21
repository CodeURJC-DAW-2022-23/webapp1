import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { FeedComponent } from './post/pages/feed/feed.component';
import { SignInComponent } from './auth/pages/sign-in/sign-in.component';
import { RegisterComponent } from './auth/pages/register/register.component';
import { CreatePostComponent } from './post/pages/create-post/create-post.component';
import { TopComponent } from './post/pages/top/top.component';
import { AdminPanelComponent } from './admin/pages/admin-panel/admin-panel.component';
import { ErrorComponent } from './shared/pages/error/error.component';

const routes: Routes = [
  {
    path: '',
    component: FeedComponent,
  },
  {
    path: 'followed',
    component: FeedComponent,
  },
  {
    path: 'sign-in',
    component: SignInComponent,
  },
  {
    path: 'register',
    component: RegisterComponent,
  },
  {
    path: 'create',
    component: CreatePostComponent,
  },
  {
    path: 'post/:id',
    component: TopComponent,
  },
  {
    path: 'user/:id',
    loadChildren: () =>
      import('./profile/profile.module').then(m => m.ProfileModule),
  },
  {
    path: 'admin',
    component: AdminPanelComponent,
  },
  {
    path: 'error',
    component: ErrorComponent,
  },
  {
    path: '**',
    redirectTo: 'error',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
