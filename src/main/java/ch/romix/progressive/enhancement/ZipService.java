package ch.romix.progressive.enhancement;

import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import org.apache.commons.collections4.ListUtils;

@ApplicationScoped
public class ZipService {

  private static Logger LOGGER = Logger.getLogger(ZipService.class.getCanonicalName());
  private static Map<String, List<String>> zipMap;

  static {
    zipMap = new HashMap<>();
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
          List<String> cities = zipMap.getOrDefault(zip, Collections.emptyList());
          List<String> newCities = ListUtils
              .unmodifiableList(ListUtils.union(cities, Collections.singletonList(city)));
          zipMap.put(zip, newCities);
        }
        LOGGER.info("Found " + zipMap.size() + " zips");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public List<String> getCityByZIP(String zip) {
    return zipMap.getOrDefault(zip, new ArrayList<>());
  }

}
