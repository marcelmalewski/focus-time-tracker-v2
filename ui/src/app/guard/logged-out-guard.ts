import { Injectable } from '@angular/core';
import {
    ActivatedRouteSnapshot,
    CanActivate,
    Router,
    RouterStateSnapshot,
} from '@angular/router';
import { catchError, map, Observable, of, retry } from 'rxjs';
import { Pages } from '../other/typesAndConsts';
import { GeneralActionsService } from '../service/general-actions.service';

@Injectable({
    providedIn: 'root',
})
export class LoggedOutGuard implements CanActivate {
    constructor(
        private generalActionsService: GeneralActionsService,
        private router: Router
    ) {}

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean> {
        return this.generalActionsService.getLoggedIn().pipe(
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
