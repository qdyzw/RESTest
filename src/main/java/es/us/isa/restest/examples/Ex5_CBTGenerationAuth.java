package es.us.isa.restest.examples;

import es.us.isa.restest.generators.ConstraintBasedTestCaseGenerator;
import es.us.isa.restest.runners.RESTestLoader;
import es.us.isa.restest.testcases.TestCase;
import es.us.isa.restest.util.RESTestException;
import es.us.isa.restest.writers.restassured.RESTAssuredWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

import static es.us.isa.restest.util.FileManager.createDir;

/**
 * This example demonstrates the generation of test cases for a REST API with authentication parameters,
 * applying the Constraint-Based test case generation approach and utilizing configuration options.
 *
 * - Specify the configuration options in the user properties file located at "src/main/resources/Examples/Ex5_CBTGenerationAuth/youtube_getVideos.properties".
 * - The test cases are generated by randomly selecting values for each input parameter, ensuring the satisfaction of inter-parameter dependencies using a constraint solver (Choco) and IDL.
 * - The generated test cases are written to a file using the RESTAssured writer.
 * - For more details on handling inter-parameter dependencies in REST APIs with IDL, refer to the provided resources.
 *
 * The resources for this example are located at src/main/resources/Examples/Ex5_CBTGenerationAuth.
 */
public class Ex5_CBTGenerationAuth {

    public static final String PROPERTY_FILE_PATH = "src/main/resources/Examples/Ex5_CBTGenerationAuth/youtube_getVideos.properties"; 		// Path to user properties file with configuration options

    private static final Logger logger = LogManager.getLogger(Ex5_CBTGenerationAuth.class.getName());


    public static void main(String[] args) throws RESTestException {

        // Load properties
        RESTestLoader loader = new RESTestLoader(PROPERTY_FILE_PATH);

        // Create test case generator
        ConstraintBasedTestCaseGenerator generator = (ConstraintBasedTestCaseGenerator) loader.createGenerator();
        Collection<TestCase> testCases = generator.generate();

        // Create target directory for test cases if it does not exist
        createDir(loader.getTargetDirJava());

        // Write (RestAssured) test cases
        RESTAssuredWriter writer = (RESTAssuredWriter) loader.createWriter();
        writer.write(testCases);

        logger.info(testCases.size() + " test cases generated and written to " + loader.getTargetDirJava());

    }
}
