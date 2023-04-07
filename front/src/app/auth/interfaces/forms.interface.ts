export interface SignInForm {
  username: string;
  password: string;
}

export interface RegisterForm {
  username: string;
  email: string;
  passwords: {
    password: string;
    confirmPassword: string;
  };
  termsOfService: boolean;
}
