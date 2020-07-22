package ch.romix.mpa.web;

import io.quarkus.qute.Template;
import io.quarkus.vertx.web.Route;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StackRoute {

  @Inject
  Template stack;

  @Route(path = "/stack", methods = HttpMethod.GET)
  public void index(RoutingContext rc) {
    rc.response().end(stack.render());
  }

}
