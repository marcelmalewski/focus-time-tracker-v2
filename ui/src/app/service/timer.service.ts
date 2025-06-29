import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Stage, Stages } from '../other/typesAndConsts';
import {
    PrincipalBasicData,
    TimerSettings,
    TimerCurrentTime,
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

    principalTimerFocus(body: TimerSettings): Observable<number> {
        return this.http.put<number>(
            '/api/v1/persons/principal/timer/focus',
            body,
            {
                headers: this.headers,
            }
        );
    }

    principalTimerPause(body: TimerCurrentTime): Observable<number> {
        return this.http.put<number>(
            '/api/v1/persons/principal/timer/pause',
            body,
            {
                headers: this.headers,
            }
        );
    }

    principalTimerBreak(body: any): Observable<any> {
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
        };
    }

    static mapToTimerSettings(
        timerSettings: TimerSettings | PrincipalBasicData,
        stage: Stage
    ): TimerSettings {
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
