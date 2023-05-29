package hello.controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import hello.entity.Message;
import hello.entity.User;



@Stateless
@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageController {
		
	@PersistenceContext(unitName = "MaPU")
	private EntityManager entityManager;
	
	 @GET
	    public Response getAllUsers() {
	    	//return Response.ok("ok").build();
	        List<Message> messages = entityManager.createQuery("SELECT m FROM Message m", Message.class).getResultList();
	        return Response.ok(messages).build();
	    }

    
}