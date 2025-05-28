import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Stage, Stages } from '../other/typesAndConsts';
import {
    PrincipalBasicData,
    TimerSettings,
    TimerStageAndRemaining,
} from '../interface/person.interface';

@Injectable({
    providedIn: 'root',
})
export class TimerService {
    headers = new HttpHeaders().set('content-type', 'application/json');

    constructor(private http: HttpClient) {}

    updatePrincipalTimerSettings(body: TimerSettings): Observable<any> {
        return this.http.put('/api/v1/persons/principal/timer/settings', body, {
            headers: this.headers,
        });
    }

    updatePrincipalTimerStageAndRemainingTime(
        body: TimerStageAndRemaining
    ): Observable<any> {
        return this.http.put('/api/v1/persons/principal/timer/pause', body, {
            headers: this.headers,
        });
    }

    static prepareDefaultTimerSettings(): TimerSettings {
        return {
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
    }

    static mapPrincipalBasicDataToTimerSettings(
        principalBasicData: PrincipalBasicData,
        stage: Stage
    ): TimerSettings {
        return {
            timerStage: stage,
            timerSelectedTopic: principalBasicData.timerSelectedTopic,
            timerSetHours: principalBasicData.timerSetHours,
            timerSetMinutes: principalBasicData.timerSetMinutes,
            timerSetSeconds: principalBasicData.timerSetSeconds,
            timerShortBreak: principalBasicData.timerShortBreak,
            timerLongBreak: principalBasicData.timerLongBreak,
            timerAutoBreak: principalBasicData.timerAutoBreak,
            timerInterval: principalBasicData.timerInterval,
        };
    }

    static prepareBodyForTimerSettingsUpdate(
        timerSettings: TimerSettings,
        stage: Stage
    ) {
        return {
            timerStage: stage,
            timerSelectedTopic: timerSettings.timerSelectedTopic,
            timerSetHours: timerSettings.timerSetHours,
            timerSetMinutes: timerSettings.timerSetMinutes,
            timerSetSeconds: timerSettings.timerSetSeconds,
            timerShortBreak: timerSettings.timerShortBreak,
            timerLongBreak: timerSettings.timerLongBreak,
            timerAutoBreak: timerSettings.timerAutoBreak,
            timerInterval: timerSettings.timerInterval,
        };
    }
}
