import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { SharedModule } from './shared/shared.module';
import { AuthModule } from './auth/auth.module';
import { AdminModule } from './admin/admin.module';
import { ProfileModule } from './profile/profile.module';
import { PostModule } from './post/post.module';

import { AppComponent } from './app.component';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    SharedModule,
    AuthModule,
    AdminModule,
    ProfileModule,
    PostModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
