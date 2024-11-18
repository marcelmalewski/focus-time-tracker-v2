import { Component } from '@angular/core';
import { CommandLineComponent } from '../command-line/command-line.component';

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [CommandLineComponent],
  templateUrl: './settings.component.html',
})
export class SettingsComponent {}
