import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
    providedIn: 'root',
})
export class GeneralActionsService {
    constructor(private http: HttpClient) {}

    updatePrincipalTimerSettings(payload: HttpParams): Observable<any> {
        return this.http.post('/api/timer/settings', payload);
    }
}
