package ch.romix.progressive.enhancement;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class HomeResources {

  @Inject
  Template index;

  @GET
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance index() {
    return index.instance();
  }

}
