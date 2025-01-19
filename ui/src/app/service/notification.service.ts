import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
    providedIn: 'root',
})
export class NotificationService {
    constructor(private snackBar: MatSnackBar) {}

    openErrorNotification(message: string) {
        this.snackBar.open(message, undefined, {
            duration: 2000,
            panelClass: ['notification-error'],
        });
    }

    openSuccessNotification(message: string) {
        this.snackBar.open(message, undefined, {
            duration: 2000,
            panelClass: ['notification-success'],
        });
    }
}
