import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { PrincipalBasicData } from '../interface/person.interface';

@Injectable({
    providedIn: 'root',
})
export class PrincipalDataService {
    private principalBasicData: PrincipalBasicData | undefined;

    constructor(private http: HttpClient) {}

    getOrLoadPrincipalBasicData(): Observable<PrincipalBasicData> {
        if (this.principalBasicData !== undefined)
            return of(this.principalBasicData);

        return this.http.get<PrincipalBasicData>(
            '/api/v1/persons/principal/basic-data'
        );
    }

    getPrincipalBasicData(): PrincipalBasicData {
        return this.principalBasicData!;
    }

    setPrincipalBasicData(data: PrincipalBasicData | undefined) {
        this.principalBasicData = data;
    }
}
