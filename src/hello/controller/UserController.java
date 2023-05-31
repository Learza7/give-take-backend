package hello.controller;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import hello.entity.User;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

	@PersistenceContext(unitName = "MaPU")
	private EntityManager entityManager;



    @GET
    public Response getAllUsers() {
    	//return Response.ok("ok").build();
        List<User> users = entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
        return Response.ok(users).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        // Check if a user with the same email already exists
        List<User> existingUsers = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", user.getEmail())
                .getResultList();

        if (!existingUsers.isEmpty()) {
            // A user with this email already exists
            return Response.status(Response.Status.BAD_REQUEST).entity("A user with this email already exists.").build();
        } else {
            entityManager.persist(user);
            entityManager.flush();  // Flush to get the ID of the newly created user
            return Response.status(Response.Status.CREATED).entity(user.getId()).build();
        }
    }
    
    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUser(User user) {
        // Check if the user exists
        List<User> existingUsers = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password", User.class)
                .setParameter("email", user.getEmail())
                .setParameter("password", user.getPassword()) // This should be hashed and salted in a real application
                .getResultList();

        if (existingUsers.isEmpty()) {
            // No user with this email/password was found
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid email/password.").build();
        } else {
            // The user was found, return the user ID
            return Response.ok(existingUsers.get(0).getId()).build();
        }
    }

    @Path("/logout")
    @POST
    public Response logoutUser() {
        // In this case, we don't invalidate the session as we're not using it
        return Response.ok().build();
    }
    
    @Path("/me")
    @GET
    public Response getUser(@HeaderParam("X-User-ID") Long id) {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(user).build();
    }

}
