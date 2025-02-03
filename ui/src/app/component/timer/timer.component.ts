import {
    Component,
    CUSTOM_ELEMENTS_SCHEMA,
    OnDestroy,
    OnInit,
} from '@angular/core';
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
import {
    MainTopicBasicData,
    PrincipalBasicData,
    PrincipalWithMainTopics,
} from '../../interface/person.interface';
import {
    MatOption,
    MatSelect,
    MatSelectModule,
} from '@angular/material/select';
import { NgIf } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDivider, MatDividerModule } from '@angular/material/divider';

@Component({
    selector: 'app-home',
    standalone: true,
    imports: [
        CommandLineComponent,
        MatInput,
        MatFormField,
        MatSelect,
        MatOption,
        NgIf,
        MatFormFieldModule,
        MatSelectModule,
        MatDivider,
    ],
    templateUrl: './timer.component.html',
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class TimerComponent implements OnDestroy, OnInit {
    private componentDestroyed$ = new Subject<void>();
    principalBasicData: PrincipalBasicData | undefined;
    mainTopicsBasicData: MainTopicBasicData[] | undefined;

    constructor(
        private router: Router,
        private generalActionsService: GeneralActionsService,
        private principalDataService: PrincipalDataService,
        private notificationService: NotificationService
    ) {}

    ngOnInit(): void {
        const { principalBasicData, mainTopicsBasicData } =
            this.principalDataService.getPrincipalMainTopicsBasicData();
        this.principalBasicData = principalBasicData;
        this.mainTopicsBasicData = mainTopicsBasicData;
    }

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
