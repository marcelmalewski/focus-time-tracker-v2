import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
    providedIn: 'root',
})
export class GeneralActionsService {
    constructor(private http: HttpClient) {}

    getLoggedIn(): Observable<boolean> {
        return this.http.get<boolean>('/api/v1/persons/principal/logged-in');
    }

    login(payload: HttpParams): Observable<any> {
        return this.http.post('/api/login', payload);
    }

    logout(): Observable<any> {
        return this.http.post('/api/logout', null);
    }
}
