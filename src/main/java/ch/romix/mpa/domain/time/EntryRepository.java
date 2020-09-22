package ch.romix.mpa.domain.time;

import ch.romix.mpa.domain.UserValueObject;
import java.time.LocalDate;
import java.util.Collection;

public interface EntryRepository {
  Collection<EntryEntity> findByUser(UserValueObject user);
  Collection<EntryEntity> findByUserAndDay(UserValueObject user, LocalDate day);
  void add(EntryEntity entryEntity);
  void delete(EntryEntity entryEntity);
}
