import { Injectable } from '@angular/core';
import {
    ActivatedRouteSnapshot,
    CanActivate,
    Router,
    RouterStateSnapshot,
} from '@angular/router';
import { catchError, map, Observable, of, retry, tap } from 'rxjs';
import { PrincipalDataService } from '../service/principal-data.service';
import {
    PrincipalBasicData,
    PrincipalWithMainTopics,
} from '../interface/person.interface';
import { Pages } from '../other/typesAndConsts';
import {
    NotLoggedInMessage,
    UnknownServerErrorMessage,
} from '../other/message';
import { NotificationService } from '../service/notification.service';

@Injectable({
    providedIn: 'root',
})
export class PrincipalBasicDataGuard implements CanActivate {
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
