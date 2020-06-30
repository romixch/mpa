package ch.romix.progressive.enhancement;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/stack")
public class StackResources {

  @Inject
  Template stack;

  @GET
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance index() {
    return stack.instance();
  }

}
