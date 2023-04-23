import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { FeedComponent } from './post/pages/feed/feed.component';
import { SignInComponent } from './auth/pages/sign-in/sign-in.component';
import { RegisterComponent } from './auth/pages/register/register.component';
import { CreatePostComponent } from './post/pages/create-post/create-post.component';
import { TopComponent } from './post/pages/top/top.component';
import { AdminPanelComponent } from './admin/pages/admin-panel/admin-panel.component';
import { ErrorComponent } from './shared/pages/error/error.component';

import { AuthGuard } from './auth/guards/auth.guard';

const routes: Routes = [
  {
    path: '',
    component: FeedComponent,
    canActivate: [AuthGuard],
    data: { role: 'guest' },
  },
  {
    path: 'sign-in',
    component: SignInComponent,
    title: 'Alist | Sign In',
    canActivate: [AuthGuard],
    data: { role: 'guest' },
  },
  {
    path: 'register',
    component: RegisterComponent,
    title: 'Alist | Register',
    canActivate: [AuthGuard],
    data: { role: 'guest' },
  },
  {
    path: 'create',
    component: CreatePostComponent,
    title: 'Alist | Create Post',
    canActivate: [AuthGuard],
    data: { role: 'user' },
  },
  {
    path: 'post/:postId',
    component: TopComponent,
    title: 'Alist | Post',
    canActivate: [AuthGuard],
    data: { role: 'guest' },
  },
  {
    path: 'profile',
    loadChildren: () =>
      import('./profile/profile.module').then(m => m.ProfileModule),
    title: 'Alist | Profile',
    canActivate: [AuthGuard],
    data: { role: 'user' },
  },
  {
    path: 'user/:username',
    loadChildren: () =>
      import('./profile/profile.module').then(m => m.ProfileModule),
    title: 'Alist | User',
    canActivate: [AuthGuard],
    data: { role: 'guest' },
  },
  {
    path: 'admin',
    component: AdminPanelComponent,
    title: 'Alist | Admin Panel',
    canActivate: [AuthGuard],
    data: { role: 'admin' },
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
export class AppRoutingModule { }
