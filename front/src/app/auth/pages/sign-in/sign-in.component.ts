import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['../form.css'],
})
export class SignInComponent {
  signInForm: FormGroup = this.fb.group({
    username: [, [Validators.required, Validators.minLength(3)]],
    password: [, [Validators.required, Validators.minLength(5)]],
  });

  constructor(private fb: FormBuilder) {}

  isInvalidField(field: string | string[]) {
    return (
      this.signInForm.get(field)?.touched && this.signInForm.get(field)?.errors
    );
  }

  save() {
    // TODO: use service for checking
    console.log(this.signInForm.value);
    this.signInForm.reset();
  }
}
