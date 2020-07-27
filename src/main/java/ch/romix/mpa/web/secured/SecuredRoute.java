package ch.romix.mpa.web.secured;

import ch.romix.mpa.infra.Environment;
import ch.romix.mpa.infra.OriginHandler;
import com.auth0.AuthenticationController;
import com.auth0.IdentityVerificationException;
import com.auth0.Tokens;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.quarkus.vertx.web.Route;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import java.net.URI;
import java.net.URISyntaxException;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.apache.http.client.utils.URIBuilder;

@Singleton
public class SecuredRoute {

  @Inject
  private Environment environment;

  @Route(path = "/auth/login", methods = HttpMethod.GET)
  public void index(RoutingContext rc) throws URISyntaxException {

    URI origin = OriginHandler.getOrigin(rc);
    URI redirectUrl = new URIBuilder(origin).setPath("/auth/callback").build();

    JwkProvider jwkProvider = new JwkProviderBuilder(environment.getAuth0Domain()).build();
    AuthenticationController authenticationController = AuthenticationController
        .newBuilder(environment.getAuth0Domain(), environment.getAuth0ClientId(), environment.getAuth0ClientSecret()).withJwkProvider(jwkProvider).build();

    String authorizeUrl = authenticationController
        .buildAuthorizeUrl(new RequestWrapper(rc), new ResponseWrapper(rc), redirectUrl.toString())
        .withScope("openid profile email")
        .build();

    rc.response().putHeader("location", authorizeUrl).setStatusCode(302).end();
  }

  @Route(path = "/auth/callback", methods = HttpMethod.GET)
  public void authCallback(RoutingContext rc)
      throws IdentityVerificationException, URISyntaxException {
    JwkProvider jwkProvider = new JwkProviderBuilder(environment.getAuth0Domain()).build();
    AuthenticationController authenticationController = AuthenticationController
        .newBuilder(environment.getAuth0Domain(), environment.getAuth0ClientId(),
            environment.getAuth0ClientSecret()).withJwkProvider(jwkProvider).build();
    Tokens tokens = authenticationController
        .handle(new RequestWrapper(rc), new ResponseWrapper(rc));
    DecodedJWT decodedJWT = JWT.decode(tokens.getIdToken());
    var claims = decodedJWT.getClaims();
    System.out.println("claims: " + claims);
    URI origin = OriginHandler.getOrigin(rc);
    URI homeUrl = new URIBuilder(origin).setPath("/").removeQuery().build();
    rc.response().putHeader("location", homeUrl.toString()).setStatusCode(302).end();
  }

  @Route(path="/auth/logout", methods = HttpMethod.GET)
  public void authLogout(RoutingContext rc) throws URISyntaxException {
    // invalidate session

    URI origin = OriginHandler.getOrigin(rc);
    URI returnUrl = new URIBuilder(origin).setPath("/").removeQuery().build();

    // Build logout URL like:
    // https://{YOUR-DOMAIN}/v2/logout?client_id={YOUR-CLIENT-ID}&returnTo=http://localhost:3000/login
    String logoutUrl = String.format(
        "https://%s/v2/logout?client_id=%s&returnTo=%s",
        environment.getAuth0Domain(),
        environment.getAuth0ClientId(),
        returnUrl
    );
    rc.response().putHeader("location", logoutUrl).setStatusCode(302).end();
  }

}
