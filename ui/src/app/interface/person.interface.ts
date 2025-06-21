import { Stage } from '../other/typesAndConsts';

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
    stopWatchAutoBreak: boolean;
    timerRemainingTime: number;
    timerInterval: number;
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
    timerRemainingTime: number;
}

export interface TimerCurrentTime {
    timerCurrentHour: number;
    timerCurrentMinute: number;
    timerCurrentSecond: number;
}

export interface TimerPause {
    timerStage: Stage;
    timerRemainingFocus: number;
}

export interface TimerBreak {
    timerStage: Stage;
    timerBreak: string;
    timerRemainingTime: number;
}

export interface PrincipalWithMainTopics {
    principalBasicData: PrincipalBasicData;
    mainTopicsBasicData: MainTopicBasicData[];
}

export interface MainTopicBasicData {
    id: number;
    name: string;
}
