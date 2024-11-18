import { Component, ElementRef, HostListener, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-command-line',
  standalone: true,
  imports: [FormsModule, NgIf],
  templateUrl: './command-line.component.html',
})
export class CommandLineComponent {
  commandValue: string = '';

  constructor(private router: Router) {}

  @ViewChild('myInput', { static: true })
  myInput!: ElementRef<HTMLInputElement>;

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

  focusInput(): void {
    this.myInput.nativeElement.focus();
  }

  onSubmit() {
    if (this.commandValue === 'settings') {
      this.router.navigate(['/settings']);
    } else {
      this.router.navigate(['/timer']);
    }

    this.commandValue = '';
  }
}
