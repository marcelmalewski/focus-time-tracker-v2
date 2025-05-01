import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'timerField',
    standalone: true,
})
export class TimerFieldPipe implements PipeTransform {
    transform(value: number): string {
        return value.toString().padStart(2, '0');
    }
}
