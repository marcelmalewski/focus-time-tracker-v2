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
    MainTopicBasicData,
    TimerSettings,
} from '../../interface/person.interface';
import { TimerService } from '../../service/timer.service';
import { Router } from '@angular/router';
import { PrincipalDataService } from '../../service/principal-data.service';
import { NotificationService } from '../../service/notification.service';
import { MatCard, MatCardContent } from '@angular/material/card';
import { TimerFieldPipe } from '../../pipes/timer-field.pipe';

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
    protected readonly Pages = Pages;

    mainTopicsBasicData!: MainTopicBasicData[];
    timerSettings!: TimerSettings;
    countDownId: any | undefined;

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
            principalBasicData.timerStage
        );

        this.countDownId = setInterval(() => {
            this.countDownLogic();
        }, 1000);
    }

    ngOnDestroy() {
        if (this.countDownId) {
            clearInterval(this.countDownId);
        }
    }

    private countDownLogic() {
        if (this.timerSettings.timerSetSeconds > 0) {
            this.timerSettings.timerSetSeconds =
                this.timerSettings.timerSetSeconds - 1;
        } else {
            this.countDownLogicOnTimerSetSecondsIsZero();
        }
    }

    private countDownLogicOnTimerSetSecondsIsZero() {
        if (this.timerSettings.timerSetMinutes > 0) {
            this.timerSettings.timerSetMinutes =
                this.timerSettings.timerSetMinutes - 1;
            this.timerSettings.timerSetSeconds = 59;
        } else {
            this.countDownLogicOnTimerSetMinutesIsZero();
        }
    }

    private countDownLogicOnTimerSetMinutesIsZero() {
        if (this.timerSettings.timerSetHours > 0) {
            this.timerSettings.timerSetHours =
                this.timerSettings.timerSetHours - 1;
            this.timerSettings.timerSetMinutes = 59;
            this.timerSettings.timerSetSeconds = 59;
        }
    }
}
