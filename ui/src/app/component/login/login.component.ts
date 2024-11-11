import { Component } from '@angular/core';
import { LoginDataInterface } from '../../interface/login-data.interface';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, NgIf],
  templateUrl: './login.component.html',
})
export class LoginComponent {
  loginData: LoginDataInterface = {
    loginOrEmail: '',
    password: '',
  };

  onSubmit() {}
}
