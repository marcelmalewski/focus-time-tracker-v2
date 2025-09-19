import { Pipe, PipeTransform } from '@angular/core';
import { BreakTypeLabels } from '../spec/timer-spec';

@Pipe({
    name: 'translateBreak',
    standalone: true,
})
export class translateBreakPipe implements PipeTransform {
    transform(value: string | undefined): string {
        return value ? BreakTypeLabels[value] : '';
    }
}
