package ch.romix.mpa.web.time;

import ch.romix.mpa.domain.UserValueObject;
import ch.romix.mpa.domain.time.EntryEntity;
import ch.romix.mpa.domain.time.EntryRepository;
import ch.romix.mpa.infra.OriginHandler;
import io.quarkus.qute.Template;
import io.quarkus.qute.api.ResourcePath;
import io.quarkus.vertx.web.Route;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.apache.http.client.utils.URIBuilder;

@Singleton
public class TimeRoute {

  @Inject
  @ResourcePath("time/index.html")
  Template index;

  @Inject
  EntryRepository entryRepository;

  @Route(path = "/time", methods = HttpMethod.GET)
  public void index(RoutingContext rc) {
    UserValueObject user = rc.session().get("user");
    System.out.println("user in time: " + user);
    rc.response().end(index
        .data("user", rc.session().get("user"))
        .data("entries", entryRepository.findByUser(rc.session().get("user")))
        .data("currentDay", DateTimeFormatter.ISO_DATE.format(LocalDate.now()))
        .render());
  }

  @Route(path = "/time/add", methods = HttpMethod.POST)
  public void postForm(RoutingContext rc) throws URISyntaxException {
    TimeAddPostData data = new TimeAddPostData(rc.request().formAttributes());
    UserValueObject user = rc.session().get("user");
    EntryEntity entryEntity = new EntryEntity(UUID.randomUUID().toString(), user.getName(),
        data.parsedDay, data.parsedStart, data.parsedEnd);
    entryRepository.add(entryEntity);
    URI origin = OriginHandler.getOrigin(rc);
    URI uri = new URIBuilder(origin).setPath("/time").build();
    rc.response().putHeader("location", uri.toString()).setStatusCode(302).end();

  }
}
