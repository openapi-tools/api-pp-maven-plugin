# API Post Processing Maven Plugin

The long story short: this module avoids annotation clutter in your code and gives you the ability to process your Open API specification
in your continous build or pipeline before piublishing your Open API specification. 

The plugin allows you to include resposes like 301 to allow for future changes to resources and e.g. 202 for 
deferred processing and so on.
 
 ## Status

This plugin is intended to use the [Swagger Core library](https://github.com/swagger-api/swagger-core) to extend
OpenAPI specification with request and reponse headers, reponse codes etc. in order for a build pipeline to extend 
REST services with defaults. 

  [![Build status](https://travis-ci.org/openapi-tools/api-pp-maven-plugin.svg?branch=master)](https://travis-ci.org/openapi-tools/api-pp-maven-plugin)

Module is ready as an initial version


# Where to apply

The idea is to minimize the annotation clutter in the code and postpone what you would always and automate that as a part of development.
Thus this could be used during continous integration or as a step in the continous delivery pipeline
 - e.g. as a step for making the Open API specification ready for consumers and having added the necessary items to the specification,
which makes it possible for the API to evolve whilst keeping consumers happy. The optimal situation is obtained if the content versioning
paradigm (having support for multiple versions in same endpoint) is used.

# Future
The initial version supports a rudimentary standard and mininmal collection of reponse and request codes and headers.
The module will be extended with a more fine grained support for individual request headers, response status codes and headers, if this is found useful. 

# Usage

To have Swagger generate the OpenAPI specifications as part of the build add in the plugin to the POM.

The standard collection of status codes and headers
```xml
<build>
  <plugins>
    ...
    <plugin>
      <groupId>io.openapitools.api.specification</groupId>
      <artifactId>api-pp-maven-plugin</artifactId>
      <configuration>
        <packages>
          <package>standard</package>
        </packages>
      </configuration>
    </plugin>
    ...
  </plugins>
</build>
```

This will run the generation in the verify lifecycle stage of the Maven build.

## Specifying Packages

The packages currently supported are:

    standard - adds (200, 201, 202, 203, 204, 301, 304, 307, 400, 401, 402, 403, 404, 406, 410, 412, 415, 422, 429, 500, 501, 503, 505 ....)
    minimum  - adds (200, 202, 204, 301, 304, 400, 415, 500) 
    
Not supported yet:

    individual - adding individual reponse codes and/or headers, but will be if enough people find it useful


### Full Configuration - Standard Post Processing

The fully populated configuration example giving the _standard_ post processing collection of the Open API. 

```xml
<build>
  <plugins>
    ...
        <plugin>
          <groupId>io.openapitools.swagger</groupId>
          <artifactId>api-pp-maven-plugin</artifactId>
          <configuration>
              <packages>
                  <package>standard</package>
              </packages>
              <inputDirectory>sample-api</inputDirectory>
              <inputFilename>petstore</inputFilename>
              <outputDirectory>target/api-standard</outputDirectory>
              <outputFilename>open-api-specs-standard</outputFilename>
              <outputFormats>
                  <outputFormat>JSON</outputFormat>
                  <outputFormat>YAML</outputFormat>
              </outputFormats>
          </configuration>
        </plugin>
    ...
  </plugins>
</build>
```

### Minimum Config - Standard Post processing

The min populated configuration example giving the _standard_ post processing collection of the Open API. 

```xml
<build>
  <plugins>
    ...
        <plugins>
           <plugin>
               <groupId>io.openapitools.swagger</groupId>
               <artifactId>api-pp-maven-plugin</artifactId>
               <configuration>
                   <inputDirectory>sample-api</inputDirectory>
                   <inputFilename>petstore</inputFilename>
                   <outputDirectory>target/open-api-minimal-config</outputDirectory>
               </configuration>
           </plugin>
   ...
  </plugins>
</build>
```

### Full Configuration - Minimum Post Processing

The fully populated configuration example giving the _minimal_ post processing collection of the Open API. 

```xml
<build>
  <plugins>
    ...
        <plugin>
           <groupId>io.openapitools.swagger</groupId>
           <artifactId>api-pp-maven-plugin</artifactId>
           <configuration>
               <packages>
                   <package>minimal</package>
               </packages>
               <inputDirectory>sample-api</inputDirectory>
               <inputFilename>petstore</inputFilename>
               <outputDirectory>target/api-min</outputDirectory>
               <outputFilename>open-api-specs-minimum</outputFilename>
               <outputFormats>
                   <outputFormat>JSON</outputFormat>
                   <outputFormat>YAML</outputFormat>
               </outputFormats>
           </configuration>
       </plugin>
    ...
  </plugins>
</build>
```

### Inidividual Codes Configuration - Post Processing

The fully populated configuration example giving the _minimal_ post processing collection of the Open API. 
 ```xml
 <build>
   <plugins>
     ... 
        <plugin>
             <groupId>io.openapitools.swagger</groupId>
             <artifactId>api-pp-maven-plugin</artifactId>
             <configuration>
                 <packages/>
                 <codes>
                     <code>200</code>
                     <code>201</code>
                     <code>202</code>
                     <code>204</code>
                     <code>301</code>
                     <code>304</code>
                     <code>400</code>
                     <code>415</code>
                     <code>500</code>
                 </codes>
                 <inputDirectory>sample-api</inputDirectory>
                 <inputFilename>petstore</inputFilename>
                 <outputDirectory>target/api-codes-min</outputDirectory>
                 <outputFilename>open-api-specs-codes-min</outputFilename>
                 <outputFormats>
                     <outputFormat>JSON</outputFormat>
                     <outputFormat>YAML</outputFormat>
                 </outputFormats>
             </configuration>
         </plugin>
    ...
   </plugins>
 </build>
 ```

## Deploying

The generated post processed OpenAPI specifications may be installed and deployed as Maven artifact.
 To enable this add the configuration parameter attachArtifact.

```xml
<plugin>
  <groupId>io.openapitools.api.specification</groupId>
  <artifactId>api-pp-maven-plugin</artifactId>
  <configuration>
    <attachArtifact>true</attachArtifact>
```
