package ch.romix.progressive.enhancement.web;

import io.quarkus.qute.Template;
import io.quarkus.qute.api.ResourcePath;
import io.quarkus.vertx.web.Route;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HomeRoute {

  @Inject
  Template index;

  @Inject
  @ResourcePath("home/1-was-fehlt.html")
  Template wasFehlt;

  @Inject
  @ResourcePath("home/2-trotzdem.html")
  Template trotzdem;

  @Inject
  @ResourcePath("home/3-was-spare-ich-mir.html")
  Template wasSpareIchMir;

  @Inject
  @ResourcePath("home/4-inspiration.html")
  Template inspiration;

  @Route(path = "/", methods = HttpMethod.GET)
  public void index(RoutingContext rc) {
    rc.response().end(index.render());
  }

  @Route(path = "/home/was-fehlt", methods = HttpMethod.GET)
  public void wasFehlt(RoutingContext rc) {
    rc.response().end(wasFehlt.render());
  }

  @Route(path = "/home/trotzdem", methods = HttpMethod.GET)
  public void trotzdem(RoutingContext rc) {
    rc.response().end(trotzdem.render());
  }

  @Route(path = "/home/was-spare-ich-mir", methods = HttpMethod.GET)
  public void wasSpareIchMir(RoutingContext rc) {
    rc.response().end(wasSpareIchMir.render());
  }

  @Route(path = "/home/inspiration", methods = HttpMethod.GET)
  public void inspiration(RoutingContext rc) {
    rc.response().end(inspiration.render());
  }
}
