import { Page, Pages, Stage } from './common-spec';

export interface TimerCurrentTime {
    timerCurrentHour: number;
    timerCurrentMinute: number;
    timerCurrentSecond: number;
}

export interface TimerCurrentBreakTime {
    timerCurrentMinute: number;
    timerCurrentSecond: number;
}

export interface MoveTimerToStageBreakWithAutoBreakResult {
    timerStage: Stage;
    timerRemainingInterval: number;
}

export interface TimerManualBreakDto {
    breakTypeToStart: Stage;
    finished: boolean;
}

export const BreakTypeLabels: { [key: string]: string } = {
    SHORT_BREAK: 'Short break',
    LONG_BREAK: 'Long break',
};
