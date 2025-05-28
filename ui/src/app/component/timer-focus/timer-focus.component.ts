import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommandLineComponent } from '../command-line/command-line.component';
import { BottomMenuComponent } from '../bottom-menu/bottom-menu.component';
import { Pages, Stage, Stages } from '../../other/typesAndConsts';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {
    MatError,
    MatFormField,
    MatLabel,
    MatSuffix,
} from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatOption } from '@angular/material/core';
import { MatSelect } from '@angular/material/select';
import { MatSlideToggle } from '@angular/material/slide-toggle';
import { NgIf } from '@angular/common';
import {
    MainTopicBasicData,
    TimerCurrentTime,
    TimerSettings,
    TimerStageAndRemaining,
} from '../../interface/person.interface';
import { TimerService } from '../../service/timer.service';
import { Router } from '@angular/router';
import { PrincipalDataService } from '../../service/principal-data.service';
import { NotificationService } from '../../service/notification.service';
import { MatCard, MatCardContent } from '@angular/material/card';
import { TimerFieldPipe } from '../../pipes/timer-field.pipe';
import { Subject, takeUntil } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { UnknownServerErrorMessage } from '../../other/message';

@Component({
    selector: 'app-timer-focus',
    standalone: true,
    templateUrl: './timer-focus.component.html',
    imports: [
        CommandLineComponent,
        BottomMenuComponent,
        FormsModule,
        MatError,
        MatFormField,
        MatInput,
        MatLabel,
        MatOption,
        MatSelect,
        MatSlideToggle,
        MatSuffix,
        NgIf,
        ReactiveFormsModule,
        MatCard,
        MatCardContent,
        TimerFieldPipe,
    ],
})
export class TimerFocusComponent implements OnInit, OnDestroy {
    private componentDestroyed$ = new Subject<void>();
    protected readonly Pages = Pages;

    timerSettings!: TimerSettings;
    timerCurrentTime!: TimerCurrentTime;
    countDownId: any | undefined;

    constructor(
        private router: Router,
        private timerService: TimerService,
        private principalDataService: PrincipalDataService,
        private notificationService: NotificationService
    ) {}

    ngOnInit(): void {
        const { principalBasicData } =
            this.principalDataService.getPrincipalMainTopicsBasicData();

        this.timerSettings = TimerService.mapPrincipalBasicDataToTimerSettings(
            principalBasicData,
            principalBasicData.timerStage
        );

        if (this.timerSettings.timerStage === Stages.FOCUS) {
            // trzeba uzyc remainig time
            this.timerCurrentTime = {
                timerCurrentHour: this.timerSettings.timerSetHours,
                timerCurrentMinute: this.timerSettings.timerSetMinutes,
                timerCurrentSecond: this.timerSettings.timerSetSeconds,
            };

            this.countDownId = setInterval(() => {
                this.countDownLogic();
            }, 1000);
        } else {
            // trzeba uzyc remainig time
            this.timerCurrentTime = {
                timerCurrentHour: this.timerSettings.timerSetHours,
                timerCurrentMinute: this.timerSettings.timerSetMinutes,
                timerCurrentSecond: this.timerSettings.timerSetSeconds,
            };
        }
    }

    ngOnDestroy() {
        if (this.countDownId) {
            clearInterval(this.countDownId);
        }
        this.componentDestroyed$.next();
        this.componentDestroyed$.complete();
    }

    private countDownLogic() {
        if (this.timerCurrentTime.timerCurrentSecond > 0) {
            this.timerCurrentTime.timerCurrentSecond =
                this.timerCurrentTime.timerCurrentSecond - 1;
        } else {
            this.countDownLogicOnTimerSetSecondsIsZero();
        }
    }

    private countDownLogicOnTimerSetSecondsIsZero() {
        if (this.timerCurrentTime.timerCurrentMinute > 0) {
            this.timerCurrentTime.timerCurrentMinute =
                this.timerCurrentTime.timerCurrentMinute - 1;
            this.timerCurrentTime.timerCurrentSecond = 59;
        } else {
            this.countDownLogicOnTimerSetMinutesIsZero();
        }
    }

    private countDownLogicOnTimerSetMinutesIsZero() {
        if (this.timerCurrentTime.timerCurrentHour > 0) {
            this.timerCurrentTime.timerCurrentHour =
                this.timerCurrentTime.timerCurrentHour - 1;
            this.timerCurrentTime.timerCurrentMinute = 59;
            this.timerCurrentTime.timerCurrentSecond = 59;
        }
    }

    onPause() {
        clearInterval(this.countDownId);

        const timerRemainingTime =
            this.timerCurrentTime.timerCurrentHour * 60 * 60 +
            this.timerCurrentTime.timerCurrentMinute * 60 +
            this.timerCurrentTime.timerCurrentSecond;
        const body: TimerStageAndRemaining = {
            timerStage: Stages.PAUSE,
            timerRemainingTime: timerRemainingTime,
        };

        this.timerService
            .updatePrincipalTimerStageAndRemainingTime(body)
            .pipe(takeUntil(this.componentDestroyed$))
            .subscribe({
                next: () => {
                    this.principalDataService.updateTimerStageAndRemainingTime(
                        body
                    );
                    this.timerSettings.timerStage = Stages.PAUSE;
                },
                error: (_: HttpResponse<any>) => {
                    this.notificationService.openErrorNotification(
                        UnknownServerErrorMessage
                    );

                    this.countDownId = setInterval(() => {
                        this.countDownLogic();
                    }, 1000);
                },
            });
    }

    onHome() {}

    protected readonly Stages = Stages;
}
