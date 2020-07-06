package ch.romix.progressive.enhancement.session;

import io.vertx.core.http.Cookie;
import io.vertx.core.http.CookieSameSite;
import io.vertx.core.http.HttpServerResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

@Provider
public class SessionCookieResponseFilter implements ContainerResponseFilter {

  @Context
  HttpServerResponse response;
  @Inject
  SessionData sessionData;

  @Override
  public void filter(ContainerRequestContext requestContext,
      ContainerResponseContext responseContext) throws IOException {
    setSettionDataForInteger(sessionData::getPersonCount, "person-count");
    setSettionDataForDate(sessionData::getDate, "date");
    setSettionDataForString(sessionData::getTime, "time");
    setSettionDataForString(sessionData::getFirstname, "firstname");
    setSettionDataForString(sessionData::getLastname, "lastname");
    setSettionDataForString(sessionData::getZipcity, "zipcity");
  }

  private void setSettionDataForInteger(Supplier<Integer> getter, String cookieName) {
    Integer value = getter.get();
    if (value != null && value != 0) {
      response.addCookie(createCookie(cookieName, String.valueOf(value)));
    }
  }

  private void setSettionDataForDate(Supplier<LocalDate> getter, String cookieName) {
    LocalDate value = getter.get();
    if (value != null) {
      response.addCookie(createCookie(cookieName, value.format(DateTimeFormatter.ISO_LOCAL_DATE)));
    }
  }

  private void setSettionDataForString(Supplier<String> getter, String cookieName) {
    String value = getter.get();
    if (value != null) {
      Cookie cookie = createCookie(cookieName, value);
      response.addCookie(cookie);
    }
  }

  private Cookie createCookie(String name, String value) {
    Cookie cookie = Cookie.cookie(name, value);
    cookie.setSameSite(CookieSameSite.STRICT);
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    return cookie;
  }
}
