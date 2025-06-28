import { Component } from '@angular/core';
import { CommandLineComponent } from '../command-line/command-line.component';
import { BottomMenuComponent } from '../bottom-menu/bottom-menu.component';
import { Pages } from '../../other/typesAndConsts';

// TODO dodać notyfikacje not implemented i skasować
@Component({
    selector: 'app-settings',
    standalone: true,
    imports: [CommandLineComponent, BottomMenuComponent],
    templateUrl: './settings.component.html',
})
export class SettingsComponent {
    protected readonly Pages = Pages;
}
