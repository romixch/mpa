package ch.romix.progressive.enhancement.forms;

import ch.romix.progressive.enhancement.ZipService;
import ch.romix.progressive.enhancement.session.SessionData;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.ResourcePath;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

@Path("/forms")
public class FormsResources {

  @Inject
  Validator validator;

  @Inject
  Template forms;

  @ResourcePath("forms-thank-you.html")
  Template thankyou;

  @Inject
  ZipService zipService;

  @Inject
  @RequestScoped
  SessionData sessionData;

  @GET
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance forms() {
    FormsTemplateData templateData = new FormsTemplateData();
    return forms.data("data", templateData).data("violations", Collections.EMPTY_MAP);
  }

  @POST
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public Response postForm(@MultipartForm FormsPostData data,
      @HeaderParam("X-Up-Validate") String validate,
      @HeaderParam("Origin") String origin) {
    FormsTemplateData templateData = data.toTemplateData();
    Set<ConstraintViolation<FormsTemplateData>> violations = validator.validate(templateData);
    if (validate != null || !violations.isEmpty()) {
      if ("zipcity".equals(validate)) {
        updateZipCity(templateData);
      }
      return validateForm(templateData);
    } else {
      saveDataToSession(data);
      URI uri = UriBuilder.fromUri(origin).path("/forms/thankyou").build();
      Logger.getLogger(getClass()).info("redirecting to " + uri.toString());
      return Response.seeOther(uri).build();
    }
  }

  private void updateZipCity(FormsTemplateData templateData) {
    Collection<String> citiesByZip = zipService.getCityByZIPAndCity(templateData.getZipcity());
    if (citiesByZip.size() == 1) {
      templateData.setZipcity(citiesByZip.iterator().next());
    }
  }

  @GET
  @Produces(MediaType.TEXT_HTML)
  @Path("/thankyou")
  public TemplateInstance thankyou() {
    FormsTemplateData templateData = new FormsTemplateData(sessionData.getPersonCount(),
        sessionData.getDate(), sessionData.getTime(), sessionData.getFirstname(), sessionData.getLastname(),
        sessionData.getZipcity());
    return thankyou.data("data", templateData);
  }

  private void saveDataToSession(FormsPostData data) {
    sessionData.setPersonCount(data.getPersonCount());
    sessionData.setDate(LocalDate.parse(data.date, DateTimeFormatter.ISO_LOCAL_DATE));
    sessionData.setTime(data.time);
    sessionData.setFirstname(data.firstname);
    sessionData.setLastname(data.lastname);
    sessionData.setZipcity(data.zipcity);
  }

  private Response validateForm(FormsTemplateData templateData) {
    Set<ConstraintViolation<FormsTemplateData>> violations = validator.validate(templateData);
    Map<String, String> violationMap = violations.stream().collect(
        Collectors.toMap(v -> v.getPropertyPath().toString(), ConstraintViolation::getMessage));
    return Response.ok(forms.data("data", templateData).data("violations", violationMap)).build();
  }
}