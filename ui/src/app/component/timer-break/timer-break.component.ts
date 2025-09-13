import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommandLineComponent } from '../command-line/command-line.component';
import { BottomMenuComponent } from '../bottom-menu/bottom-menu.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Subject } from 'rxjs';
import { MatCard, MatCardContent } from '@angular/material/card';
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
import { TimerService } from '../../service/timer.service';
import { Router } from '@angular/router';
import { PrincipalDataService } from '../../service/principal-data.service';
import { NotificationService } from '../../service/notification.service';
import { TimerFieldPipe } from '../../pipes/timer-field.pipe';
import { TimerSettings } from '../../spec/person-spec';
import { Pages } from '../../spec/common-spec';
import { NgIf } from '@angular/common';
import { TimerCurrentTime } from '../../spec/timer-spec';

@Component({
    selector: 'app-timer-break',
    standalone: true,
    templateUrl: './timer-break.component.html',
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
export class TimerBreakComponent implements OnInit, OnDestroy {
    private componentDestroyed$ = new Subject<void>();
    readonly Pages = Pages;

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
            this.principalDataService.getPrincipalWithMainTopics();

        this.timerSettings =
            TimerService.mapToTimerSettings(principalBasicData);
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
}
