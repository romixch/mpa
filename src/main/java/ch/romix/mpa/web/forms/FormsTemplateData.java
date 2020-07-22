package ch.romix.mpa.web.forms;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FormsTemplateData {

  @Min(message = "Mindestens eine Person muss teilnehmen", value = 1)
  private int personCount;

  @NotNull(message = "Bitte wählen Sie ein Datum")
  private LocalDate date;

  @NotBlank(message = "Bitte wählen Sie eine Zeit aus")
  private String time;

  @NotBlank(message = "Bitte geben Sie Ihren Vornamen an")
  private String firstname;

  @NotBlank(message = "Wir benötigen Ihren Nachnamen um weiterzufahren")
  private String lastname;

  @NotBlank(message = "Ohne PLZ und Ort geht nix")
  private String zipcity;

  public FormsTemplateData() {
  }

  public FormsTemplateData(int personCount, LocalDate date, String time, String firstname,
      String lastname,
      String zipcity) {
    this.personCount = personCount;
    this.date = date;
    if (getAvailableTimes().contains(time)) {
      this.time = time;
    } else {
      this.time = "";
    }
    this.firstname = firstname;
    this.lastname = lastname;
    this.zipcity = zipcity;
  }

  public List<String> getAvailableTimes() {
    List<String> availableTimes = Arrays
        .asList("16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00",
            "20:30", "21:00", "21:30", "22:00", "22:30");
    int notAvailableNumberOfTimes = Math
        .min(Math.max(0, getPersonCount()), availableTimes.size() - 1);
    return availableTimes.subList(notAvailableNumberOfTimes, availableTimes.size() - 1);
  }

  public int getPersonCount() {
    return personCount;
  }

  public LocalDate getDate() {
    return date;
  }

  public String getTime() {
    return time;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public String getZipcity() {
    return zipcity;
  }

  public void setZipcity(String zipcity) {
    this.zipcity = zipcity;
  }
}
