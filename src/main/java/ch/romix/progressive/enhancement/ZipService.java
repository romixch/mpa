package ch.romix.progressive.enhancement;

import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import org.apache.commons.collections4.ListUtils;

@ApplicationScoped
public class ZipService {

  private static Logger LOGGER = Logger.getLogger(ZipService.class.getCanonicalName());
  private static Map<String, List<String>> zipMap;
  private static Map<String, List<String>> cityMap;

  static {
    zipMap = new HashMap<>();
    cityMap = new HashMap<>();
    try (InputStream is = Thread.currentThread().getContextClassLoader()
        .getResourceAsStream("Postleitzahlen-Schweiz.csv")) {
      if (is == null) {
        throw new RuntimeException("Couldn't read Postleitzahlen-Schweiz.csv");
      }
      try (CSVReader csvReader = new CSVReader(new InputStreamReader(is))) {
        String[] values;
        while ((values = csvReader.readNext()) != null) {
          String zip = values[0];
          String city = values[1];

          List<String> zipCitiesInZipMap = zipMap.getOrDefault(zip, Collections.emptyList());
          List<String> newZipCitiesByZip = ListUtils
              .unmodifiableList(
                  ListUtils.union(zipCitiesInZipMap, Collections.singletonList(zip + " " + city)));
          zipMap.put(zip, newZipCitiesByZip);

          List<String> zipCitiesInCityMap = cityMap
              .getOrDefault(city.toLowerCase(), Collections.emptyList());
          List<String> newZipCitiesByCity = ListUtils
              .unmodifiableList(
                  ListUtils.union(zipCitiesInCityMap, Collections.singletonList(zip + " " + city)));
          cityMap.put(city.toLowerCase(), newZipCitiesByCity);
        }
        LOGGER.info("Found " + zipMap.size() + " zips");
        LOGGER.info("Found " + cityMap.size() + " cities");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Collection<String> getCityByZIPAndCity(String zipAndCity) {
    String[] parts = zipAndCity.toLowerCase().split("\\s+");
    Set<String> zipsAndCities = Arrays.stream(parts)
        .map(part ->
            ListUtils.union(zipMap.getOrDefault(part, Collections.emptyList()),
                cityMap.getOrDefault(part, Collections.emptyList())))
        .flatMap(Collection::stream)
        .collect(Collectors.toSet());
    return zipsAndCities;
  }

}
