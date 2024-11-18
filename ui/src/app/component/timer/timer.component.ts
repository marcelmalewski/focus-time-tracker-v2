import { Component, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommandLineComponent } from '../command-line/command-line.component';
import { MatFormField, MatInput } from '@angular/material/input';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommandLineComponent, MatInput, MatFormField],
  templateUrl: './timer.component.html',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class TimerComponent {}
