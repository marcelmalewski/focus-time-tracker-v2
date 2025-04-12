import { Component } from '@angular/core';
import { CommandLineComponent } from '../command-line/command-line.component';

@Component({
    selector: 'app-timer-focus',
    standalone: true,
    templateUrl: './timer-focus.component.html',
    imports: [CommandLineComponent],
})
export class TimerFocusComponent {}
