export const Pages = {
  LOGIN: 'login',
  TIMER: 'timer',
  TIMER_EDIT: 'timer/edit',
  SETTINGS: 'settings',
} as const;
type PagesKeys = keyof typeof Pages;
export type Page = (typeof Pages)[PagesKeys];

export const Commands = {
  GO_TO_TIMER: 'go to timer',
  GO_TO_SETTINGS: 'go to settings',
} as const;
export const CommandsValues = Object.values(Commands);
type CommandsKeys = keyof typeof Commands;
export type Command = (typeof Commands)[CommandsKeys];

export const Stages = {
  HOME: 'Home',
  FOCUS: 'Focus',
  PAUSE: 'Pause',
  SHORT_BREAK: 'Short break',
  LONG_BREAK: 'Long break',
} as const;
type StagesKeys = keyof typeof Stages;
export type Stage = (typeof Stages)[StagesKeys];
