import { Component, CUSTOM_ELEMENTS_SCHEMA, OnDestroy } from '@angular/core';
import { CommandLineComponent } from '../command-line/command-line.component';
import { MatFormField, MatInput } from '@angular/material/input';
import { HttpParams, HttpResponse } from '@angular/common/http';
import { Subject, takeUntil } from 'rxjs';
import { Pages } from '../../other/typesAndConsts';
import { GeneralActionsService } from '../../service/general-actions.service';
import { Router } from '@angular/router';
import { PrincipalDataService } from '../../service/principal-data.service';
import {
    LoggedOutMessage,
    UnknownServerErrorMessage,
} from '../../other/message';
import { NotificationService } from '../../service/notification.service';

@Component({
    selector: 'app-home',
    standalone: true,
    imports: [CommandLineComponent, MatInput, MatFormField],
    templateUrl: './timer.component.html',
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class TimerComponent implements OnDestroy {
    private componentDestroyed$ = new Subject<void>();

    constructor(
        private router: Router,
        private generalActionsService: GeneralActionsService,
        private principalDataService: PrincipalDataService,
        private notificationService: NotificationService
    ) {}

    ngOnDestroy(): void {
        this.componentDestroyed$.next();
        this.componentDestroyed$.complete();
    }

    submitLogout() {
        this.generalActionsService
            .logout()
            .pipe(takeUntil(this.componentDestroyed$))
            .subscribe({
                error: (response: HttpResponse<any>) => {
                    if (response.status === 401) {
                        this.principalDataService.setPrincipalBasicData(
                            undefined
                        );
                        this.router.navigateByUrl(Pages.LOGIN);
                        this.notificationService.openSuccessNotification(
                            LoggedOutMessage
                        );
                    } else {
                        this.notificationService.openErrorNotification(
                            UnknownServerErrorMessage
                        );
                        this.router.navigateByUrl(Pages.UNKNOWN_ERROR, {
                            skipLocationChange: true,
                        });
                    }
                },
            });
    }
}
