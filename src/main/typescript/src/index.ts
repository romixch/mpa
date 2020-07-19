import "@vaadin/vaadin-date-picker/vaadin-date-picker.js"
import 'unpoly';
import 'unpoly/dist/unpoly.css';

import up from './unpoly';
import { i18nDatePicker } from "./vaadinTranslation";

up.compiler('#vaadinDate', {}, () => {
  const vaadinDate: any = document.getElementById('vaadinDate');
  vaadinDate.i18n = i18nDatePicker;
  const dateInput: any = document.getElementById('date');
  vaadinDate.addEventListener('change', () => {
    dateInput.value = vaadinDate.value;
    up.validate(dateInput);
  });
});