package hello.controller;

import javax.ws.rs.*;
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
        entityManager.persist(user);
        return Response.status(Response.Status.CREATED).entity(user).build();
    }
}
