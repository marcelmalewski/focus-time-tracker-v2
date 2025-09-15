import { Component, OnInit } from '@angular/core';
import { CommandLineComponent } from '../command-line/command-line.component';
import { BottomMenuComponent } from '../bottom-menu/bottom-menu.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatCard, MatCardContent } from '@angular/material/card';
import { NgIf } from '@angular/common';
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
import { PrincipalDataService } from '../../service/principal-data.service';
import { TimerFieldPipe } from '../../pipes/timer-field.pipe';
import { PrincipalBasicData } from '../../spec/person-spec';
import { Pages, Stages } from '../../spec/common-spec';
import { TimerCurrentBreakTime } from '../../spec/timer-spec';
import { TimerService } from '../../service/timer.service';

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
export class TimerBreakComponent implements OnInit {
    readonly Pages = Pages;

    principalBasicData!: PrincipalBasicData;
    timerCurrentTime!: TimerCurrentBreakTime;
    countDownId: any | undefined;

    constructor(
        private principalDataService: PrincipalDataService,
        private timerService: TimerService
    ) {}

    ngOnInit(): void {
        this.principalBasicData =
            this.principalDataService.getPrincipalBasicData();

        this.timerService.matchPageWithStage(
            Pages.TIMER_FOCUS,
            this.principalBasicData.timerStage
        );

        const timerCurrentTimeInMinutes =
            this.principalBasicData.timerStage === Stages.SHORT_BREAK
                ? this.principalBasicData.timerShortBreak
                : this.principalBasicData.timerLongBreak;
        this.timerCurrentTime = {
            timerCurrentMinute: timerCurrentTimeInMinutes,
            timerCurrentSecond: 0,
        };
        this.countDownId = setInterval(() => {
            this.countDownLogic();
        }, 1000);
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
            this.timerCurrentTime.timerCurrentMinute = 59;
            this.timerCurrentTime.timerCurrentSecond = 59;
        }
    }
}
