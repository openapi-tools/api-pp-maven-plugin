package io.openapitools.api.pp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;

/**
 * Maven mojo to generate OpenAPI documentation document based on Swagger.
 */
@Mojo(name = "postprocessor", defaultPhase = LifecyclePhase.PACKAGE, requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME)
public class APIPostProcessorMojo extends AbstractMojo {

    /**
     * List of packages which states what sets of codes must be added to API resources. This is <i>not</i> recursive.
     */
    @Parameter
    private Set<String> packages;

    /**
     * List of codes which must be added to API resources. This is <i>not</i> recursive.
     */
    @Parameter
    private Set<String> codes;

    /**
     * Directory which contains the input specification. Default is "${project.build.directory}"
     */
    @Parameter(defaultValue = "${project.build.directory}")
    private File inputDirectory;

    /**
     * Filename for the existing specification to post process. Default is "open-api".
     */
    @Parameter(defaultValue = "open-api")
    private String inputFilename = "open-api";

    /**
     * Directory to contain generated specification. Default is "${project.build.directory}"
     */
    @Parameter(defaultValue = "${project.build.directory}")
    private File outputDirectory;

    /**
     * Filename to use for the generated specification. Default is "open-api-specification".
     */
    @Parameter(defaultValue = "open-api-post-processed-specification")
    private String outputFilename = "open-api-post-processed-specification";

    /**
     * Choosing the output format. Supports JSON or YAML.
     */
    @Parameter
    private Set<OutputFormat> outputFormats = Collections.singleton(OutputFormat.JSON);

    /**
     * Attach generated documentation as artifact to the Maven project. If true documentation will be deployed along with other artifacts.
     */
    @Parameter(defaultValue = "false")
    private boolean attachArtifact;

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;

    @Component
    private MavenProjectHelper projectHelper;
    
    private final String[] MINIMUM = {"200", "202", "204", "301", "400", "404", "415", "500"};
    private final String[] STANDARD = {"200", "201", "202", "203", "204", "301", "304", "307", 
        "400", "401", "403", "404", "406", "409", "410", "412", "415", "422", "429", 
        "500", "501", "503", "505"};


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        SwaggerParser swaggerParser = new SwaggerParser();
        String fileAndPathName = inputDirectory + "/" + inputFilename;
        fileAndPathName = findFileFormat(fileAndPathName);
        Swagger api = swaggerParser.read(fileAndPathName);

        if (api == null) {
            throw new MojoFailureException("It was not possible to find input API specification at "
                    + fileAndPathName + " with  extensions json, yml or yaml");
        }
        
        if (null == packages && null == codes) {
            applySpecificHeadersAndResponses(api, new HashSet<>(Arrays.asList(STANDARD)));
        } else {
            if (null != packages && packages.contains("standard")) {
                applySpecificHeadersAndResponses(api, new HashSet<>(Arrays.asList(STANDARD)));
            } else if (null != packages && packages.contains("minimal")) {
                applySpecificHeadersAndResponses(api, new HashSet<>(Arrays.asList(MINIMUM)));
            } else if (null != codes  && codes.size() > 0) {
                applySpecificHeadersAndResponses(api, codes);
            }
        }

        if (outputDirectory.mkdirs()) {
            getLog().debug("Created output directory " + outputDirectory);
        }
       
        outputFormats.forEach(format
                -> {
            try {
                File outputFile = new File(outputDirectory, outputFilename + "." + format.name().toLowerCase());
                format.write(api, outputFile);
                if (attachArtifact) {
                    projectHelper.attachArtifact(project, format.name().toLowerCase(), "OpenAPI-Specification", outputFile);
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to write " + outputFilename + " document", e);
            }
        }
        );
    }

    private String findFileFormat(final String fileAndPathName) {
        return Arrays.asList(fileAndPathName + ".json", fileAndPathName + ".yaml", fileAndPathName + ".yml").stream()
            .filter(ffn -> Files.exists(Paths.get(ffn)))
            .findAny()
            .orElse(fileAndPathName);
    }
    
    private void applySpecificHeadersAndResponses(Swagger api, Set<String> codes) {
        Map<String, Path> paths = api.getPaths();
        paths.forEach((k, p) -> {
            List<Operation> operations = p.getOperations();
            operations.forEach(operation -> {
                Responses.addResponseCodes(operation, codes);
                Headers.addStandardParameters(operation);
                Responses.addVerbSpecificHeaders(p, codes);
            });
        });
    }

}
