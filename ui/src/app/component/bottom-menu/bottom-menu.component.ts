import { Component, Input, OnDestroy } from '@angular/core';
import { CommandLineComponent } from '../command-line/command-line.component';
import { Subject, takeUntil } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { GeneralActionsService } from '../../service/general-actions.service';
import { PrincipalDataService } from '../../service/principal-data.service';
import { NotificationService } from '../../service/notification.service';
import {
    LoggedOutMessage,
    UnknownServerErrorMessageRefreshPage,
} from '../../spec/message-spec';
import { Pages } from '../../spec/common-spec';

@Component({
    selector: 'bottom-menu',
    standalone: true,
    templateUrl: './bottom-menu.component.html',
    imports: [CommandLineComponent],
})
export class BottomMenuComponent implements OnDestroy {
    @Input({ required: true })
    commandContext!: string;

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
                        this.principalDataService.clearPrincipalData();
                        this.router.navigateByUrl(Pages.LOGIN);
                        this.notificationService.openSuccessNotification(
                            LoggedOutMessage
                        );
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

    goToHome() {
        this.router.navigateByUrl(Pages.TIMER_HOME);
    }

    goToFocusSessions() {
        this.router.navigateByUrl(Pages.FOCUS_SESSIONS);
    }
}
