import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import model.FallaMonumentsData;
import model.Feature;
import utils.ApplicationProperties;

public class FallasMonumentsJsonParser {
	
	// Logger
	private static Logger logger = LoggerFactory.getLogger(FallasMonumentsJsonParser.class);
  
  public static Map <String, HashMap<String, Feature>> getListOfFallas(final String filter, final String urlParameter) {

//    Map <String, Feature> sortedSelectedMap;
    Map <String, HashMap<String, Feature>> unsortedMap = new HashMap<String, HashMap<String, Feature>>();
    Map <String, HashMap<String, Feature>> sortedMap = null;

    logger.info("Application started");
    
    // Load properties from file
    ApplicationProperties.loadApplicationProperties ();
      
    Gson gson = new Gson();

    try {

      // Convert JSON File to Java Object
      logger.info("Reading data from " + ApplicationProperties.getStringProperty("dataset.url"));
      FallaMonumentsData fallaMonumentsData = gson.fromJson(readJsonFromUrl(ApplicationProperties.getStringProperty("dataset.url")), FallaMonumentsData.class);
      logger.info("Total # of Fallas read: " + fallaMonumentsData.getFeatures().size());

      Iterator<Feature> FallaIterator = fallaMonumentsData.getFeatures().iterator();

      Feature feature;

      logger.info("Sorting results by " + filter + " and parameter " + urlParameter);

      while(FallaIterator.hasNext()) {
        feature = (Feature)FallaIterator.next();

        logger.info("Seccion: " + feature.getProperties().getSeccion());

        if (checkFilter(filter, feature)) {

        }

        if (("all").equals(filter)) {
          //unsortedMap.put(feature.getProperties().getNombre(), feature);
          if (!unsortedMap.containsKey(feature.getProperties().getNombre())) {
            unsortedMap.put(feature.getProperties().getNombre(), new HashMap<String, Feature>());
          }
          unsortedMap.get(feature.getProperties().getNombre()).put(feature.getProperties().getNombre(), feature);
        } else if (("section").equals(filter)) {
          if ((urlParameter == null || ((urlParameter != null) && (feature.getProperties().getSeccion() != null) && ((feature.getProperties().getSeccion()).startsWith(urlParameter))))) {
            if (!unsortedMap.containsKey(feature.getProperties().getSeccion())) {
              unsortedMap.put(feature.getProperties().getSeccion(), new HashMap<String, Feature>());
            }
            unsortedMap.get(feature.getProperties().getSeccion()).put(feature.getProperties().getNombre(), feature);
          }
        } else if (("section_i").equals(filter)) {
          if (!unsortedMap.containsKey(fillWithZeros(feature.getProperties().getSeccionI(),2))) {
            unsortedMap.put(fillWithZeros(feature.getProperties().getSeccionI(),2), new HashMap<String, Feature>());
          }
          unsortedMap.get(fillWithZeros(feature.getProperties().getSeccionI(),2)).put(feature.getProperties().getNombre(), feature);
        } else {
          logger.error("Filter criteria (" + filter + ") invalid");
        }
     }
    logger.info("UnSorted: " + unsortedMap);
    sortedMap = new TreeMap<String, HashMap<String, Feature>>(unsortedMap);

    for (Map.Entry<String, HashMap<String, Feature>> entry : sortedMap.entrySet()) {
      logger.info("- " + entry.getKey() + " -");
      //sbResult.append("- " + entry.getKey() + " -");
      //sortedSelectedMap = new TreeMap<String, Feature>(entry.getValue());
      sortedMap.replace(entry.getKey(), entry.getValue());
      /*
      for (Map.Entry<String, Feature> entrySelected : sortedSelectedMap.entrySet()) {
        logger.info("   > " + entrySelected.getKey());
        sbResult.append("   > " + entrySelected.getKey());
      }
      */
    }
  } catch (Exception e) {
    logger.error("Exception: " + e.getClass() + " - " + e.getMessage());
  }
  logger.info("# Results: " + sortedMap.size());
  return sortedMap;
}

public static boolean checkFilter (final String filter, final Feature feature) {

  boolean result = false;

  if (("all").equals(filter)) {
  } else if (("section").equals(filter)) {
    if ((urlParameter == null || ((urlParameter != null) && (feature.getProperties().getSeccion() != null) && ((feature.getProperties().getSeccion()).startsWith(urlParameter))))) {
      if (!unsortedMap.containsKey(feature.getProperties().getSeccion())) {
        unsortedMap.put(feature.getProperties().getSeccion(), new HashMap<String, Feature>());
      }
      unsortedMap.get(feature.getProperties().getSeccion()).put(feature.getProperties().getNombre(), feature);
    }
  } else if (("section_i").equals(filter)) {
  }
}


public static String readJsonFromUrl(String url) throws IOException {
  InputStream is = new URL(url).openStream();
  try {
    BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
    return readAll(rd);
  } finally {
    is.close();
  }
}

private static String readAll(Reader rd) throws IOException {
  StringBuilder sb = new StringBuilder();
  int cp;
  while ((cp = rd.read()) != -1) {
    sb.append((char) cp);
  }
  return sb.toString();
}

public static String fillWithZeros (String inputString, int length) {
  if (inputString.length() >= length) {
    return inputString;
  }
  StringBuilder sb = new StringBuilder();
  while (sb.length() < length - inputString.length()) {
    sb.append('0');
  }
  sb.append(inputString);
  return sb.toString();
  }
}