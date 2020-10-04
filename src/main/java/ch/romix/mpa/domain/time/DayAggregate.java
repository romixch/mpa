package ch.romix.mpa.domain.time;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

public class DayAggregate {
  private final LocalDate day;
  private final Duration duration;
  private final Collection<EntryEntity> entries;

  public DayAggregate(LocalDate day, Collection<EntryEntity> entries) {
    this.day = day;
    this.duration = entries.stream().map(e -> e.duration).reduce(Duration.ZERO, Duration::plus);
    this.entries = entries;
  }

  public Collection<EntryEntity> getEntries() {
    return Collections.unmodifiableCollection(entries);
  }

  public LocalDate getDay() {
    return day;
  }

  public Duration getDuration() {
    return duration;
  }
}
