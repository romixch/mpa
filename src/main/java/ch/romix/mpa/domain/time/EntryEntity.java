package ch.romix.mpa.domain.time;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class EntryEntity {

  public final String id;
  public final String userRef;
  public final LocalDate day;
  public final LocalTime start;
  public final LocalTime end;
  public final Duration duration;

  public EntryEntity(String id, String userRef, LocalDate day, LocalTime start, LocalTime end) {
    this.id = id;
    this.userRef = userRef;
    this.day = day;
    this.start = start;
    this.end = end;
    this.duration = Duration.between(start, end);
  }
}
