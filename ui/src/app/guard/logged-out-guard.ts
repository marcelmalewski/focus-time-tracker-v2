import { Injectable } from '@angular/core';
import {
    ActivatedRouteSnapshot,
    CanActivate,
    Router,
    RouterStateSnapshot,
} from '@angular/router';
import { catchError, map, Observable, of, retry, tap } from 'rxjs';
import { PrincipalDataService } from '../service/principal-data.service';
import { Pages } from '../other/typesAndConsts';

@Injectable({
    providedIn: 'root',
})
export class LoggedOutGuard implements CanActivate {
    constructor(
        private principalDataService: PrincipalDataService,
        private router: Router
    ) {}

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean> {
        return this.principalDataService.getLoggedIn().pipe(
            retry(3),
            map(loggedIn => {
                if (loggedIn) {
                    this.router.navigateByUrl(Pages.TIMER);
                    return true;
                }

                return true;
            }),
            catchError(() => {
                return of(true);
            })
        );
    }
}
