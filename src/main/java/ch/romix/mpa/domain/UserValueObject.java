package ch.romix.mpa.domain;

import java.net.URL;

public class UserValueObject {

  private final String name;
  private final URL picture;

  public UserValueObject(String name, URL picture) {

    this.name = name;
    this.picture = picture;
  }

  public String getName() {
    return name;
  }

  public URL getPicture() {
    return picture;
  }
}
