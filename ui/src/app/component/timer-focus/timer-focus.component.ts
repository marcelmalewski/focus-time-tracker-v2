import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommandLineComponent } from '../command-line/command-line.component';
import { BottomMenuComponent } from '../bottom-menu/bottom-menu.component';
import { Pages, Stages } from '../../other/typesAndConsts';
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
    TimerCurrentTime,
    TimerSettings,
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
    protected readonly Stages = Stages;

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

        this.timerSettings = TimerService.mapToTimerSettings(
            principalBasicData,
            principalBasicData.timerStage
        );

        const { timerCurrentHour, timerCurrentMinute, timerCurrentSecond } =
            this.extractCurrentFocusTime(
                principalBasicData.timerRemainingFocus
            );
        this.timerCurrentTime = {
            timerCurrentHour,
            timerCurrentMinute,
            timerCurrentSecond,
        };

        if (this.timerSettings.timerStage === Stages.FOCUS) {
            this.countDownId = setInterval(() => {
                this.countDownLogic();
            }, 1000);
        }
    }

    private extractCurrentFocusTime(remainingTime: number) {
        const allMinutes = Math.floor(remainingTime / 60);
        const timerCurrentSecond = remainingTime - allMinutes * 60;

        const timerCurrentHour = Math.floor(allMinutes / 60);
        const timerCurrentMinute = allMinutes - timerCurrentHour * 60;

        return {
            timerCurrentHour,
            timerCurrentMinute,
            timerCurrentSecond,
        };
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
        this.timerService
            .principalTimerPause(this.timerCurrentTime)
            .pipe(takeUntil(this.componentDestroyed$))
            .subscribe({
                next: timerRemainingFocus => {
                    this.principalDataService.localUpdateTimerRemainingFocus(
                        timerRemainingFocus
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

    onResume() {
        this.timerService
            .updatePrincipalTimerStage(Stages.FOCUS)
            .pipe(takeUntil(this.componentDestroyed$))
            .subscribe({
                next: () => {
                    this.principalDataService.localUpdateTimerStage(
                        Stages.FOCUS
                    );
                    this.timerSettings.timerStage = Stages.FOCUS;
                    this.countDownId = setInterval(() => {
                        this.countDownLogic();
                    }, 1000);
                },
                error: (_: HttpResponse<any>) => {
                    this.notificationService.openErrorNotification(
                        UnknownServerErrorMessage
                    );
                },
            });
    }

    onShortBreak() {}

    onHome() {}
}
