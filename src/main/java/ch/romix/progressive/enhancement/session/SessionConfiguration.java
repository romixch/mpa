package ch.romix.progressive.enhancement.session;

import io.vertx.core.Vertx;
import io.vertx.core.http.CookieSameSite;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class SessionConfiguration {
  private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SessionConfiguration.class);

  @Inject
  Vertx vertx;

  public void init(@Observes Router router) {
    LocalSessionStore sessionStore = LocalSessionStore.create(this.vertx);
    SessionHandler sessionHandler = SessionHandler.create(sessionStore);
    sessionHandler.setCookieSameSite(CookieSameSite.STRICT);
    router.route().order(0).handler(sessionHandler);

    router.route().order(1).handler(rc -> {
      LOGGER.info("######## headers");
      rc.request().headers().entries().forEach(entry -> LOGGER.info("Header " + entry.getKey() + ": " + entry.getValue()));
      LOGGER.info("########");
      LOGGER.info("uri: " + rc.request().uri());
      LOGGER.info("absoluteURI: " + rc.request().absoluteURI());
      rc.next();
    });
  }
}
