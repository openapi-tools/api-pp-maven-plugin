package io.openapitools.pp;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Rule;
import org.junit.Test;

public class APIPostProcessorMojoIT {

    @Rule
    public MojoRule rule = new MojoRule();

    @Test
    public void testFullyConfigured() throws Exception {
        Path output = Paths.get("target/api");
        if (Files.exists(output)) {
            Files.walkFileTree(output, new DeleteVisitor());
        }
        File file = new File("src/test/resources/post-processor-mojo-pom-full-config.xml");
        Mojo mojo = rule.lookupMojo("postprocessor", file);
        mojo.execute();
        File yaml = new File("target/api/open-api-specs.yaml");
        assertTrue(yaml.exists());
        File json = new File("target/api/open-api-specs.json");
        assertTrue(json.exists());
    }

    @Test
    public void testMinimalConfig() throws Exception {
        Path output = Paths.get("target/open-api-minimal-config");
        if (Files.exists(output)) {
            Files.walkFileTree(output, new DeleteVisitor());
        }
        File file = new File("src/test/resources/post-processor-mojo-pom-minimal-config.xml");
        Mojo mojo = rule.lookupMojo("postprocessor", file);
        mojo.execute();
        File json = new File("target/open-api-minimal-config/open-api-post-processed-specification.json");
        assertTrue(json.exists());
    }

    @Test
    public void testStandardPostProcessed() throws Exception {
        Path output = Paths.get("target/api-standard");
        if (Files.exists(output)) {
            Files.walkFileTree(output, new DeleteVisitor());
        }
        File file = new File("src/test/resources/standard-post-processor-mojo-pom.xml");
        Mojo mojo = rule.lookupMojo("postprocessor", file);
        mojo.execute();
        File yaml = new File("target/api-standard/open-api-specs-standard.yaml");
        assertTrue(yaml.exists());
        File json = new File("target/api-standard/open-api-specs-standard.json");
        assertTrue(json.exists());
        String apiAsStr = new String(Files.readAllBytes(Paths.get("target/api-standard/open-api-specs-standard.yaml")));
        assertTrue("As this is a standard postprocessing 202 should be part of that", apiAsStr.contains("202"));
        assertTrue("As this is a standard postprocessing 505 should be part of that", apiAsStr.contains("505"));
    }

    @Test
    public void testMinimumPostProcessed() throws Exception {
        Path output = Paths.get("target/api-min");
        if (Files.exists(output)) {
            Files.walkFileTree(output, new DeleteVisitor());
        }
        File file = new File("src/test/resources/min-post-processor-mojo-pom.xml");
        Mojo mojo = rule.lookupMojo("postprocessor", file);
        mojo.execute();
        File yaml = new File("target/api-min/open-api-specs-minimum.yaml");
        assertTrue(yaml.exists());
        File json = new File("target/api-min/open-api-specs-minimum.json");
        assertTrue(json.exists());
        String apiAsStr = new String(Files.readAllBytes(Paths.get("target/api-min/open-api-specs-minimum.yaml")));
        assertTrue("As this is a min postprocessing 202 should be part of that", apiAsStr.contains("202"));
        assertFalse("As this is a min postprocessing 505 should not be part of that", apiAsStr.contains("505"));
    }

    private static class DeleteVisitor extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Files.delete(dir);
            return FileVisitResult.CONTINUE;
        }
    }
}
