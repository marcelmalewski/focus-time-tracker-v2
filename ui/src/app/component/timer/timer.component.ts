import {
    Component,
    CUSTOM_ELEMENTS_SCHEMA,
    OnDestroy,
    OnInit,
    ViewChild,
} from '@angular/core';
import { CommandLineComponent } from '../command-line/command-line.component';
import { MatFormField, MatInput } from '@angular/material/input';
import { HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Subject, takeUntil } from 'rxjs';
import { Pages, Stages } from '../../other/typesAndConsts';
import { GeneralActionsService } from '../../service/general-actions.service';
import { Router } from '@angular/router';
import { PrincipalDataService } from '../../service/principal-data.service';
import {
    AtLeastOneMessage,
    AtLeastZeroMessage,
    LessThanOrEqual59Message,
    LessThanOrEqual99Message,
    LoggedOutMessage,
    TimerSettingsUpdated,
    UnknownServerErrorMessage,
    UnknownServerErrorMessageRefreshPage,
} from '../../other/message';
import { NotificationService } from '../../service/notification.service';
import {
    MainTopicBasicData,
    TimerSettings,
} from '../../interface/person.interface';
import {
    MatOption,
    MatSelect,
    MatSelectModule,
} from '@angular/material/select';
import { NgIf } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDivider } from '@angular/material/divider';
import { FormsModule, NgForm } from '@angular/forms';
import { MatCard, MatCardContent } from '@angular/material/card';
import { MatSlideToggle } from '@angular/material/slide-toggle';
import { TimerService } from '../../service/timer.service';

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
        FormsModule,
        MatCard,
        MatCardContent,
        MatSlideToggle,
    ],
    templateUrl: './timer.component.html',
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class TimerComponent implements OnDestroy, OnInit {
    @ViewChild('timerForm') private timerForm!: NgForm;
    private componentDestroyed$ = new Subject<void>();
    mainTopicsBasicData: MainTopicBasicData[] | undefined;

    timerSettings: TimerSettings = {
        timerStage: Stages.HOME,
        timerSelectedTopic: '',
        timerSetHours: 0,
        timerSetMinutes: 0,
        timerSetSeconds: 0,
        timerShortBreak: 0,
        timerLongBreak: 0,
        timerAutoBreak: false,
        timerInterval: 0,
    };

    constructor(
        private router: Router,
        private generalActionsService: GeneralActionsService,
        private timerService: TimerService,
        private principalDataService: PrincipalDataService,
        private notificationService: NotificationService
    ) {}

    ngOnInit(): void {
        const { principalBasicData, mainTopicsBasicData } =
            this.principalDataService.getPrincipalMainTopicsBasicData();
        this.mainTopicsBasicData = mainTopicsBasicData;

        this.timerSettings.timerSetHours = principalBasicData.timerSetHours;
        this.timerSettings.timerSetMinutes = principalBasicData.timerSetMinutes;
        this.timerSettings.timerSetSeconds = principalBasicData.timerSetSeconds;
        this.timerSettings.timerShortBreak = principalBasicData.timerShortBreak;
        this.timerSettings.timerLongBreak = principalBasicData.timerLongBreak;
        this.timerSettings.timerAutoBreak = principalBasicData.timerAutoBreak;
        this.timerSettings.timerInterval = principalBasicData.timerInterval;
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
                            UnknownServerErrorMessageRefreshPage
                        );
                        this.router.navigateByUrl(Pages.UNKNOWN_ERROR, {
                            skipLocationChange: true,
                        });
                    }
                },
            });
    }

    onSubmitStart() {
        this.timerForm.controls['timerSelectedTopicInput'].setErrors({});
    }

    onSubmitSave() {
        this.timerForm.controls['timerSelectedTopicInput'].setErrors({
            required: true,
        });

        if (this.timerForm.invalid) {
            return;
        }

        const body = {
            timerStage: this.timerSettings.timerStage,
            timerSelectedTopic: this.timerSettings.timerSelectedTopic,
            timerSetHours: this.timerSettings.timerSetHours,
            timerSetMinutes: this.timerSettings.timerSetMinutes,
            timerSetSeconds: this.timerSettings.timerSetSeconds,
            timerShortBreak: this.timerSettings.timerShortBreak,
            timerLongBreak: this.timerSettings.timerLongBreak,
            timerAutoBreak: this.timerSettings.timerAutoBreak,
            timerInterval: this.timerSettings.timerInterval,
        };

        this.timerService
            .updatePrincipalTimerSettings(body)
            .pipe(takeUntil(this.componentDestroyed$))
            .subscribe({
                next: () => {
                    this.notificationService.openSuccessNotification(
                        TimerSettingsUpdated
                    );
                },
                error: (_: HttpResponse<any>) => {
                    this.notificationService.openErrorNotification(
                        UnknownServerErrorMessage
                    );
                },
            });
    }

    protected readonly AtLeastOneMessage = AtLeastOneMessage;
    protected readonly LessThanOrEqual99Message = LessThanOrEqual99Message;
    protected readonly AtLeastZeroMessage = AtLeastZeroMessage;
    protected readonly LessThanOrEqual59Message = LessThanOrEqual59Message;
}
