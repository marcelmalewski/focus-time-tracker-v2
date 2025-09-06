import { Injectable } from '@angular/core';
import {
    ActivatedRouteSnapshot,
    CanActivate,
    RouterStateSnapshot,
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { PrincipalDataService } from '../service/principal-data.service';

@Injectable({
    providedIn: 'root',
})
export class StageMatchViewGuard implements CanActivate {
    constructor(private principalDataService: PrincipalDataService) {}

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean> {
        return of(true);
    }
}
