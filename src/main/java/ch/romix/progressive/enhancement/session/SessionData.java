package ch.romix.progressive.enhancement.session;

import java.time.LocalDate;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class SessionData {

  private int personCount;
  private LocalDate date;
  private String time;
  private String firstname;
  private String lastname;
  private String zip;
  private String city;

  public int getPersonCount() {
    return personCount;
  }

  public void setPersonCount(int personCount) {
    this.personCount = personCount;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }
}