package ch.romix.mpa.domain.time;

import static java.util.stream.Collectors.groupingBy;

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
}
