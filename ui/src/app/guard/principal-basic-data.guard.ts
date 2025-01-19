import { Injectable } from '@angular/core';
import {
    ActivatedRouteSnapshot,
    CanActivate,
    Router,
    RouterStateSnapshot,
} from '@angular/router';
import { catchError, map, Observable, of, retry, tap } from 'rxjs';
import { PrincipalDataService } from '../service/principal-data.service';
import { PrincipalBasicData } from '../interface/person.interface';
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
    constructor(
        private router: Router,
        private principalDataService: PrincipalDataService,
        private notificationService: NotificationService
    ) {}

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean> {
        return this.principalDataService.getPrincipalBasicData().pipe(
            retry(3),
            tap((data: PrincipalBasicData) => {
                this.principalDataService.setPrincipalBasicData(data);
            }),
            map(() => true),
            catchError(err => {
                if (err.status === 401) {
                    this.notificationService.openErrorNotification(
                        NotLoggedInMessage
                    );
                    this.router.navigateByUrl(Pages.LOGIN);
                } else {
                    this.notificationService.openErrorNotification(
                        UnknownServerErrorMessage
                    );
                    this.router.navigateByUrl(Pages.UNKNOWN_ERROR, {
                        skipLocationChange: true,
                    });
                }

                return of(false);
            })
        );
    }
}
