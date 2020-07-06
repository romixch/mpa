package ch.romix.progressive.enhancement.session;

import io.vertx.core.http.Cookie;
import io.vertx.core.http.HttpServerRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

@Provider
public class SessionCookieRequestFilter implements ContainerRequestFilter {

  @Context
  HttpServerRequest request;
  @Inject
  SessionData sessionData;

  @Override
  public void filter(ContainerRequestContext context) throws IOException {
    setSessionDataForInteger("person-count", sessionData::setPersonCount);
    setSessionDataForDate("date", sessionData::setDate);
    setSessionDataForString("time", sessionData::setTime);
    setSessionDataForString("firstname", sessionData::setFirstname);
    setSessionDataForString("lastname", sessionData::setLastname);
    setSessionDataForString("zipcity", sessionData::setZipcity);
  }

  private void setSessionDataForString(String cookieName, Consumer<String> setter) {
    Cookie cookie = request.getCookie(cookieName);
    if (cookie != null) {
      setter.accept(cookie.getValue());
    }
  }

  private void setSessionDataForDate(String cookieName, Consumer<LocalDate> setter) {
    Cookie cookie = request.getCookie(cookieName);
    if (cookie != null) {
      String value = cookie.getValue();
      if (value != null) {
        setter.accept(LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE));
      }
    }
  }

  private void setSessionDataForInteger(String cookieName, Consumer<Integer> setter) {
    Cookie cookie = request.getCookie(cookieName);
    if (cookie != null) {
      String value = cookie.getValue();
      if (value != null) {
        setter.accept(Integer.parseInt(value));
      }
    }
  }
}
