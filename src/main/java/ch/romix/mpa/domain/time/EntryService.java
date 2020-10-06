package ch.romix.mpa.domain.time;

import static java.util.stream.Collectors.groupingBy;

import ch.romix.mpa.domain.time.EntryEntity.TimeType;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EntryService {
  public static Collection<DayAggregate> aggregateByDate(Collection<EntryEntity> entries) {
    Map<LocalDate, List<EntryEntity>> grouped = entries.stream().collect(groupingBy(e -> e.day));
    return grouped.entrySet().stream()
        .map((entry) -> new DayAggregate(entry.getKey(), entry.getValue())).collect(
            Collectors.toList());
  }

  public static Duration total(TimeType type, Collection<EntryEntity> entries) {
    return entries.stream()
        .filter(entry -> type.equals(entry.timeType))
        .map(entry -> entry.duration)
        .reduce(Duration.ZERO, Duration::plus);
  }
}
