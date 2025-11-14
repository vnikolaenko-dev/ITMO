package vnikolaenko.github.user.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vnikolaenko.github.user.application.UserService;

import java.util.Map;


@Path("/auth")
@PermitAll
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    UserService userService;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/login")
    public Response login(@FormParam("username") String username, @FormParam("password") String password) {
        String token = userService.login(username, password);
        return Response.ok(Map.of("token", token)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/register")
    public Response register(@FormParam("username") String username, @FormParam("password") String password, @FormParam("role") String role) {
        String token = userService.register(username, password, role);
        return Response.ok(Map.of("token", token)).build();
    }
}
