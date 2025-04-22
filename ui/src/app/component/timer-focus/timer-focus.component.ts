import { Component, OnInit } from '@angular/core';
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
    AtLeastOneMessage,
    AtLeastZeroMessage,
    LessThanOrEqual59Message,
    LessThanOrEqual99Message,
} from '../../other/message';
import {
    MainTopicBasicData,
    TimerSettings,
} from '../../interface/person.interface';
import { TimerService } from '../../service/timer.service';
import { Router } from '@angular/router';
import { PrincipalDataService } from '../../service/principal-data.service';
import { NotificationService } from '../../service/notification.service';
import { MatCard, MatCardContent } from '@angular/material/card';

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
    ],
})
export class TimerFocusComponent implements OnInit {
    protected readonly Pages = Pages;
    protected readonly LessThanOrEqual99Message = LessThanOrEqual99Message;
    protected readonly AtLeastOneMessage = AtLeastOneMessage;
    protected readonly LessThanOrEqual59Message = LessThanOrEqual59Message;
    protected readonly AtLeastZeroMessage = AtLeastZeroMessage;

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
            Stages.FOCUS
        );
    }
}
