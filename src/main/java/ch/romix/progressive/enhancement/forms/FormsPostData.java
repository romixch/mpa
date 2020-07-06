package ch.romix.progressive.enhancement.forms;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class FormsPostData {

  @FormParam("person-count")
  @PartType(MediaType.TEXT_PLAIN)
  String personCount;

  @FormParam("date")
  @PartType(MediaType.TEXT_PLAIN)
  String date;

  @FormParam("time")
  @PartType(MediaType.TEXT_PLAIN)
  String time;

  @FormParam("firstname")
  @PartType(MediaType.TEXT_PLAIN)
  String firstname;

  @FormParam("lastname")
  @PartType(MediaType.TEXT_PLAIN)
  String lastname;

  @FormParam("zipcity")
  @PartType(MediaType.TEXT_PLAIN)
  String zipcity;

  public int getPersonCount() {
    int count = 0;
    if (personCount != null) {
      count = Integer.parseInt(personCount);
    }
    return count;
  }

  FormsTemplateData toTemplateData() {
    LocalDate parsedDate;
    try {
      parsedDate = LocalDate.parse(this.date, DateTimeFormatter.ISO_LOCAL_DATE);
    } catch (DateTimeParseException e) {
      parsedDate = null;
    }

    return new FormsTemplateData(getPersonCount(), parsedDate, time, firstname, lastname, zipcity);
  }
}
