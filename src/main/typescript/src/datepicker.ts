import "@vaadin/vaadin-date-picker/vaadin-date-picker.js"
import { i18nDatePicker } from "./vaadinTranslation";

import up from './unpoly';

up.compiler('#vaadinDate', {}, () => {
  const vaadinDate: any = document.getElementById('vaadinDate');
  vaadinDate.i18n = i18nDatePicker;
  const dateInput: any = document.getElementById('date');
  vaadinDate.addEventListener('change', () => {
    dateInput.value = vaadinDate.value;
    up.validate(dateInput);
  });
});

up.compiler('#timeDayPicker', {}, () => {
  const vaadinDate: any = document.getElementById('timeDayPicker');
  vaadinDate.i18n = i18nDatePicker;
  const dateInput: any = document.getElementById('day');
  vaadinDate.addEventListener('change', () => {
    console.log('setting date to ', vaadinDate.value);
    dateInput.value = vaadinDate.value;
  });
});

