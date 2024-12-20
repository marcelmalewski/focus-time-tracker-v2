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

@Injectable({
  providedIn: 'root',
})
export class PrincipalBasicDataGuard implements CanActivate {
  constructor(
    private principalDataService: PrincipalDataService,
    private router: Router
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
        console.log('test');
        if (err.status === 401) {
          console.log('not logged in');
          this.router.navigateByUrl(Pages.LOGIN);
        } else {
          // TODO jak obsluguje 500 itd.
          this.router.navigateByUrl(Pages.LOGIN);
        }

        return of(false);
      })
    );
  }
}
