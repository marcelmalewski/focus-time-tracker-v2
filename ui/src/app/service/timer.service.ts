import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Stage, Stages } from '../other/typesAndConsts';
import {
    PrincipalBasicData,
    TimerSettings,
    TimerPause,
} from '../interface/person.interface';

@Injectable({
    providedIn: 'root',
})
export class TimerService {
    headers = new HttpHeaders().set('content-type', 'application/json');

    constructor(private http: HttpClient) {}

    updatePrincipalTimerSettings(body: TimerSettings): Observable<any> {
        return this.http.put('/api/v1/persons/principal/timer', body, {
            headers: this.headers,
        });
    }

    principalTimerPause(body: TimerPause): Observable<any> {
        return this.http.put('/api/v1/persons/principal/timer/pause', body, {
            headers: this.headers,
        });
    }

    principalTimerBreak(body: TimerPause): Observable<any> {
        return this.http.put('/api/v1/persons/principal/timer/break', body, {
            headers: this.headers,
        });
    }

    updatePrincipalTimerStage(timerStage: Stage): Observable<any> {
        return this.http.put(
            '/api/v1/persons/principal/timer/stage',
            { timerStage },
            {
                headers: this.headers,
            }
        );
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
            timerRemainingTime: 0,
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
            timerRemainingTime: principalBasicData.timerRemainingTime,
        };
    }

    static prepareBodyForTimerSettingsUpdate(
        timerSettings: TimerSettings,
        stage: Stage,
        timerRemainingTime: number
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
            timerRemainingTime: timerRemainingTime,
        };
    }

    static calculateRemainingTime(
        hours: number,
        minutes: number,
        seconds: number
    ) {
        return hours * 60 * 60 + minutes * 60 + seconds;
    }
}
