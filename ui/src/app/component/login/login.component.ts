import { Component, OnDestroy, ViewChild } from '@angular/core';
import { AuthInterface } from '../../interface/auth.interface';
import { FormsModule, NgForm } from '@angular/forms';
import { NgIf } from '@angular/common';
import { Subject, takeUntil } from 'rxjs';
import { HttpParams, HttpResponse } from '@angular/common/http';
import { Pages } from '../../other/typesAndConsts';
import { Router, RouterLink } from '@angular/router';
import { GeneralActionsService } from '../../service/general-actions.service';
import { UnknownServerErrorMessageRefreshPage } from '../../other/message';
import { NotificationService } from '../../service/notification.service';

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [FormsModule, NgIf, RouterLink],
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
        private router: Router,
        private generalActionsService: GeneralActionsService,
        private notificationService: NotificationService
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

        this.generalActionsService
            .login(payload)
            .pipe(takeUntil(this.componentDestroyed$))
            .subscribe({
                next: () => {
                    this.router.navigateByUrl(Pages.TIMER_HOME);
                },
                error: (response: HttpResponse<any>) => {
                    if (response.status === 401) {
                        this.notCorrectLoginOrEmailOrPasswords = true;
                    } else {
                        this.notificationService.openErrorNotification(
                            UnknownServerErrorMessageRefreshPage
                        );
                        this.router.navigateByUrl(Pages.UNKNOWN_ERROR, {
                            skipLocationChange: true,
                        });
                    }
                },
            });
    }
}
