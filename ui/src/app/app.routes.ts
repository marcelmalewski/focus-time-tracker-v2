import { Routes } from '@angular/router';
import { LoginComponent } from './component/login/login.component';
import { TimerHomeComponent } from './component/timer-home/timer-home.component';
import { UnknownErrorComponent } from './component/unknown-error/unknown-error.component';
import { LoggedOutGuard } from './guard/logged-out-guard';
import { PrincipalWithMainTopicsGuard } from './guard/principal-with-main-topics.guard';
import { TimerFocusComponent } from './component/timer-focus/timer-focus.component';
import { PageNotFoundComponent } from './component/page-not-found/page-not-found.component';
import { PrincipalBasicDataGuard } from './guard/principal-basic-data.guard';
import { TimerBreakComponent } from './component/timer-break/timer-break.component';

export const routes: Routes = [
    {
        path: 'login',
        title: 'login',
        component: LoginComponent,
        canActivate: [LoggedOutGuard],
    },
    {
        path: 'timer',
        title: 'timer-home',
        component: TimerHomeComponent,
        canActivate: [PrincipalWithMainTopicsGuard],
    },
    {
        path: 'timer/focus',
        title: 'timer-focus',
        component: TimerFocusComponent,
        canActivate: [PrincipalBasicDataGuard],
    },
    {
        path: 'timer/break',
        title: 'timer-break',
        component: TimerBreakComponent,
        canActivate: [PrincipalBasicDataGuard],
    },
    {
        path: 'unknown-error',
        title: 'unknown-error',
        component: UnknownErrorComponent,
    },
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    { path: '**', component: PageNotFoundComponent },
];
