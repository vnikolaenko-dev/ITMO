package vnikolaenko.github.lab1.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.NoArgsConstructor;
import vnikolaenko.github.lab1.model.User;
import vnikolaenko.github.lab1.service.UserService;
import vnikolaenko.github.lab1.util.GlobalExceptionHandler;

import static jakarta.ws.rs.client.Entity.entity;

@Path("/auth")
@ApplicationScoped
@NoArgsConstructor
public class AuthorizationResource {
    @Inject
    private UserService userService;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User user) {
        if (userService.login(user)) return Response.ok().build();
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(new GlobalExceptionHandler.ErrorResponse("Username and password are required"))
                .build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(User user) {
        if (userService.register(user)) return Response.ok().build();
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(new GlobalExceptionHandler.ErrorResponse("Something went wrong"))
                .build();
    }
}
