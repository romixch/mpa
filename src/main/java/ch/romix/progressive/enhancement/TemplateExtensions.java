package ch.romix.progressive.enhancement;

import io.quarkus.qute.TemplateExtension;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@TemplateExtension
public class TemplateExtensions {

  static String format(LocalDate date) {
    // TODO: Use this with GraalVM 20: DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.GERMAN)
    return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
  }
}
