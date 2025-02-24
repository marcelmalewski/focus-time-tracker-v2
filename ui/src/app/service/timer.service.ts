import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

@Injectable({
    providedIn: 'root',
})
export class TimerService {
    headers = new HttpHeaders().set('content-type', 'application/json');

    constructor(private http: HttpClient) {}

    updatePrincipalTimerSettings(body: Object): Observable<any> {
        return this.http.put('/api/v1/persons/principal/timer/settings', body, {
            headers: this.headers,
        });
    }
}
