package ch.romix.mpa.infra;

import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse;
import com.google.cloud.secretmanager.v1.ProjectName;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.SecretVersionName;
import io.quarkus.runtime.Startup;
import io.vertx.core.json.JsonObject;
import javax.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Startup
@ApplicationScoped
public class Environment {

  Logger LOGGER = LoggerFactory.getLogger(Environment.class);

  private String gcpProjectId = "progressive-enhancement";
  private String gcpSecretId = "auth0-client-secret";
  private String auth0Domain;
  private String auth0ClientId;
  private String auth0ClientSecret;

  public Environment() throws Exception {
    auth0Domain = System.getenv("AUTH0_DOMAIN");
    auth0ClientId = System.getenv("AUTH0_CLIENTID");
    auth0ClientSecret = System.getenv("AUTH0_CLIENTSECRET");

    if (auth0Domain == null || auth0ClientId == null || auth0ClientSecret == null) {
      readFromGCPSecretManager(gcpProjectId, gcpSecretId);
    }
  }


  public void readFromGCPSecretManager(String projectId, String secretId) throws Exception {
    // Initialize client that will be used to send requests. This client only needs to be created
    // once, and can be reused for multiple requests. After completing all of your requests, call
    // the "close" method on the client to safely clean up any remaining background resources.
    try (SecretManagerServiceClient client = SecretManagerServiceClient.create()) {
      // Build the parent name from the project.
      ProjectName projectName = ProjectName.of(projectId);

      SecretVersionName secretVersionName = SecretVersionName
          .of(gcpProjectId, gcpSecretId, "latest");
      AccessSecretVersionResponse secretResponse = client
          .accessSecretVersion(secretVersionName);

      String data = secretResponse.getPayload().getData().toStringUtf8();
      JsonObject jsonObject = new JsonObject(data);
      auth0Domain = jsonObject.getString("AUTH0_DOMAIN");
      auth0ClientId = jsonObject.getString("AUTH0_CLIENTID");
      auth0ClientSecret = jsonObject.getString("AUTH0_CLIENTSECRET");
    }
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