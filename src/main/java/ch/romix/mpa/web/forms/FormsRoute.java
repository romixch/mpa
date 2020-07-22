package ch.romix.mpa.web.forms;

import ch.romix.mpa.infra.session.SessionData;
import ch.romix.mpa.infra.OriginHandler;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.ResourcePath;
import io.quarkus.vertx.web.Route;
import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.apache.http.client.utils.URIBuilder;
import org.jboss.logging.Logger;

@Singleton
public class FormsRoute {

  @Inject
  Validator validator;

  @Inject
  Template forms;

  @ResourcePath("forms-thank-you.html")
  Template thankyou;

  @Inject
  ZipService zipService;

  @Route(path = "/forms", methods = HttpMethod.GET)
  public void forms(RoutingContext rc) {
    FormsTemplateData templateData = new FormsTemplateData();
    var templateInstance = forms.data("data", templateData)
        .data("violations", Collections.EMPTY_MAP);
    rc.response().end(templateInstance.render());
  }

  @Route(path = "/forms", methods = HttpMethod.POST)
  public void postForm(RoutingContext rc) throws URISyntaxException {
    FormsPostData data = new FormsPostData(rc.request().formAttributes());
    FormsTemplateData templateData = data.toTemplateData();
    @Nullable String validate = rc.request().getHeader("X-Up-Validate");
    Set<ConstraintViolation<FormsTemplateData>> violations = validator.validate(templateData);
    if (validate != null || !violations.isEmpty()) {
      if ("zipcity".equals(validate)) {
        updateZipCity(templateData);
      }
      TemplateInstance templateInstance = validateForm(templateData);
      rc.response().end(templateInstance.render());
    } else {
      saveDataToSession(data, rc.session());
      URI origin = OriginHandler.getOrigin(rc);
      URI uri = new URIBuilder(origin).setPath("/forms/thankyou").build();
      Logger.getLogger(getClass()).info("redirecting to " + uri.toString());
      rc.response().putHeader("location", uri.toString()).setStatusCode(302).end();
    }
  }

  private void updateZipCity(FormsTemplateData templateData) {
    Collection<String> citiesByZip = zipService.getCityByZIPAndCity(templateData.getZipcity());
    if (citiesByZip.size() == 1) {
      templateData.setZipcity(citiesByZip.iterator().next());
    }
  }

  @Route(path = "/forms/thankyou", methods = HttpMethod.GET)
  public void thankyou(RoutingContext rc) {
    @Nullable Session session = rc.session();
    SessionData sessionData = session.get("forms");
    FormsTemplateData templateData = new FormsTemplateData(sessionData.getPersonCount(),
        sessionData.getDate(), sessionData.getTime(), sessionData.getFirstname(),
        sessionData.getLastname(),
        sessionData.getZipcity());
    rc.response().end(thankyou.data("data", templateData).render());
  }

  private void saveDataToSession(FormsPostData data, @Nullable Session session) {
    SessionData sessionData = new SessionData();
    sessionData.setPersonCount(data.getPersonCount());
    sessionData.setDate(data.parsedDate);
    sessionData.setTime(data.time);
    sessionData.setFirstname(data.firstname);
    sessionData.setLastname(data.lastname);
    sessionData.setZipcity(data.zipcity);
    session.put("forms", sessionData);
  }

  private TemplateInstance validateForm(FormsTemplateData templateData) {
    Set<ConstraintViolation<FormsTemplateData>> violations = validator.validate(templateData);
    Map<String, String> violationMap = violations.stream().collect(
        Collectors.toMap(v -> v.getPropertyPath().toString(), ConstraintViolation::getMessage));
    return forms.data("data", templateData).data("violations", violationMap);
  }
}