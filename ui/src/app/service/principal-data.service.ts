import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import {
    MainTopicBasicData,
    PrincipalBasicData,
    PrincipalWithMainTopics,
    TimerSettings,
    TimerRemainingTime,
} from '../interface/person.interface';
import {
    NotLoggedInMessage,
    UnknownServerErrorMessageRefreshPage,
} from '../other/message';
import { Pages, Stage } from '../other/typesAndConsts';
import { NotificationService } from './notification.service';
import { Router } from '@angular/router';

@Injectable({
    providedIn: 'root',
})
export class PrincipalDataService {
    private principalBasicData: PrincipalBasicData | undefined;
    private mainTopicsBasicData: MainTopicBasicData[] | undefined;

    constructor(
        private http: HttpClient,
        private router: Router,
        private notificationService: NotificationService
    ) {}

    getOrLoadPrincipalBasicData(): Observable<PrincipalBasicData> {
        if (this.principalBasicData !== undefined)
            return of(this.principalBasicData);

        return this.http.get<PrincipalBasicData>(
            '/api/v1/persons/principal/basic-data'
        );
    }

    setPrincipalBasicData(data: PrincipalBasicData) {
        this.principalBasicData = data;
    }

    getPrincipalMainTopicsBasicData(): PrincipalWithMainTopics {
        return {
            principalBasicData: this.principalBasicData!,
            mainTopicsBasicData: this.mainTopicsBasicData!,
        };
    }

    getOrLoadPrincipalWithMainTopics(): Observable<PrincipalWithMainTopics> {
        if (this.mainTopicsBasicData !== undefined)
            return of({
                principalBasicData: this.principalBasicData!,
                mainTopicsBasicData: this.mainTopicsBasicData,
            });

        return this.http.get<PrincipalWithMainTopics>(
            '/api/v1/persons/principal/with-main-topics'
        );
    }

    setPrincipalDataWithMainTopics(data: PrincipalWithMainTopics) {
        this.principalBasicData = data.principalBasicData;
        this.mainTopicsBasicData = data.mainTopicsBasicData;
    }

    updateTimerSettings(timerSettings: TimerSettings) {
        this.principalBasicData!.timerStage = timerSettings.timerStage;
        this.principalBasicData!.timerSelectedTopic =
            timerSettings.timerSelectedTopic;
        this.principalBasicData!.timerSetHours = timerSettings.timerSetHours;
        this.principalBasicData!.timerSetMinutes =
            timerSettings.timerSetMinutes;
        this.principalBasicData!.timerSetSeconds =
            timerSettings.timerSetSeconds;
        this.principalBasicData!.timerShortBreak =
            timerSettings.timerShortBreak;
        this.principalBasicData!.timerLongBreak = timerSettings.timerLongBreak;
        this.principalBasicData!.timerAutoBreak = timerSettings.timerAutoBreak;
        this.principalBasicData!.timerInterval = timerSettings.timerInterval;
        this.principalBasicData!.timerRemainingTime =
            timerSettings.timerRemainingTime;
    }

    updateTimerAfterPause(timerStageAndRemaining: TimerRemainingTime) {
        this.principalBasicData!.timerRemainingFocus =
            timerStageAndRemaining.timerRemainingFocus;
    }

    updateTimerStage(timerStage: Stage) {
        this.principalBasicData!.timerStage = timerStage;
    }

    clearPrincipalData() {
        this.principalBasicData = undefined;
        this.mainTopicsBasicData = undefined;
    }

    handleLoadPrincipalDataError(err: any) {
        if (err.status === 401) {
            this.notificationService.openErrorNotification(NotLoggedInMessage);
            this.router.navigateByUrl(Pages.LOGIN);
        } else {
            this.notificationService.openErrorNotification(
                UnknownServerErrorMessageRefreshPage
            );
            this.router.navigateByUrl(Pages.UNKNOWN_ERROR, {
                skipLocationChange: true,
            });
        }

        return of(false);
    }
}
