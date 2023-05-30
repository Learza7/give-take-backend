package hello.config;

import java.io.IOException;
import java.util.Base64;
import java.util.StringTokenizer;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
//@Priority(Priorities.AUTHENTICATION)
public class BasicAuthFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
    	
    	if (true) {
    		return ;
    	}
    	
    	if (requestContext.getUriInfo().getPath().equals("/users") && requestContext.getMethod().equals("POST")) {
            // If it's for user creation, skip the authentication
            return;
        }
    	
        // Get the Authorization header from the request
        String authorizationHeader = 
            requestContext.getHeaderString("Authorization");
            //getHeaderString(HttpHeaders.AUTHORIZATION);
        System.out.println(requestContext.getHeaders());
        // Validate the Authorization header
        if (authorizationHeader == null || !authorizationHeader.startsWith("Basic ")) {
            throw new NotAuthorizedException("Authorization header must be provided : " + authorizationHeader );
        }

        // Extract the token from the Authorization header
        String token = authorizationHeader.substring("Basic".length()).trim();

        try {
            // Decode the token
            String decodedString = Base64.getDecoder().decode(token).toString();
            StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");

            // Extract the username and password
            String username = tokenizer.nextToken();
            String password = tokenizer.nextToken();

            // Validate the extracted username and password
            // NOTE: You should have your own user validation mechanism.
            if (!validateUser(username, password)) {
                throw new NotAuthorizedException("Invalid username or password");
            }
        } catch (Exception e) {
            requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private boolean validateUser(String username, String password) {
        // TODO: Validate the user using the username and password
        return true;
    }
}
