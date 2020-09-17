package ch.romix.mpa.web.time;

import io.quarkus.qute.Template;
import io.quarkus.qute.api.ResourcePath;
import io.quarkus.vertx.web.Route;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TimeRoute {
  @Inject
  @ResourcePath("time/index.html")
  Template index;

  @Route(path = "/time", methods = HttpMethod.GET)
  public void index(RoutingContext rc) {
    Object user = rc.session().get("user");
    System.out.println("user in time: " + user);
    rc.response().end(index.data("user", rc.session().get("user")).render());
  }
}
