package ch.romix.mpa.web.secured;

import ch.romix.mpa.infra.Environment;
import ch.romix.mpa.infra.OriginHandler;
import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.json.auth.UserInfo;
import com.auth0.net.AuthRequest;
import com.auth0.net.Request;
import io.quarkus.vertx.web.Route;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.CookieSameSite;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.apache.http.client.utils.URIBuilder;

@Singleton
public class SecuredRoute {

  @Inject
  private Environment environment;

  @Route(path = "/auth/login", methods = HttpMethod.GET)
  public void index(RoutingContext rc) throws URISyntaxException {
    AuthAPI auth0 = new AuthAPI(environment.getAuth0Domain(), environment.getAuth0ClientId(),
        environment.getAuth0ClientSecret());

    URI origin = OriginHandler.getOrigin(rc);
    URI redirectUrl = new URIBuilder(origin).setPath("/auth/callback").build();
    String state = UUID.randomUUID().toString();
    String authorizeUrl = auth0.authorizeUrl(redirectUrl.toString())
        .withScope("openid profile email")
        .withState(state)
        .build();
    rc.response().addCookie(Cookie.cookie("auth0-state", state).setMaxAge(600).setSameSite(
        CookieSameSite.LAX));
    rc.response().putHeader("location", authorizeUrl).setStatusCode(302).end();
  }

  @Route(path = "/auth/callback", methods = HttpMethod.GET)
  public void authCallback(RoutingContext rc)
      throws URISyntaxException, Auth0Exception {
    String code = rc.request().getParam("code");
    String state = rc.request().getParam("state");
    URI origin = OriginHandler.getOrigin(rc);

    String stateFromCookie = rc.request().cookieMap().get("auth0-state").getValue();
    if (!state.equals(stateFromCookie)) {
      throw new RuntimeException("Not authorized. State does not match");
    }

    AuthAPI auth0 = new AuthAPI(environment.getAuth0Domain(), environment.getAuth0ClientId(),
        environment.getAuth0ClientSecret());

    AuthRequest authRequest = auth0.exchangeCode(code, origin.toString());

    try {
      TokenHolder tokenHolder = authRequest.execute();
      Request<UserInfo> request = auth0.userInfo(tokenHolder.getAccessToken());
      UserInfo info = request.execute();
      rc.session().put("user", info);
    } finally {
      rc.response().removeCookie("auth0-state");
    }

    URI homeUrl = new URIBuilder(origin).setPath("/").removeQuery().build();
    rc.response().putHeader("location", homeUrl.toString()).setStatusCode(302).end();
  }

  @Route(path = "/auth/logout", methods = HttpMethod.GET)
  public void authLogout(RoutingContext rc) throws URISyntaxException {
    AuthAPI auth0 = new AuthAPI(environment.getAuth0Domain(), environment.getAuth0ClientId(),
        environment.getAuth0ClientSecret());

    URI origin = OriginHandler.getOrigin(rc);
    URI returnUrl = new URIBuilder(origin).setPath("/").removeQuery().build();

    String logoutUrl = auth0.logoutUrl(returnUrl.toString(), true).build();
    rc.response().putHeader("location", logoutUrl).setStatusCode(302).end();
    rc.session().destroy();
  }

}
