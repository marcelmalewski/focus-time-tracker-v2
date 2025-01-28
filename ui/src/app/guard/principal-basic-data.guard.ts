import { Injectable } from '@angular/core';
import {
    ActivatedRouteSnapshot,
    CanActivate,
    RouterStateSnapshot,
} from '@angular/router';
import { catchError, map, Observable, of, retry, tap } from 'rxjs';
import { PrincipalDataService } from '../service/principal-data.service';
import { PrincipalBasicData } from '../interface/person.interface';

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
            tap((data: PrincipalBasicData) => {
                this.principalDataService.setPrincipalBasicData(data);
            }),
            map(() => true),
            catchError(err =>
                this.principalDataService.handleLoadPrincipalDataError(err)
            )
        );
    }
}
