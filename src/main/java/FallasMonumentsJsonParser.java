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
  
  public static void getListOfFallas(final String filter) {

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
      Map <String, HashMap<String, Feature>> unsortedMap = new HashMap<String, HashMap<String, Feature>>();
      Map <String, HashMap<String, Feature>> sortedMap = new HashMap<String, HashMap<String, Feature>>();

      logger.info("Sorting results by " + filter);
      while(FallaIterator.hasNext()) {
        feature = (Feature)FallaIterator.next();

        if (("all").equals(filter)) {
          //unsortedMap.put(feature.getProperties().getNombre(), feature);
          if (!unsortedMap.containsKey(feature.getProperties().getNombre())) {
            unsortedMap.put(feature.getProperties().getNombre(), new HashMap<String, Feature>());
          }
          unsortedMap.get(feature.getProperties().getNombre()).put(feature.getProperties().getNombre(), feature);
        } else if (("section").equals(filter)) {
          if (!unsortedMap.containsKey(feature.getProperties().getSeccion())) {
            unsortedMap.put(feature.getProperties().getSeccion(), new HashMap<String, Feature>());
          }
          unsortedMap.get(feature.getProperties().getSeccion()).put(feature.getProperties().getNombre(), feature);
        } else if (("section_i").equals(filter)) {
          if (!unsortedMap.containsKey(fillWithZeros(feature.getProperties().getSeccionI(),2))) {
            unsortedMap.put(fillWithZeros(feature.getProperties().getSeccionI(),2), new HashMap<String, Feature>());
          }
          unsortedMap.get(fillWithZeros(feature.getProperties().getSeccionI(),2)).put(feature.getProperties().getNombre(), feature);
        } else {
          logger.error("Filter criteria (" + filter + ") invalid");
        }
     }
    //logger.info("UnSorted: " + unsortedMap);
    sortedMap = new TreeMap<String, HashMap<String, Feature>>(unsortedMap);
    Map <String, Feature> sortedSelectedMap;

    for (Map.Entry<String, HashMap<String, Feature>> entry : sortedMap.entrySet()) {
      logger.info("- " + entry.getKey() + " -");
      sortedSelectedMap = new TreeMap<String, Feature>(entry.getValue());
      for (Map.Entry<String, Feature> entrySelected : sortedSelectedMap.entrySet()) {
        logger.info("   > " + entrySelected.getKey());
      }
    }
  } catch (IOException e) {
    e.printStackTrace();
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