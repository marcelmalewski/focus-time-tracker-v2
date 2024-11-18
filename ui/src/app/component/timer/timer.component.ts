import { Component } from '@angular/core';
import { CommandLineComponent } from '../command-line/command-line.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommandLineComponent],
  templateUrl: './timer.component.html',
})
export class TimerComponent {}
