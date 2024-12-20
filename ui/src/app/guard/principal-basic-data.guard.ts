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
import { MatSnackBar } from '@angular/material/snack-bar';
import { NotLoggedIn } from '../other/message';
import { NotLoggedInNotificationComponent } from '../other/not-logged-in-snack-bar/not-logged-in-notification.component';

@Injectable({
  providedIn: 'root',
})
export class PrincipalBasicDataGuard implements CanActivate {
  constructor(
    private principalDataService: PrincipalDataService,
    private router: Router,
    private snackBar: MatSnackBar
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
          this.openBasicNotification(NotLoggedIn);
          this.router.navigateByUrl(Pages.LOGIN);
        } else {
          console.log('not known error');
          // TODO jak obsluguje 500 itd.
          this.router.navigateByUrl(Pages.LOGIN);
        }

        return of(false);
      })
    );
  }

  // openBasicNotification() {
  //   this.snackBar.openFromComponent(NotLoggedInNotificationComponent, {
  //     duration: 2000,
  //   });
  // }

  openBasicNotification(message: string) {
    this.snackBar.open(message, undefined, {
      duration: 2000,
      panelClass: ['notification-error'],
    });
  }
}
