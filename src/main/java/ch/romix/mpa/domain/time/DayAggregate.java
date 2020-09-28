package ch.romix.mpa.domain.time;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;

public class DayAggregate {
  private final LocalDate day;
  private final Duration duration;
  private final Collection<EntryEntity> entries;

  public DayAggregate(LocalDate day, Collection<EntryEntity> entries) {
    this.day = day;
    this.duration = entries.stream().map(e -> e.duration).reduce(Duration.ZERO, Duration::plus);
    this.entries = entries;
  }
}
