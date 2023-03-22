import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { SignInComponent } from './pages/sign-in/sign-in.component';
import { RegisterComponent } from './pages/register/register.component';

@NgModule({
  declarations: [SignInComponent, RegisterComponent],
  imports: [CommonModule, RouterModule],
  exports: [SignInComponent, RegisterComponent],
})
export class AuthModule {}
