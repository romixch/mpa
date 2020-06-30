package ch.romix.progressive.enhancement.list;

import java.util.List;

public class ListFilteringTemplateData {
  private String searchTerm;
  private List<String> list;
  private String wikipediaData;
  private String wikipediaUrl;

  public ListFilteringTemplateData(String searchTerm, List<String> list, String wikipediaData, String wikipediaUrl) {
    this.searchTerm = searchTerm;
    this.list = list;
    this.wikipediaData = wikipediaData;
    this.wikipediaUrl = wikipediaUrl;
  }

  public String getSearchTerm() {
    return searchTerm;
  }

  public List<String> getList() {
    return list;
  }

  public String getWikipediaData() {
    return wikipediaData;
  }

  public String getWikipediaUrl() {
    return wikipediaUrl;
  }
}
