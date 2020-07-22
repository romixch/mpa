package ch.romix.mpa.web.forms;

import io.vertx.core.MultiMap;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FormsPostData {

  String personCount;
  String date;
  LocalDate parsedDate;
  String time;
  String firstname;
  String lastname;
  String zipcity;

  public FormsPostData(MultiMap formAttributes) {
    personCount = formAttributes.get("person-count");
    date = formAttributes.get("date");
    try {
      parsedDate = LocalDate.parse(this.date, DateTimeFormatter.ISO_DATE);
    } catch (DateTimeParseException e) {
      parsedDate = null;
    }
    time = formAttributes.get("time");
    firstname = formAttributes.get("firstname");
    lastname = formAttributes.get("lastname");
    zipcity = formAttributes.get("zipcity");
  }

  public int getPersonCount() {
    int count = 0;
    if (personCount != null) {
      count = Integer.parseInt(personCount);
    }
    return count;
  }

  FormsTemplateData toTemplateData() {
    return new FormsTemplateData(getPersonCount(), parsedDate, time, firstname, lastname, zipcity);
  }
}
