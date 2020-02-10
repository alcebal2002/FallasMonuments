import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import model.FallaMonumentsData;
import model.Feature;
import utils.ApplicationProperties;

public class FallasMonumentsJsonParser {
	
	// Logger
	private static Logger logger = LoggerFactory.getLogger(FallasMonumentsJsonParser.class);
	
    public static void main(String[] args) {

    	logger.info("Application started");
    	
		// Load properties from file
		ApplicationProperties.loadApplicationProperties ();
    	
        Gson gson = new Gson();

        try {

            // Convert JSON File to Java Object
            FallaMonumentsData fallaMonumentsData = gson.fromJson(readJsonFromUrl(ApplicationProperties.getStringProperty("dataset.url")), FallaMonumentsData.class);

            logger.info("Number of Fallas: " + fallaMonumentsData.getFeatures().size());
            
            Iterator<Feature> FallaIterator = fallaMonumentsData.getFeatures().iterator();
            while(FallaIterator.hasNext()) {
            	
            	Feature feature = (Feature)FallaIterator.next();
            	
            	logger.info("Falla: " + feature.getProperties().getNombre() + " - Section: " + feature.getProperties().getSeccion());
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

}