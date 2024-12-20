import { Component, OnDestroy, ViewChild } from '@angular/core';
import { AuthInterface } from '../../interface/auth.interface';
import { FormsModule, NgForm } from '@angular/forms';
import { NgIf } from '@angular/common';
import { Subject, takeUntil } from 'rxjs';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Pages } from '../../other/typesAndConsts';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, NgIf],
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnDestroy {
  @ViewChild('loginForm') private loginForm!: NgForm;
  private componentDestroyed$ = new Subject<void>();
  notCorrectLoginOrEmailOrPasswords: boolean = false;

  loginData: AuthInterface = {
    loginOrEmail: '',
    password: '',
  };

  constructor(
    private http: HttpClient,
    private router: Router
  ) {}

  ngOnDestroy(): void {
    this.componentDestroyed$.next();
    this.componentDestroyed$.complete();
  }

  onSubmit() {
    if (this.loginForm.invalid) {
      return;
    }

    this.notCorrectLoginOrEmailOrPasswords = false;
    const payload = new HttpParams()
      .set('username', this.loginData.loginOrEmail)
      .set('password', this.loginData.password);

    this.http
      .post('api/login', payload)
      .pipe(takeUntil(this.componentDestroyed$))
      .subscribe({
        next: () => {
          this.router.navigateByUrl(Pages.TIMER);
        },
        error: (response: HttpResponse<any>) => {
          console.log(response.status);
          console.log('response.status');
          if (response.status === 401) {
            this.notCorrectLoginOrEmailOrPasswords = true;
          } else {
            // TODO inny error?
          }
        },
      });
  }
}
