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
    title: 'Alist | Followed',
  },
  {
    path: 'sign-in',
    component: SignInComponent,
    title: 'Alist | Sign In',
  },
  {
    path: 'register',
    component: RegisterComponent,
    title: 'Alist | Register',
  },
  {
    path: 'create',
    component: CreatePostComponent,
    title: 'Alist | Create Post',
  },
  {
    path: 'post/:id',
    component: TopComponent,
    title: 'Alist | Post',
  },
  {
    path: 'user/:id',
    loadChildren: () =>
      import('./profile/profile.module').then(m => m.ProfileModule),
    title: 'Alist | User',
  },
  {
    path: 'admin',
    component: AdminPanelComponent,
    title: 'Alist | Admin Panel',
  },
  {
    path: 'error',
    component: ErrorComponent,
    title: 'Alist | Error',
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
