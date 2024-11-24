import {
  Component,
  CUSTOM_ELEMENTS_SCHEMA,
  ElementRef,
  HostListener,
  Input,
  OnInit,
  ViewChild,
} from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AsyncPipe, NgIf } from '@angular/common';
import { Router } from '@angular/router';
import {
  MatAutocomplete,
  MatAutocompleteTrigger,
  MatOption,
} from '@angular/material/autocomplete';
import { map, Observable, startWith } from 'rxjs';
import { MatFormField } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import {
  Command,
  Commands,
  CommandsValues,
  Pages,
} from '../../other/typesAndConsts';

@Component({
  selector: 'app-command-line',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    MatAutocompleteTrigger,
    MatAutocomplete,
    MatOption,
    MatFormField,
    MatInput,
    ReactiveFormsModule,
    AsyncPipe,
  ],
  templateUrl: './command-line.component.html',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class CommandLineComponent implements OnInit {
  @Input({ required: true })
  commandContext!: string;

  commandInputControl = new FormControl('');
  commands: Command[] = CommandsValues;
  filteredCommands: Observable<string[]> | undefined;
  firstCommandFromFilteredCommands: string | undefined;

  @ViewChild('commandInput', { static: true })
  commandInput!: ElementRef<HTMLInputElement>;

  constructor(private router: Router) {}

  ngOnInit() {
    this.filteredCommands = this.commandInputControl.valueChanges.pipe(
      startWith(''),
      map(value => this.filterCommands(value || ''))
    );
  }

  private filterCommands(value: string): string[] {
    if (value === '') {
      return [];
    }

    const filterValue = value.toLowerCase();
    const filteredCommands = this.commands.filter(option =>
      option.toLowerCase().includes(filterValue)
    );
    this.firstCommandFromFilteredCommands = filteredCommands[0];

    return filteredCommands;
  }

  onSubmit() {
    const commandValue: string | null = this.firstCommandFromFilteredCommands
      ? this.firstCommandFromFilteredCommands
      : this.commandInputControl.value;
    if (!commandValue) {
      return;
    }

    if (commandValue === Commands.GO_TO_SETTINGS) {
      this.router.navigate([Pages.SETTINGS]);
    } else {
      this.router.navigate([Pages.TIMER]);
    }

    this.commandInputControl.reset();
  }

  @HostListener('document:keydown', ['$event'])
  handleShortcut(event: KeyboardEvent): void {
    if (
      event.key === 'c' &&
      !event.ctrlKey &&
      !event.altKey &&
      !event.metaKey
    ) {
      event.preventDefault();
      this.focusInput();
    }
  }

  private focusInput(): void {
    this.commandInput.nativeElement.focus();
  }
}
