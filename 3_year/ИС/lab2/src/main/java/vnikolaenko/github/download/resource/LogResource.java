package vnikolaenko.github.download.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import vnikolaenko.github.download.application.LogService;

@ApplicationScoped
@Path("/log")
@RolesAllowed({"admin", "user"})
public class LogResource {
    @Inject
    JsonWebToken jwt;
    @Inject
    LogService logService;

    @GET
    public Response get() {
        if (jwt.getGroups().contains("admin")) {
            return Response.ok(logService.getLogs()).build();
        }
        String username = jwt.getName();
        return Response.ok(logService.getDownloadsByUsername(username)).build();
    }
}
