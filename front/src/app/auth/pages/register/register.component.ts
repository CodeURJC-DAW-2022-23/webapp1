import { Component } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['../form.css'],
})
export class RegisterComponent {
  registerForm: FormGroup = this.fb.group({
    username: [, [Validators.required, Validators.minLength(3)]],
    email: [, [Validators.required, Validators.email]],
    passwords: this.fb.group(
      {
        password: [, [Validators.required, Validators.minLength(4)]],
        confirmPassword: [, [Validators.required, Validators.minLength(4)]],
      },
      { validator: this.checkPasswords }
    ),
    termsOfService: [false, [Validators.requiredTrue]],
  });
  visiblePassword: boolean = false;

  constructor(private fb: FormBuilder) {}

  private checkPasswords(
    control: AbstractControl
  ): { invalid: boolean } | null {
    const password: string = control.get('password')?.value;
    const confirmPassword: string = control.get('confirmPassword')?.value;
    if (password !== confirmPassword) return { invalid: true };
    return null;
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

  save() {
    // TODO: use service for checking
    console.log(this.registerForm.value);
    this.registerForm.reset();
  }
}
