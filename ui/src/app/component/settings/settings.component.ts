import { Component } from '@angular/core';
import { CommandLineComponent } from '../command-line/command-line.component';
import { BottomMenuComponent } from '../bottom-menu/bottom-menu.component';
import { Pages } from '../../other/typesAndConsts';

@Component({
    selector: 'app-settings',
    standalone: true,
    imports: [CommandLineComponent, BottomMenuComponent],
    templateUrl: './settings.component.html',
})
export class SettingsComponent {
    protected readonly Pages = Pages;
}
