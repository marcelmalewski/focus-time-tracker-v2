export const Pages = {
    LOGIN: 'login',
    TIMER_HOME: 'timer',
    TIMER_FOCUS: 'timer/focus',
    TIMER_BREAK: 'timer/break',
    TIMER_EDIT: 'timer/edit',
    SETTINGS: 'settings',
    UNKNOWN_ERROR: 'unknown-error',
} as const;

export const PageLinks = {
    TIMER_HOME: '/timer',
} as const;

export const Commands = {
    GO_TO_TIMER: 'go to timer',
    GO_TO_SETTINGS: 'go to settings',
    LOGOUT: 'logout',
} as const;
export const CommandsValues = Object.values(Commands);
type CommandsKeys = keyof typeof Commands;
export type Command = (typeof Commands)[CommandsKeys];

export const Stages = {
    HOME: 'HOME',
    FOCUS: 'FOCUS',
    PAUSE: 'PAUSE',
    SHORT_BREAK: 'SHORT_BREAK',
    LONG_BREAK: 'LONG_BREAK',
} as const;
type StagesKeys = keyof typeof Stages;
export type Stage = (typeof Stages)[StagesKeys];
