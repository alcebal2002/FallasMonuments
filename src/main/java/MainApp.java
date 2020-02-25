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
        get("/fallas2020All", (req, res) -> {

			StringWriter writer = new StringWriter();
			Map<String, Object> root = new HashMap<String, Object>();

			try {
				root.put( "fallasMap", FallasMonumentsJsonParser.getListOfFallas("all"));
//				writer.write(FallasMonumentsJsonParser.getListOfFallas("all"));
				resultTemplate.process(root, writer);

			} catch (Exception ex) {
        		 logger.error ("Exception: " + ex.getClass() + " - " + ex.getMessage());
        	}
			return writer;
        });
        get("/fallas2020Seccion", (req, res) -> {

			StringWriter writer = new StringWriter();
			Map<String, Object> root = new HashMap<String, Object>();
        	try {
				root.put( "fallasMap", FallasMonumentsJsonParser.getListOfFallas("section"));
//				writer.write(FallasMonumentsJsonParser.getListOfFallas("section"));
				resultTemplate.process(root, writer);
    		} catch (Exception ex) {
        		 logger.error ("Exception: " + ex.getClass() + " - " + ex.getMessage());
        	}
			return writer;
        });
        get("/fallas2020SeccionInfantil", (req, res) -> {

			StringWriter writer = new StringWriter();
			Map<String, Object> root = new HashMap<String, Object>();

        	try {
				root.put( "fallasMap", FallasMonumentsJsonParser.getListOfFallas("section_i"));
//				writer.write(FallasMonumentsJsonParser.getListOfFallas("section_i"));
				resultTemplate.process(root, writer);
    		} catch (Exception ex) {
        		 logger.error ("Exception: " + ex.getClass() + " - " + ex.getMessage());
        	}
			return writer;
        });
    }
}