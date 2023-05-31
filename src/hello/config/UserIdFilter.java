package hello.config;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import hello.entity.User;

@Provider
public class UserIdFilter implements ContainerRequestFilter {

    @PersistenceContext(unitName = "MaPU")
    private EntityManager entityManager;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
    	if (true) {
    		return ;
    	}
    	
        // Check the path of the request
        String path = requestContext.getUriInfo().getPath();
        if (!path.startsWith("users/") && !path.startsWith("anotherPath/")) {
            // This filter does not apply to this path, so just return
            return;
        }

        // Get the X-User-ID header from the request
        String userIdHeader = requestContext.getHeaderString("X-User-ID");

        // If the X-User-ID header is not present, abort the request
        if (userIdHeader == null) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        // Convert the user ID to a Long
        Long userId;
        try {
            userId = Long.valueOf(userIdHeader);
        } catch (NumberFormatException e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        // Check if a user with this ID exists
        User user = entityManager.find(User.class, userId);
        if (user == null) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        // The user was found, proceed with the request
    }
}
