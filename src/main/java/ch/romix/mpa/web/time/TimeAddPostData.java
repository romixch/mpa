package ch.romix.mpa.web.time;

import io.vertx.core.MultiMap;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeAddPostData {

  String day;
  LocalDate parsedDay;
  String start;
  LocalTime parsedStart;
  String end;
  LocalTime parsedEnd;

  public TimeAddPostData(MultiMap formAttributes) {
    day = formAttributes.get("day");
    start = formAttributes.get("start");
    end = formAttributes.get("end");
    try {
      parsedDay = LocalDate.parse(this.day, DateTimeFormatter.ISO_DATE);
    } catch (DateTimeParseException e) {
      parsedDay = null;
    }
    try {
      parsedStart = LocalTime.parse(this.start, DateTimeFormatter.ISO_TIME);
    } catch (DateTimeParseException e) {
      parsedStart = null;
    }
    try {
      parsedEnd = LocalTime.parse(this.end, DateTimeFormatter.ISO_TIME);
    } catch (DateTimeParseException e) {
      parsedEnd = null;
    }
  }
}
