import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SignInComponent } from './pages/sign-in/sign-in.component';
import { RegisterComponent } from './pages/register/register.component';

@NgModule({
  declarations: [SignInComponent, RegisterComponent],
  imports: [CommonModule],
  exports: [SignInComponent, RegisterComponent],
})
export class AuthModule {}
