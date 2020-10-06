package ch.romix.mpa.web.time;

import ch.romix.mpa.domain.time.EntryEntity.TimeType;
import io.vertx.core.MultiMap;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.validation.constraints.NotNull;

public class TimeAddPostData {

  String day;
  @NotNull
  LocalDate parsedDay;
  String start;
  @NotNull
  LocalTime parsedStart;
  String end;
  @NotNull
  LocalTime parsedEnd;
  @NotNull
  TimeType timeType;

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
    timeType = TimeType.valueOf(formAttributes.get("timeType"));
  }
}
