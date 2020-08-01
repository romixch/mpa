package ch.romix.mpa.infra;

import io.quarkus.runtime.Startup;
import javax.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Startup
@ApplicationScoped
public class Environment {

  Logger LOGGER = LoggerFactory.getLogger(Environment.class);

  private final String gcpProjectId = "progressive-enhancement";
  private final String gcpSecretId = "auth0-client-secret";
  private final String auth0Domain;
  private final String auth0ClientId;
  private final String auth0ClientSecret;

  public Environment() throws Exception {
    auth0Domain = System.getenv("AUTH0_DOMAIN");
    auth0ClientId = System.getenv("AUTH0_CLIENTID");
    auth0ClientSecret = System.getenv("AUTH0_CLIENTSECRET");
  }

  public String getAuth0Domain() {
    return auth0Domain;
  }

  public String getAuth0ClientId() {
    return auth0ClientId;
  }

  public String getAuth0ClientSecret() {
    return auth0ClientSecret;
  }
}