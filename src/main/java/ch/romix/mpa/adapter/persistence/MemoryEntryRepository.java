package ch.romix.mpa.adapter.persistence;

import ch.romix.mpa.domain.UserValueObject;
import ch.romix.mpa.domain.time.EntryEntity;
import ch.romix.mpa.domain.time.EntryRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MemoryEntryRepository implements EntryRepository {

  private Collection<EntryEntity> entries = new ArrayList<>();

  @Override
  public Collection<EntryEntity> findByUser(UserValueObject user) {
    return entries.stream()
        .filter(e -> e.userRef.equals(user.getName()))
        .collect(Collectors.toList());
  }

  @Override
  public Collection<EntryEntity> findByUserAndDay(UserValueObject user, LocalDate day) {
    return entries.stream()
        .filter(e -> e.userRef.equals(user.getName()))
        .filter(e -> e.day.isEqual(day))
        .collect(Collectors.toList());
  }

  @Override
  public void add(EntryEntity entryEntity) {
     entries.add(entryEntity);
  }

  @Override
  public void delete(EntryEntity entryEntity) {
    entries.removeIf(e -> e.id.equals(entryEntity.id));
  }
}
