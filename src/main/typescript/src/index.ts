import "@ui5/webcomponents/dist/Input.js"
import "@ui5/webcomponents/dist/DatePicker.js"
import "@ui5/webcomponents/dist/features/InputElementsFormSupport.js"

import up from './unpoly';

up.compiler('#ui5date', {}, () => {
  const ui5date = document.getElementById('ui5date');
  const dateInput: any = document.getElementById('date');
  ui5date.addEventListener('change', (e: any) => {
    console.log('### details:', e.detail);
    dateInput.value = e.detail.value;
    up.validate(dateInput);
  });
});