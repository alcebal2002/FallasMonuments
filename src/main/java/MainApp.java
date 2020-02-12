import static spark.Spark.get;
import static spark.Spark.halt;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Spark;
import freemarker.template.Configuration;
import freemarker.template.Template;

import utils.ApplicationProperties;
import utils.Constants;

public class MainApp {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(MainApp.class);
	
	@SuppressWarnings("deprecation")
	private static Configuration freemarkerConfig = new Configuration();

	public static void main(String[] args) throws Exception {
		
		logger.info("Starting SPARK REST Framework");

		// Load properties from file
		ApplicationProperties.loadApplicationProperties ();
		
		Spark.staticFileLocation(ApplicationProperties.getStringProperty(Constants.SP_PUBLICPATH));
		
		get("/", (req, res) -> Constants.SPARK_WELCOME_MESSAGE);
        get("/stop", (req, res) -> halt(401, Constants.SPARK_BYE_MESSAGE));
        get("/monitor", (req, res) -> {
        	StringWriter writer = new StringWriter();

        	try {
        		
        		Map<String, Object> root = new HashMap<String, Object>();
        		root.put( "Key", "Value" );
				Template resultTemplate = freemarkerConfig.getTemplate(ApplicationProperties.getStringProperty(Constants.SP_TEMPLATEFILENAME));
				resultTemplate.process(root, writer);
    		} catch (Exception ex) {
        		 logger.error ("Exception: " + ex.getClass() + " - " + ex.getMessage());
        	}
			return writer;
        });
    }
}