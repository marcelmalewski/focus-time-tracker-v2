import {
    Component,
    CUSTOM_ELEMENTS_SCHEMA,
    OnDestroy,
    OnInit,
    ViewChild,
} from '@angular/core';
import { CommandLineComponent } from '../command-line/command-line.component';
import { MatFormField, MatInput } from '@angular/material/input';
import { HttpResponse } from '@angular/common/http';
import { Subject, takeUntil } from 'rxjs';
import { Pages, Stage, Stages } from '../../other/typesAndConsts';
import { Router } from '@angular/router';
import { PrincipalDataService } from '../../service/principal-data.service';
import {
    AtLeastOneMessage,
    AtLeastZeroMessage,
    LessThanOrEqual59Message,
    LessThanOrEqual99Message,
    NotImplementedYet,
    TimerSettingsUpdated,
    UnknownServerErrorMessage,
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
import { BottomMenuComponent } from '../bottom-menu/bottom-menu.component';

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
        BottomMenuComponent,
    ],
    templateUrl: './timer-home.component.html',
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class TimerHomeComponent implements OnDestroy, OnInit {
    @ViewChild('timerForm') private timerForm!: NgForm;
    private componentDestroyed$ = new Subject<void>();

    protected readonly AtLeastOneMessage = AtLeastOneMessage;
    protected readonly LessThanOrEqual99Message = LessThanOrEqual99Message;
    protected readonly AtLeastZeroMessage = AtLeastZeroMessage;
    protected readonly LessThanOrEqual59Message = LessThanOrEqual59Message;

    mainTopicsBasicData: MainTopicBasicData[] | undefined;
    timerSettings: TimerSettings = TimerService.prepareDefaultTimerSettings();

    constructor(
        private router: Router,
        private timerService: TimerService,
        private principalDataService: PrincipalDataService,
        private notificationService: NotificationService
    ) {}

    ngOnInit(): void {
        const { principalBasicData, mainTopicsBasicData } =
            this.principalDataService.getPrincipalMainTopicsBasicData();

        this.mainTopicsBasicData = mainTopicsBasicData;
        this.timerSettings = TimerService.mapPrincipalBasicDataToTimerSettings(
            principalBasicData,
            Stages.HOME
        );
    }

    ngOnDestroy(): void {
        this.componentDestroyed$.next();
        this.componentDestroyed$.complete();
    }

    onSubmitStart() {
        if (this.timerForm.invalid) {
            return;
        }

        const body = this.prepareBodyForTimerSettingsUpdate(Stages.FOCUS);
        this.timerService
            .updatePrincipalTimerSettings(body)
            .pipe(takeUntil(this.componentDestroyed$))
            .subscribe({
                next: () => {
                    this.router.navigateByUrl(Pages.TIMER_FOCUS);
                },
                error: (_: HttpResponse<any>) => {
                    this.notificationService.openErrorNotification(
                        UnknownServerErrorMessage
                    );
                },
            });
    }

    onSubmitSave() {
        if (this.timerForm.invalid) {
            return;
        }

        const body = this.prepareBodyForTimerSettingsUpdate(Stages.HOME);
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

    private prepareBodyForTimerSettingsUpdate(stage: Stage) {
        return {
            timerStage: stage,
            timerSelectedTopic: this.timerSettings.timerSelectedTopic,
            timerSetHours: this.timerSettings.timerSetHours,
            timerSetMinutes: this.timerSettings.timerSetMinutes,
            timerSetSeconds: this.timerSettings.timerSetSeconds,
            timerShortBreak: this.timerSettings.timerShortBreak,
            timerLongBreak: this.timerSettings.timerLongBreak,
            timerAutoBreak: this.timerSettings.timerAutoBreak,
            timerInterval: this.timerSettings.timerInterval,
        };
    }

    onChangeToStopwatch() {
        this.notificationService.openErrorNotification(NotImplementedYet);
    }

    protected readonly Pages = Pages;
}
