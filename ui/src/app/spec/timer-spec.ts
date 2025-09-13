import { Stage } from './common-spec';

export interface TimerCurrentTime {
    timerCurrentHour: number;
    timerCurrentMinute: number;
    timerCurrentSecond: number;
}

export interface MoveTimerToStageBreakWithAutoBreakResult {
    timerStage: Stage;
    timerRemainingInterval: number;
}

export interface TimerManualBreakDto {
    breakTypeToStart: Stage;
}
