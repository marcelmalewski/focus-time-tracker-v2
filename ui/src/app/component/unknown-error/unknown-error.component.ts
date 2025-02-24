import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import { UnknownServerErrorMessageRefreshPage } from '../../other/message';

@Component({
    selector: 'app-unknown-error',
    standalone: true,
    imports: [FormsModule, NgIf],
    templateUrl: './unknown-error.component.html',
})
export class UnknownErrorComponent {
    protected readonly UnknownServerErrorMessage =
        UnknownServerErrorMessageRefreshPage;
}
