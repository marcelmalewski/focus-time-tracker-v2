import { Injectable } from '@angular/core';
import {
    ActivatedRouteSnapshot,
    CanActivate,
    RouterStateSnapshot,
} from '@angular/router';
import { catchError, map, Observable, retry, tap } from 'rxjs';
import { PrincipalDataService } from '../service/principal-data.service';
import { PrincipalBasicData } from '../spec/person-spec';

@Injectable({
    providedIn: 'root',
})
export class PrincipalBasicDataGuard implements CanActivate {
    constructor(private principalDataService: PrincipalDataService) {}

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean> {
        return this.principalDataService.getOrLoadPrincipalBasicData().pipe(
            retry(3),
            tap((result: PrincipalBasicData) => {
                this.principalDataService.setPrincipalBasicData(result);
            }),
            map(() => true),
            catchError(err =>
                this.principalDataService.handleLoadPrincipalDataError(err)
            )
        );
    }
}
