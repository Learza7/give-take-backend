package hello.controller;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import hello.entity.Article;
import hello.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Stateless
@Path("/articles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArticleController {

	@PersistenceContext(unitName = "MaPU")
	private EntityManager entityManager;

    @GET
    public List<Article> getAllArticles() {
        TypedQuery<Article> query = entityManager.createQuery("SELECT a FROM Article a", Article.class);
        return query.getResultList();
    }

    @GET
    @Path("/{id}")
    public Article getArticle(@PathParam("id") Long id) {
        return entityManager.find(Article.class, id);
    }

    
 // import other necessary packages

    @POST
    public Response createArticle(Article article, @HeaderParam("X-User-ID") Long userId) {
        User user = entityManager.find(User.class, userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        article.setUser(user);
        entityManager.persist(article);
        return Response.status(Response.Status.CREATED).build();
    }



    @PUT
    @Path("/{id}")
    public void updateArticle(@PathParam("id") Long id, Article article) {
        Article articleToUpdate = entityManager.find(Article.class, id);
        if (articleToUpdate != null) {
            articleToUpdate.setTitle(article.getTitle());
            articleToUpdate.setDescription(article.getDescription());
            entityManager.merge(articleToUpdate);
        }
    }

    @DELETE
    @Path("/{id}")
    public void deleteArticle(@PathParam("id") Long id) {
        Article article = entityManager.find(Article.class, id);
        if (article != null) {
            entityManager.remove(article);
        }
    }
}
