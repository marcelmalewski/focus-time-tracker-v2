import { Component } from '@angular/core';
import { LoginDataInterface } from '../../interface/login-data.interface';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
})
export class LoginComponent {
  loginData: LoginDataInterface = {
    login: '',
    password: '',
  };

  submitted = false;

  onSubmit() {
    this.submitted = true;
  }
}
