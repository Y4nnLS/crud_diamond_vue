package org.acme;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.jboss.logging.Logger;

import io.quarkus.security.Authenticated;

@Path("/movies")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {

    private static final Logger LOGGER = Logger.getLogger(MovieResource.class);

    private final MovieService movieService;

    public MovieResource(MovieService movieService) {
        this.movieService = movieService;
    }

    @GET
    public List<Movie> getAll() {
        return movieService.getAllMovies();
    }

    @POST
    @Path("/add")
    public Response create(Movie movie) {
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println("entrou create");
        LOGGER.info("Recebendo solicitação para criar filme: " + movie);
        movieService.addMovie(movie);
        return Response.status(Response.Status.CREATED).entity(movie).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Movie movie = movieService.getMovieById(id);
        if (movie != null) {
            return Response.ok(movie).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}/update")
    public Response update(@PathParam("id") Long id, Movie movie) {
        LOGGER.info("Recebendo solicitação para atualizar filme com ID " + id + ": " + movie);
        Movie updatedMovie = movieService.updateMovie(id, movie);
        if (updatedMovie != null) {
            return Response.ok(updatedMovie).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}/delete")
    @RolesAllowed("admin")
    public Response delete(@PathParam("id") Long id) {
        LOGGER.info("Recebendo solicitação para deletar filme com ID " + id);
        boolean deleted = movieService.deleteMovie(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
