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
    name: [, [Validators.required, Validators.minLength(3)]],
    email: [, [Validators.required, Validators.email]],
    passwords: this.fb.group(
      {
        password: [, [Validators.required, Validators.minLength(5)]],
        confirmPassword: [, [Validators.required, Validators.minLength(5)]],
      },
      { validator: this.checkPasswords }
    ),
    termsOfService: [false, [Validators.requiredTrue]],
  });

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

  isInvalidField(field: string | string[]) {
    return (
      this.registerForm.get(field)?.touched &&
      this.registerForm.get(field)?.errors
    );
  }

  save() {
    // TODO: use service for checking
    console.log(this.registerForm.value);
    this.registerForm.reset();
  }
}
