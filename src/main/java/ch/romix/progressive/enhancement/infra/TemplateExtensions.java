package ch.romix.progressive.enhancement.infra;

import io.quarkus.qute.TemplateExtension;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@TemplateExtension
public class TemplateExtensions {

  static String format(LocalDate date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        .withLocale(Locale.GERMAN);
    return date.format(formatter);
  }
}
