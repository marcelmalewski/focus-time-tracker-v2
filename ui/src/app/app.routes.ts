import { Routes } from '@angular/router';
import { LoginComponent } from './component/login/login.component';
import { TimerHomeComponent } from './component/timer-home/timer-home.component';
import { RegisterComponent } from './component/register/register.component';
import { SettingsComponent } from './component/settings/settings.component';
import { PrincipalBasicDataGuard } from './guard/principal-basic-data.guard';
import { UnknownErrorComponent } from './component/unknown-error/unknown-error.component';
import { NotLoggedInMessage } from './other/message';
import { LoggedOutGuard } from './guard/logged-out-guard';
import { PrincipalWithMainTopicsGuard } from './guard/principal-with-main-topics.guard';
import { TimerFocusComponent } from './component/timer-focus/timer-focus.component';

export const routes: Routes = [
    {
        path: 'login',
        title: 'login',
        component: LoginComponent,
        canActivate: [LoggedOutGuard],
    },
    {
        path: 'register',
        title: 'register',
        component: RegisterComponent,
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
        canActivate: [PrincipalWithMainTopicsGuard],
    },
    {
        path: 'settings',
        title: 'settings',
        component: SettingsComponent,
        canActivate: [PrincipalBasicDataGuard],
    },
    {
        path: 'unknown-error',
        title: 'unknown-error',
        component: UnknownErrorComponent,
    },
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    { path: '**', component: LoginComponent }, //TODO add page not found
];
