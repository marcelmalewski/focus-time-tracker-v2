import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'translateFinishedBreak',
    standalone: true,
})
export class translateFinishedPipe implements PipeTransform {
    transform(value: boolean): string {
        return value ? 'Yes' : 'No';
    }
}
