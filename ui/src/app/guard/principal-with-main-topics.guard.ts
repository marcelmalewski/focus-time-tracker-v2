import { Injectable } from '@angular/core';
import {
    ActivatedRouteSnapshot,
    CanActivate,
    RouterStateSnapshot,
} from '@angular/router';
import { catchError, map, Observable, retry, tap } from 'rxjs';
import { PrincipalDataService } from '../service/principal-data.service';
import { PrincipalWithMainTopics } from '../spec/person-spec';

@Injectable({
    providedIn: 'root',
})
export class PrincipalWithMainTopicsGuard implements CanActivate {
    constructor(private principalDataService: PrincipalDataService) {}

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean> {
        return this.principalDataService
            .getOrLoadPrincipalWithMainTopics()
            .pipe(
                retry(3),
                tap((data: PrincipalWithMainTopics) => {
                    this.principalDataService.setPrincipalDataWithMainTopics(
                        data
                    );
                }),
                map(() => true),
                catchError(err =>
                    this.principalDataService.handleLoadPrincipalDataError(err)
                )
            );
    }
}
