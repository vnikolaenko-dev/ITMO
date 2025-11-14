package vnikolaenko.github.humanbeing.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vnikolaenko.github.humanbeing.application.CoordinatesService;


import java.util.List;

@Path("/coordinates")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({"admin", "user"})
public class CoordinatesResource {
    @Inject
    CoordinatesService coordinatesService;

    @GET
    @Path("/get-all")
    public Response getAll() {
        return Response.ok(coordinatesService.getAll()).build();
    }
}
