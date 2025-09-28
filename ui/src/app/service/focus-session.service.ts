import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

@Injectable({
    providedIn: 'root',
})
export class FocusSessionService {
    headers = new HttpHeaders().set('content-type', 'application/json');

    constructor(private http: HttpClient) {}

    getPrincipalAllFocusSessions(page: number, size: number): Observable<any> {
        let params = new HttpParams().set('page', page).set('size', size);

        return this.http.get('api/v1/focus-sessions/principal', {
            headers: this.headers,
            params: params,
        });
    }
}
