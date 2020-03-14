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
		
		freemarkerConfig.setClassForTemplateLoading(MainApp.class, ApplicationProperties.getStringProperty(Constants.SP_TEMPLATEPATH));
		Spark.staticFileLocation(ApplicationProperties.getStringProperty(Constants.SP_PUBLICPATH));
		
		Template resultTemplate = freemarkerConfig.getTemplate(ApplicationProperties.getStringProperty(Constants.SP_TEMPLATEFILENAME));
		

		get("/", (req, res) -> Constants.SPARK_WELCOME_MESSAGE);
        get("/stop", (req, res) -> halt(401, Constants.SPARK_BYE_MESSAGE));
        get("/fallas2020", (req, res) -> {

			StringWriter writer = new StringWriter();
			Map<String, Object> root = new HashMap<String, Object>();

			logger.info("Search orden: " + req.queryParams("orden"));
			logger.info("Search parametro: " + req.queryParams("parametro"));

			try {
				root.put( "fallasMap", FallasMonumentsJsonParser.getListOfFallas(req.queryParams("orden")!=null?req.queryParams("orden"):null,
																				 req.queryParams("parametro")!=null?req.queryParams("parametro"):null));
				root.put( "jsonData", FallasMonumentsJsonParser.getJsonData());
																		 
				resultTemplate.process(root, writer);

			} catch (Exception ex) {
        		 logger.error ("Exception: " + ex.getClass() + " - " + ex.getMessage());
        	}
			return writer;
		});
    }
}