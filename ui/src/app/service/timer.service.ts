import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {
    PrincipalBasicData,
    TimerCurrentTime,
    TimerSettings,
} from '../spec/person-spec';
import { Page, Stage, Stages, StageToPage } from '../spec/common-spec';
import { Router } from '@angular/router';

@Injectable({
    providedIn: 'root',
})
export class TimerService {
    headers = new HttpHeaders().set('content-type', 'application/json');

    constructor(
        private http: HttpClient,
        private router: Router
    ) {}

    matchPageWithStage(currentPage: Page, currentStage: Stage) {
        const expectedCurrentPage = StageToPage[currentStage];
        if (currentPage === expectedCurrentPage) {
            return;
        }

        this.router.navigateByUrl(expectedCurrentPage);
    }

    updatePrincipalTimerSettings(body: TimerSettings): Observable<any> {
        return this.http.put('/api/v1/persons/principal/timer', body, {
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

    principalMoveTimerToStageFocus(body: TimerSettings): Observable<number> {
        return this.http.put<number>(
            '/api/v1/persons/principal/timer/focus',
            body,
            {
                headers: this.headers,
            }
        );
    }

    principalMoveTimerBackToStageHome(): Observable<any> {
        return this.http.put<number>(
            '/api/v1/persons/principal/timer/home-after-focus',
            {
                headers: this.headers,
            }
        );
    }

    principalMoveTimerToStagePause(body: TimerCurrentTime): Observable<number> {
        return this.http.put<number>(
            '/api/v1/persons/principal/timer/pause',
            body,
            {
                headers: this.headers,
            }
        );
    }

    principalMoveTimerToStageBreak(body: any): Observable<any> {
        return this.http.put('/api/v1/persons/principal/timer/break', body, {
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

    static mapToTimerSettings(
        timerSettings: TimerSettings | PrincipalBasicData
    ): TimerSettings {
        return {
            timerStage: timerSettings.timerStage,
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
