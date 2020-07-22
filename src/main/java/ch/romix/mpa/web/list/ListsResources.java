package ch.romix.mpa.web.list;

import io.quarkus.qute.Template;
import io.quarkus.qute.api.ResourcePath;
import io.quarkus.vertx.web.Route;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Singleton;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

@Singleton
public class ListsResources {

  static List<Programmer> influencialProgrammers;

  static {
    try {
      influencialProgrammers = Arrays.asList(
          new Programmer("Alan Turing", new URL("https://de.wikipedia.org/wiki/Alan_Turing")),
          new Programmer("Tim Berners-Lee", new URL("https://de.wikipedia.org/wiki/Tim_Berners-Lee")),
          new Programmer("Grace Hopper", new URL("https://de.wikipedia.org/wiki/Grace_Hopper")),
          new Programmer("Dennis Ritchie", new URL("https://de.wikipedia.org/wiki/Dennis_Ritchie")),
          new Programmer("Linus Torvalds", new URL("https://de.wikipedia.org/wiki/Linus_Torvalds")),
          new Programmer("Bjarne Stroustrup", new URL("https://de.wikipedia.org/wiki/Bjarne_Stroustrup")),
          new Programmer("Ken Thompson", new URL("https://de.wikipedia.org/wiki/Ken_Thompson")),
          new Programmer("Brian Kernighan", new URL("https://de.wikipedia.org/wiki/Brian_W._Kernighan")),
          new Programmer("John Backus", new URL("https://de.wikipedia.org/wiki/John_W._Backus")),
          new Programmer("Niklaus Wirth", new URL("https://de.wikipedia.org/wiki/Niklaus_Wirth")),
          new Programmer("Anders Hejlsberg", new URL("https://de.wikipedia.org/wiki/Anders_Hejlsberg")),
          new Programmer("James Arthur Gosling", new URL("https://de.wikipedia.org/wiki/James_Gosling")),
          new Programmer("Guido van Rossum", new URL("https://de.wikipedia.org/wiki/Guido_van_Rossum")),
          new Programmer("Donald Ervin Knuth", new URL("https://de.wikipedia.org/wiki/Donald_E._Knuth")),
          new Programmer("John Carmack", new URL("https://de.wikipedia.org/wiki/John_Carmack")),
          new Programmer("Ada Lovelace", new URL("https://de.wikipedia.org/wiki/Ada_Lovelace")),
          new Programmer("Richard Stallman", new URL("https://de.wikipedia.org/wiki/Richard_Stallman")),
          new Programmer("Brendan Eich", new URL("https://de.wikipedia.org/wiki/Brendan_Eich"))
      );
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  @ResourcePath("list-filtering.html")
  Template listFiltering;

  @Route(path = "list-filtering", methods = HttpMethod.GET)
  public void listFiltering(RoutingContext rc) {
    String search = rc.request().getParam("search");
    String programmer = rc.request().getParam("programmer");
    List<String> programmers = influencialProgrammers.stream()
        .map(Programmer::getName)
        .filter(p -> search != null && p.toLowerCase().contains(search.toLowerCase()))
        .collect(Collectors.toList());
    Optional<Programmer> selectedProgrammer = influencialProgrammers.stream()
        .filter(p -> p.name.equals(programmer)).findAny();
    String wikiArticle = selectedProgrammer.map(this::getWikiArticle).orElse(null);
    ListFilteringTemplateData data = new ListFilteringTemplateData(search, programmers,
        wikiArticle, selectedProgrammer.map(p -> p.getWikipediaURL().toString()).orElse(""));
    rc.response().end(listFiltering.data("data", data).render());
  }

  private String getWikiArticle(Programmer programmer) {
    if (programmer.wikipediaURL == null) {
      return null;
    }
    if (programmer.getWikipediaArticle() == null) {
      try {
        Document doc = null;
        doc = Jsoup.connect(programmer.wikipediaURL.toString()).get();
        Elements elements = doc.select("p");
        if (elements.isEmpty()) {
          return null;
        }
        programmer.setWikipediaArticle(elements.get(0).outerHtml());
      } catch (IOException e) {
        e.printStackTrace();
        return null;
      }
    }
    return programmer.getWikipediaArticle();
  }

  private static class Programmer {

    private final String name;
    private final URL wikipediaURL;
    private String wikipediaArticle;

    public Programmer(String name, URL wikipediaURL) {
      this.name = name;
      this.wikipediaURL = wikipediaURL;
    }

    public String getName() {
      return name;
    }

    public URL getWikipediaURL() {
      return wikipediaURL;
    }

    public String getWikipediaArticle() {
      return wikipediaArticle;
    }

    public void setWikipediaArticle(String wikipediaArticle) {
      this.wikipediaArticle = wikipediaArticle;
    }
  }
}