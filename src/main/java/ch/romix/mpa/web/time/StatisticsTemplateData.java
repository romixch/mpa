package ch.romix.mpa.web.time;

import io.quarkus.qute.TemplateData;
import java.time.Duration;

@TemplateData
public class StatisticsTemplateData {
  public final Duration worktimeTotal;
  public final Duration sparetimeTotal;

  public StatisticsTemplateData(Duration worktimeTotal, Duration spareTimeTotal) {

    this.worktimeTotal = worktimeTotal;
    this.sparetimeTotal = spareTimeTotal;
  }
}
