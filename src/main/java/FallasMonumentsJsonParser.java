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
  
  public static Map <String, HashMap<String, Feature>> getListOfFallas(final String urlOrder, final String urlParameter) {

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

      logger.info("Results order by " + urlOrder + " with parameter " + urlParameter);

      if (checkFilter(urlOrder, urlParameter, null)) {
        while(FallaIterator.hasNext()) {
          feature = (Feature)FallaIterator.next();
  
          if (checkFilter(urlOrder, urlParameter, feature)) {
            if (("nombre").equals(urlOrder.toLowerCase())) {
              if (!unsortedMap.containsKey(feature.getProperties().getNombre())) {
                unsortedMap.put(feature.getProperties().getNombre(), new HashMap<String, Feature>());
              }
              unsortedMap.get(feature.getProperties().getNombre()).put(feature.getProperties().getNombre(), feature);
            } else if (("seccion").equals(urlOrder.toLowerCase())) {
              if (!unsortedMap.containsKey(feature.getProperties().getSeccion())) {
                unsortedMap.put(feature.getProperties().getSeccion(), new HashMap<String, Feature>());
              }
              unsortedMap.get(feature.getProperties().getSeccion()).put(feature.getProperties().getNombre(), feature);
            } else if (("seccion_infantil").equals(urlOrder.toLowerCase())) {
              if (!unsortedMap.containsKey(fillWithZeros(feature.getProperties().getSeccionI(),2))) {
                unsortedMap.put(fillWithZeros(feature.getProperties().getSeccionI(),2), new HashMap<String, Feature>());
              }
              unsortedMap.get(fillWithZeros(feature.getProperties().getSeccionI(),2)).put(feature.getProperties().getNombre(), feature);
            }
          }
        }
      } else {
        logger.error ("Malformed URL. orden parameter is mandatory and should be valid (nombre, seccion, seccion_infantil)");
      }

    sortedMap = new TreeMap<String, HashMap<String, Feature>>(unsortedMap);

    for (Map.Entry<String, HashMap<String, Feature>> entry : sortedMap.entrySet()) {
      logger.info("- " + entry.getKey() + " -");
      sortedMap.replace(entry.getKey(), entry.getValue());
    }
  } catch (Exception e) {
    logger.error("Exception: " + e.getClass() + " - " + e.getMessage());
  }
  logger.info("# Results: " + sortedMap.size());
  return sortedMap;
}

public static boolean checkFilter (final String urlOrder, final String urlParam, final Feature feature) {

  boolean result = false;

  // No feature means the checkFilter is just to check valid orden parameter is passed
  if (feature == null) {
    if (urlOrder != null && ("nombre".equals(urlOrder) || "seccion".equals(urlOrder) || "seccion_infantil".equals(urlOrder))) {
      result = true;
    }
  
    // If feature is passed, it means the checkFilter has to validate all the possible combinations
    // A valid orden parameter has to be present
  } else {
    if ((urlParam == null) ||
        ((urlParam != null) && (
          ((("nombre").equals(urlOrder.toLowerCase())) && (feature.getProperties().getNombre() != null) && ((feature.getProperties().getNombre().toLowerCase()).contains(urlParam.toLowerCase()))) ||
          ((("seccion").equals(urlOrder.toLowerCase())) && (feature.getProperties().getSeccion() != null) && ((feature.getProperties().getSeccion().toLowerCase()).contains(urlParam.toLowerCase()))) ||
          ((("seccion_infantil").equals(urlOrder.toLowerCase())) && (feature.getProperties().getSeccionI() != null) && ((feature.getProperties().getSeccionI().toLowerCase()).contains(urlParam.toLowerCase())))
        ))
      ) {
        result = true;
      }
    }
    return result;
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