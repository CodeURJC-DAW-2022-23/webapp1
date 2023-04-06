import { Component, DoCheck } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SignInForm } from '../../interfaces/forms.interface';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['../form.css'],
})
export class SignInComponent implements DoCheck {
  signInForm: FormGroup = this.formBuilder.group({
    username: [, [Validators.required, Validators.minLength(3)]],
    password: [, [Validators.required, Validators.minLength(4)]],
  });
  invalidCredentials: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthService
  ) {}

  ngDoCheck(): void {
    if (this.signInForm.get('password')?.touched) {
      this.invalidCredentials = false;
    }
  }

  isInvalidField(fieldSearch: string) {
    const field = this.signInForm.get(fieldSearch);
    return field?.touched && field?.errors;
  }

  signIn() {
    const signInForm: SignInForm = this.signInForm.value;
    this.authService.login(signInForm).subscribe({
      next: _ => {
        this.signInForm.reset();
        this.router.navigate(['/']);
      },
      error: _ => {
        this.signInForm.get('password')?.reset();
        this.invalidCredentials = true;
      },
    });
  }
}
