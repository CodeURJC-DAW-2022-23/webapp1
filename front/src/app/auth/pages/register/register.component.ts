import { Component, DoCheck } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { RegisterForm } from '../../interfaces/forms.interface';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['../form.css'],
})
export class RegisterComponent implements DoCheck {
  registerForm: FormGroup = this.formBuilder.group({
    username: [, [Validators.required, Validators.minLength(3)]],
    email: [, [Validators.required, Validators.email]],
    passwords: this.formBuilder.group(
      {
        password: [, [Validators.required, Validators.minLength(4)]],
        confirmPassword: [, [Validators.required, Validators.minLength(4)]],
      },
      { validator: this.checkPasswords }
    ),
    termsOfService: [false, [Validators.requiredTrue]],
  });
  visiblePassword: boolean = false;
  usernameTaken: boolean = false;
  emailTaken: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthService
  ) {}

  private checkPasswords(
    control: AbstractControl
  ): { invalid: boolean } | null {
    const password: string = control.get('password')?.value;
    const confirmPassword: string = control.get('confirmPassword')?.value;
    if (password !== confirmPassword) return { invalid: true };
    return null;
  }

  ngDoCheck(): void {
    if (this.registerForm.get('username')?.touched) {
      this.usernameTaken = false;
    }
    if (this.registerForm.get('email')?.touched) {
      this.emailTaken = false;
    }
  }

  isNotSamePassword(): boolean {
    return this.registerForm.get('passwords')?.errors !== null;
  }

  isInvalidField(fieldSearch: string | string[]) {
    const field = this.registerForm.get(fieldSearch);
    return field?.touched && field?.errors;
  }

  alternateVisiblePassword() {
    this.visiblePassword = !this.visiblePassword;
  }

  register() {
    const registerForm: RegisterForm = this.registerForm.value;
    this.authService.register(registerForm).subscribe({
      next: _ => {
        alert('Check your email to activate your account');
        this.registerForm.reset();
        this.router.navigate(['/sign-in']);
      },
      error: error => this.checkError(error.status),
    });
  }

  private checkError(error: number) {
    if (error === 403) {
      this.usernameTaken = true;
      this.registerForm.get('username')?.reset();
    } else if (error === 400) {
      this.emailTaken = true;
      this.registerForm.get('email')?.reset();
    }
    this.registerForm.get('passwords')?.reset();
  }
}
