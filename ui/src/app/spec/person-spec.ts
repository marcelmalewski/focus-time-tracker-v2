import { BreakType, Stage } from './common-spec';

export interface PrincipalBasicData {
    id: number;
    login: string;
    email: string;
    timerStage: Stage;
    timerSelectedTopic: string;
    timerSetHours: number;
    timerSetMinutes: number;
    timerSetSeconds: number;
    timerShortBreak: number;
    timerLongBreak: number;
    timerAutoBreak: boolean;
    timerInterval: number;
    timerRemainingFocus: number;
    timerRemainingInterval: number;
}

export interface TimerSettings {
    timerStage: Stage;
    timerSelectedTopic: string;
    timerSetHours: number;
    timerSetMinutes: number;
    timerSetSeconds: number;
    timerShortBreak: number;
    timerLongBreak: number;
    timerAutoBreak: boolean;
    timerInterval: number;
}

export interface TimerCurrentTime {
    timerCurrentHour: number;
    timerCurrentMinute: number;
    timerCurrentSecond: number;
}

export interface TimerBreak {
    breakTypeToStart: BreakType;
    timerRemainingInterval: number;
    timerAutoBreak?: boolean;
}

export interface TimerBreakResponse {
    breakTypeToStart: BreakType;
    timerRemainingInterval: number;
    timerAutoBreak?: boolean;
}

export interface PrincipalWithMainTopics {
    principalBasicData: PrincipalBasicData;
    mainTopicsBasicData: MainTopicBasicData[];
}

export interface MainTopicBasicData {
    id: number;
    name: string;
}
