import "@vaadin/vaadin-date-picker"
import 'unpoly';
import 'unpoly/dist/unpoly.css';

import up from './unpoly';
import { i18nDatePicker } from "./vaadinTranslation";

up.compiler('#vaadinDate', {}, () => {
  const vaadinDate: any = document.getElementById('vaadinDate');
  //vaadinDate.i18n = i18nDatePicker;
  const dateInput: any = document.getElementById('date');
  vaadinDate.addEventListener('change', () => {
    dateInput.value = vaadinDate.value;
    console.log('Date change event', vaadinDate.value);
    up.validate(dateInput);
  });
});