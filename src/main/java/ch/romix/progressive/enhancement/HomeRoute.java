package ch.romix.progressive.enhancement;

import io.quarkus.qute.Template;
import io.quarkus.vertx.web.Route;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HomeRoute {

  @Inject
  Template index;

  @Route(path = "/", methods = HttpMethod.GET)
  public void index(RoutingContext rc) {
    rc.response().end(index.render());
  }

}
