import {
    Component,
    CUSTOM_ELEMENTS_SCHEMA,
    numberAttribute,
    OnDestroy,
    OnInit,
    ViewChild,
} from '@angular/core';
import { CommandLineComponent } from '../command-line/command-line.component';
import { MatFormField, MatInput } from '@angular/material/input';
import { HttpParams, HttpResponse } from '@angular/common/http';
import { Subject, takeUntil } from 'rxjs';
import { Pages, Stages } from '../../other/typesAndConsts';
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
    TimerBasicSettings,
} from '../../interface/person.interface';
import {
    MatOption,
    MatSelect,
    MatSelectModule,
} from '@angular/material/select';
import { NgIf } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDivider, MatDividerModule } from '@angular/material/divider';
import { FormsModule, NgForm } from '@angular/forms';
import { MatCard, MatCardContent } from '@angular/material/card';
import { MatSlideToggle } from '@angular/material/slide-toggle';

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
    styleUrl: 'timer.component.html',
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class TimerComponent implements OnDestroy, OnInit {
    @ViewChild('timerForm') private timerForm!: NgForm;
    private componentDestroyed$ = new Subject<void>();
    mainTopicsBasicData: MainTopicBasicData[] | undefined;

    timerBasicSettings: TimerBasicSettings = {
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
        private principalDataService: PrincipalDataService,
        private notificationService: NotificationService
    ) {}

    ngOnInit(): void {
        const { principalBasicData, mainTopicsBasicData } =
            this.principalDataService.getPrincipalMainTopicsBasicData();
        this.mainTopicsBasicData = mainTopicsBasicData;

        this.timerBasicSettings.timerSetHours =
            principalBasicData.timerSetHours;
        this.timerBasicSettings.timerSetMinutes =
            principalBasicData.timerSetMinutes;
        this.timerBasicSettings.timerSetSeconds =
            principalBasicData.timerSetSeconds;
        this.timerBasicSettings.timerShortBreak =
            principalBasicData.timerShortBreak;
        this.timerBasicSettings.timerLongBreak =
            principalBasicData.timerLongBreak;
        this.timerBasicSettings.timerAutoBreak =
            principalBasicData.timerAutoBreak;
        this.timerBasicSettings.timerInterval =
            principalBasicData.timerInterval;
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

    onSubmitSaveOrStart(option: string) {
        console.log(option);
        console.log(this.timerForm.form.value);

        if (this.timerForm.invalid) {
            return;
        }
    }
}
