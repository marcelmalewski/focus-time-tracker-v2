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
    timerInterval: number;
    timerRemainingFocus: number;
    timerRemainingInterval: number;
    timerRemainingBreak: number;
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
    timerRemainingFocus: number;
    timerRemainingInterval: number;
    timerRemainingBreak: number;
}

export interface TimerCurrentTime {
    timerCurrentHour: number;
    timerCurrentMinute: number;
    timerCurrentSecond: number;
}

export interface TimerRemainingTime {
    timerRemainingFocus: number;
}

export interface TimerBreak {}

export interface PrincipalWithMainTopics {
    principalBasicData: PrincipalBasicData;
    mainTopicsBasicData: MainTopicBasicData[];
}

export interface MainTopicBasicData {
    id: number;
    name: string;
}
