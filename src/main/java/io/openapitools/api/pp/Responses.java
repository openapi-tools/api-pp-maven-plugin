package io.openapitools.api.pp;

import java.util.Map;
import java.util.Set;

import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Response;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.StringProperty;

/**
 * Adds response documentation to an operation in an Open API manner
 */
public final class Responses {
    
    private Responses() {
        // intentionally empty
    }

    public static void addResponseCodes(Operation operation, Set codes) {
        if (codes.contains("200")) addOKResponse200(operation);
        if (codes.contains("400")) addBadRequestResponse400(operation);
        if (codes.contains("401")) addNotAuthorizedResponse401(operation);
        if (codes.contains("403")) addForbiddenResponse403(operation);
        if (codes.contains("404")) addNotFoundResponse404(operation);
        if (codes.contains("406")) addNotAcceptableResponse406(operation);
        if (codes.contains("409")) addConflictResponse409(operation);
        if (codes.contains("410")) addGoneResponse410(operation);
        if (codes.contains("412")) addPreconditionFailedResponse412(operation);
        if (codes.contains("415")) addUnsupportedContentTypeResponse415(operation);
        if (codes.contains("429")) addClientTooBusyResponse429(operation);
        if (codes.contains("500")) addServerErrorResponse500(operation);
        if (codes.contains("503")) addServerBusyResponse503(operation);
        if (codes.contains("505")) addUnsupportedHTTPVersionResponse505(operation);
    }

    public static void addVerbSpecificHeaders(Path p, Set<String> codes) { 
        if (null != p.getGet()) addGetResponses(p.getGet(), codes);
        if (null != p.getPut()) addPutResponses(p.getPut(), codes);
        if (null != p.getPost()) addPostResponses(p.getPost(), codes);
        if (null != p.getPatch()) addPatchResponses(p.getPatch(), codes);
        if (null != p.getDelete()) addDeleteResponses(p.getDelete(), codes);
    }
   
    private static void addGetResponses(Operation getOperation, Set<String> codes) {
        if (codes.contains("202")) addAcceptedResponse202(getOperation);
        if (codes.contains("203")) addNonAuthoritativeInformationResponse203(getOperation);
        if (codes.contains("301")) addPermanentlyMovedResponse301(getOperation);
        if (codes.contains("304")) addUnmodifiedResponse304(getOperation);
        if (codes.contains("307")) addTemporaryRedirectResponse307(getOperation);
        if (codes.contains("404")) addNotFoundResponse404(getOperation);
        if (codes.contains("410")) addGoneResponse410(getOperation);
        if (codes.contains("501")) addNotImplementedResponse501(getOperation);
    }
    
    private static void addPostResponses(Operation postOperation, Set<String> codes) {
        if (codes.contains("201")) addCreatedResponse201(postOperation);
        if (codes.contains("202")) addAcceptedResponse202(postOperation);
        if (codes.contains("301")) addPermanentlyMovedResponse301(postOperation);
        if (codes.contains("307")) addTemporaryRedirectResponse307(postOperation);
        if (codes.contains("410")) addGoneResponse410(postOperation);
        if (codes.contains("412")) addPreconditionFailedResponse412(postOperation);
        if (codes.contains("415")) addUnsupportedContentTypeResponse415(postOperation);
        if (codes.contains("429")) addClientTooBusyResponse429(postOperation);
        if (codes.contains("500")) addServerErrorResponse500(postOperation);
        if (codes.contains("501")) addNotImplementedResponse501(postOperation);
        if (codes.contains("503")) addServerBusyResponse503(postOperation);
        if (codes.contains("505")) addUnsupportedHTTPVersionResponse505(postOperation);
    }

    private static void addPutResponses(Operation putOperation, Set<String> codes) {
        if (codes.contains("201")) addCreatedResponse201(putOperation);
        if (codes.contains("202")) addAcceptedResponse202(putOperation);
        if (codes.contains("301")) addPermanentlyMovedResponse301(putOperation);
        if (codes.contains("307")) addTemporaryRedirectResponse307(putOperation);
        if (codes.contains("410")) addGoneResponse410(putOperation);
        if (codes.contains("412")) addPreconditionFailedResponse412(putOperation);
        if (codes.contains("415")) addUnsupportedContentTypeResponse415(putOperation);
        if (codes.contains("429")) addClientTooBusyResponse429(putOperation);
        if (codes.contains("500")) addServerErrorResponse500(putOperation);
        if (codes.contains("501")) addNotImplementedResponse501(putOperation);
        if (codes.contains("503")) addServerBusyResponse503(putOperation);
        if (codes.contains("505")) addUnsupportedHTTPVersionResponse505(putOperation);
    }

    private static void addDeleteResponses(Operation deleteOperation, Set<String> codes) {
        if (codes.contains("204")) addNoContentResponse204(deleteOperation);
    }

    private static void addPatchResponses(Operation patchOperation, Set<String> codes) {
        Headers.addPatchHeaders(patchOperation);
        if (codes.contains("422")) addUnprocessableRequestResponse422(patchOperation);
    }

    private static void addOKResponse200(Operation operation) {
        String key = "200";
        Map<String, Response> responses = operation.getResponses();
        Response response = new Response();
        if (!responses.containsKey(key)) {
            response.description("OK.");
        } else {
            response = operation.getResponses().get(key);
        }
        addLogTokenResponseHeader(response);
        addUsualResponseHeaders(response);
        operation.addResponse(key, response);
    }

    private static void addCreatedResponse201(Operation operation) {
        String key = "201";
        Map<String, Response> responses = operation.getResponses();
        Response response = new Response();
        if (!responses.containsKey(key)) {
            response.description("Resource Created.");
        } else {
            response = operation.getResponses().get(key);
        }
        addLocationResponseHeader(response);
        addLogTokenResponseHeader(response);
        addUsualResponseHeaders(response);
        operation.addResponse(key, response);
    }

    private static void addAcceptedResponse202(Operation operation) {
        String key = "202";
        Map<String, Response> responses = operation.getResponses();
        Response response = new Response();
        if (!responses.containsKey(key)) {
            response.description("Request accepted for further processing.");
        } else {
            response = operation.getResponses().get(key);
        }
        addLocationResponseHeader(response);
        addRetryAfterResponseHeader(response);
        addLogTokenResponseHeader(response);
        operation.addResponse(key, response);
    }

    private static void addNonAuthoritativeInformationResponse203(Operation operation) {
        String key = "203";
        Map<String, Response> responses = operation.getResponses();
        Response response = new Response();
        if (!responses.containsKey(key)) {
            response.description("Non Authoritative Information");
        } else {
            response = operation.getResponses().get(key);
        }
        addLogTokenResponseHeader(response);
        operation.addResponse(key, response);
    }


    private static void addNoContentResponse204(Operation operation) {
        String key = "204";
        Map<String, Response> responses = operation.getResponses();
        if (!responses.containsKey(key)) {
            Response response = new Response();
            response.description("Request accepted Nothing Returned.");
            addLogTokenResponseHeader(response);
            operation.addResponse(key, response);
        }
    }

    private static void addPermanentlyMovedResponse301(Operation operation) {
        String key = "301";
        Map<String, Response> responses = operation.getResponses();
        Response response = new Response();
        if (!responses.containsKey(key)) {
            response.description("Resource has moved.");
        } else {
            response = operation.getResponses().get(key);
        }
        addLocationResponseHeader(response);
        addLogTokenResponseHeader(response);
        addExpiresHeader(response);
        operation.addResponse(key, response);
    }

    private static void addUnmodifiedResponse304(Operation operation) {
        String key = "304";
        Map<String, Response> responses = operation.getResponses();
        Response response = new Response();
        if (!responses.containsKey(key)) {
            response.description("Not Modified - Resource was not updated");
        } else {
            response = operation.getResponses().get(key);
        }
        addLogTokenResponseHeader(response);
        operation.addResponse(key, response);

    }

    private static void addTemporaryRedirectResponse307(Operation operation) {
        String key = "307";
        Map<String, Response> responses = operation.getResponses();
        Response response = new Response();
        if (!responses.containsKey(key)) {
            response.description("Temporary Redirect - Resource is available shortly else where");
        } else {
            response = operation.getResponses().get(key);
        }
        addLocationResponseHeader(response);
        addLogTokenResponseHeader(response);
        addExpiresHeader(response);
        operation.addResponse(key, response);
    }

    private static void addBadRequestResponse400(Operation operation) {
        String key = "400";
        Response response = new Response();
        Map<String, Response> responses = operation.getResponses();
        if (!responses.containsKey(key)) {
            response.description("Bad Request - the contents of the request were semantically or syntactically wrong.");
        } else {
            response = operation.getResponses().get(key);
        }
        addLogTokenResponseHeader(response);
        operation.addResponse(key, response);
    }

    private static void addNotAuthorizedResponse401(Operation operation) {
        String key = "401";
        Map<String, Response> responses = operation.getResponses();
        Response response = new Response();
        if (!responses.containsKey(key)) {
            response.description("Not Authorized for the resource.");
        } else {
            response = operation.getResponses().get(key);
        }
        addLogTokenResponseHeader(response);
        operation.addResponse(key, response);
    }

    private static void addForbiddenResponse403(Operation operation) {
        String key = "403";
        Map<String, Response> responses = operation.getResponses();
        Response response = new Response();
        if (!responses.containsKey(key)) {
            response.description("Forbidden access to the resource.");
        } else {
            response = operation.getResponses().get(key);
        }
        addLogTokenResponseHeader(response);
        operation.addResponse(key, response);
    }

    private static void addNotFoundResponse404(Operation operation) {
        String key = "404";
        Map<String, Response> responses = operation.getResponses();
        Response response = new Response();
        if (!responses.containsKey(key)) {
            response.description("Resource Not Found");
        } else {
            response = operation.getResponses().get(key);
        }
        addLogTokenResponseHeader(response);
        operation.addResponse(key, response);
    }

    private static void addNotAcceptableResponse406(Operation operation) {
        String key = "406";
        Map<String, Response> responses = operation.getResponses();
        Response response = new Response();
        if (!responses.containsKey(key)) {
            response.description("Not Acceptable - Possible mismatch between headers and content");
        } else {
            response = operation.getResponses().get(key);
        }
        addLogTokenResponseHeader(response);
        operation.addResponse(key, response);
    }

    private static void addConflictResponse409(Operation operation) {
        String key = "409";
        Map<String, Response> responses = operation.getResponses();
        Response response = new Response();
        if (!responses.containsKey(key)) {
            response.description("Conflict - state of resource may have changed.");
        } else {
            response = operation.getResponses().get(key);
        }
        addLogTokenResponseHeader(response);
        operation.addResponse(key, response);
    }

    private static void addGoneResponse410(Operation operation) {
        String key = "410";
        Map<String, Response> responses = operation.getResponses();
        Response response = new Response();
        if (!responses.containsKey(key)) {
            response.description("Gone - resource is no longer available.");
        } else {
            response = operation.getResponses().get(key);
        }
        addLogTokenResponseHeader(response);
        operation.addResponse(key, response);
    }

    private static void addPreconditionFailedResponse412(Operation operation) {
        String key = "412";
        Map<String, Response> responses = operation.getResponses();
        Response response = new Response();
        if (!responses.containsKey(key)) {
            response.description("Precondition Failed - result from state of headers.");
        } else {
            response = operation.getResponses().get(key);
        }
        addLogTokenResponseHeader(response);
        operation.addResponse(key, response);
    }

    private static void addUnsupportedContentTypeResponse415(Operation operation) {
        String key = "415";
        Map<String, Response> responses = operation.getResponses();
        Response response = new Response();
        if (!responses.containsKey(key)) {
            response.description("Content-Type not supported by Resource");
        } else {
            response = operation.getResponses().get(key);
        }
        addLogTokenResponseHeader(response);
        operation.addResponse(key, response);
    }

    private static void addUnprocessableRequestResponse422(Operation operation) {
        String key = "422";
        Map<String, Response> responses = operation.getResponses();
        Response response = new Response();
        if (!responses.containsKey(key)) {
            response.description("Unprocessable Request - illegal modification of resource");
        } else {
            response = operation.getResponses().get(key);
        }
        addLogTokenResponseHeader(response);
        operation.addResponse(key, response);
    }

    private static void addClientTooBusyResponse429(Operation operation) {
        String key = "429";
        Map<String, Response> responses = operation.getResponses();
        Response response = new Response();
        if (!responses.containsKey(key)) {
            response.description("Too much load is added from the client side into the service and the client is requested " +
                "to limit the number of requests - as the limits has been reached");
        } else {
            response = operation.getResponses().get(key);
        }
        addRetryAfterResponseHeader(response);
        addLogTokenResponseHeader(response);
        operation.addResponse(key, response);
    }

    private static void addServerErrorResponse500(Operation operation) {
        String key = "500";
        Map<String, Response> responses = operation.getResponses();
        Response response = new Response();
        if (!responses.containsKey(key)) {
            response.description("The server experienced a currently unknown problem");
        } else {
            response = operation.getResponses().get(key);
        }
        addLogTokenResponseHeader(response);
        operation.addResponse(key, response);
    }

    private static void addNotImplementedResponse501(Operation operation) {
        String key = "501";
        Map<String, Response> responses = operation.getResponses();
        Response response = new Response();
        if (!responses.containsKey(key)) {
            response.description("This method is currently not implemented");
        } else {
            response = operation.getResponses().get(key);
        }
        addLogTokenResponseHeader(response);
        operation.addResponse(key, response);
    }

    private static void addServerBusyResponse503(Operation operation) {
        String key = "503";
        Map<String, Response> responses = operation.getResponses();
        Response response = new Response();
        if (!responses.containsKey(key)) {
            response.description("The service is unavailable");
        } else {
            response = operation.getResponses().get(key);
        }
        addLogTokenResponseHeader(response);
        addRetryAfterResponseHeader(response);
        operation.addResponse(key, response);
    }

    private static void addUnsupportedHTTPVersionResponse505(Operation operation) {
        String key = "505";
        Map<String, Response> responses = operation.getResponses();
        Response response = new Response();
        if (!responses.containsKey(key)) {
            response.description("HTTP Version not supported");
        } else {
            response = operation.getResponses().get(key);
        }
        addLogTokenResponseHeader(response);
        operation.addResponse(key, response);
    }

    private static void addLogTokenResponseHeader(Response response) {
        if (notSet(response, "X-Log-Token")) {
            setHeader(response, "X-Log-Token", "A Correlation ID for consumer use");
        }
    }

    private static void addLocationResponseHeader(Response response) {
        if (notSet(response, "Location")) {
            setHeader(response, "Location", "The Location is used to state where resource can be found");
        }
    }

    private static void addRetryAfterResponseHeader(Response response) {
        if (notSet(response, "Retry-After")) {
            setHeader(response, "Retry-After", "When can the resource be expected at the Location");
        }
    }

    private static void addUsualResponseHeaders(Response response) {
        if (notSet(response, "Content-Type")) {
            setHeader(response, "Content-Type",
                "The concrete content-type returned from service - save on client for future versioning of the particular endpoint");
        }
        if (notSet(response, "Cache-Control")) {
            setHeader(response, "Cache-Control", "The consumer caching information");
        }
        if (notSet(response, "ETag")) {
            setHeader(response, "ETag", "The entity tag");
        }
        addExpiresHeader(response);
        if (notSet(response, "Last-Modified")) {
            setHeader(response, "Last-Modified", "The information was changed at this time");
        }
        if (notSet(response, "Content-Encoding")) {
            setHeader(response, "Content-Encoding", "The concrete content-encoding service");
        }
        addLogTokenResponseHeader(response);
        addRateLimiting(response);
    }

    private static void addExpiresHeader(Response response) {
        if (notSet(response, "Expires")) {
            setHeader(response, "Expires", "The information expiry time");
        }
    }

    private static boolean notSet(Response response, String header) {
        return response.getHeaders() == null || !response.getHeaders().containsKey(header);
    }

    private static void addRateLimiting(Response response) {
        if (!response.getHeaders().containsKey("X-RateLimit-Limit")) {
            setHeader(response, "X-RateLimit-Limit", "X-RateLimit-Limit: Request limit per minute");
        }
        if (!response.getHeaders().containsKey("X-RateLimit-Limit-24h")) {
            setHeader(response, "X-RateLimit-Limit-24h", "X-RateLimit-Limit-24h: Request limit per 24h");
        }
        if (!response.getHeaders().containsKey("X-RateLimit-Remaining")) {
            setHeader(response, "X-RateLimit-Remaining",
                "X-RateLimit-Remaining: Requests left for the domain/resource for the 24h (locally determined)");
        }
        if (!response.getHeaders().containsKey("X-RateLimit-Reset")) {
            setHeader(response, "X-RateLimit-Reset",
                "X-RateLimit-Reset: The remaining window before the rate limit resets in UTC epoch seconds");
        }
    }

    private static void setHeader(Response response, String name, String description) {
        Property contentType = new StringProperty();
        contentType.setName(name);
        contentType.description(description);
        response.addHeader(name, contentType);
    }
    
}
