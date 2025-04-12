import { Component } from '@angular/core';
import { CommandLineComponent } from '../command-line/command-line.component';
import { BottomMenuComponent } from '../bottom-menu/bottom-menu.component';
import { Pages } from '../../other/typesAndConsts';

@Component({
    selector: 'app-timer-focus',
    standalone: true,
    templateUrl: './timer-focus.component.html',
    imports: [CommandLineComponent, BottomMenuComponent],
})
export class TimerFocusComponent {
    protected readonly Pages = Pages;
}
