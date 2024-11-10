import { Routes } from '@angular/router';
import { LoginComponent } from './component/login/login.component';
import { TimerComponent } from './component/timer/timer.component';

export const routes: Routes = [
  { path: 'home', title: 'home', component: TimerComponent },
  { path: 'login', title: 'login', component: LoginComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', component: LoginComponent }, //TODO add page not found
];
