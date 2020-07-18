export const i18nDatePicker = {
  // An array with the full names of months starting
  // with January.
  monthNames: [
    'Januar', 'Februar', 'März', 'April', 'Mai',
    'Juni', 'Juli', 'August', 'September',
    'Oktober', 'November', 'Dezember'
  ],

  // An array of weekday names starting with Sunday. Used
  // in screen reader announcements.
  weekdays: [
    'Sonntag', 'Montag', 'Dienstag', 'Mittwoch',
    'Donnerstag', 'Freitag', 'Samstag'
  ],

  // An array of short weekday names starting with Sunday.
  // Displayed in the calendar.
  weekdaysShort: [
    'So', 'Mo', 'Di', 'Mi', 'Do', 'Fr', 'Sa'
  ],

  // An integer indicating the first day of the week
  // (0 = Sunday, 1 = Monday, etc.).
  firstDayOfWeek: 1,

  // Used in screen reader announcements along with week
  // numbers, if they are displayed.
  week: 'Woche',

  // Translation of the Calendar icon button title.
  calendar: 'Kalender',

  // Translation of the Clear icon button title.
  clear: 'Löschen',

  // Translation of the Today shortcut button text.
  today: 'Heute',

  // Translation of the Cancel button text.
  cancel: 'Abbrechen',

  // A function to format given `Object` as
  // date string. Object is in the format `{ day: ..., month: ..., year: ... }`
  // Note: The argument month is 0-based. This means that January = 0 and December = 11.
  formatDate: (d: any) => {
    // returns a string representation of the given
    // object in 'MM/DD/YYYY' -format
    console.log('d', d);
    return `${d.day}.${d.month + 1}.${d.year}`;
  },

  // A function to parse the given text to an `Object` in the format `{ day: ..., month: ..., year: ... }`.
  // Must properly parse (at least) text formatted by `formatDate`.
  // Setting the property to null will disable keyboard input feature.
  // Note: The argument month is 0-based. This means that January = 0 and December = 11.
  parseDate: (text: string) => {
    // Parses a string in 'MM/DD/YY', 'MM/DD' or 'DD' -format to
    // an `Object` in the format `{ day: ..., month: ..., year: ... }`.
    console.log('text', text);
    const splitted = text.split('.');
    return { day: splitted[0], month: -1 + splitted[1], year: splitted[2] };
  },

  // A function to format given `monthName` and
  // `fullYear` integer as calendar title string.
  formatTitle: (monthName: string, fullYear: string) => {
    return monthName + ' ' + fullYear;
  }
}