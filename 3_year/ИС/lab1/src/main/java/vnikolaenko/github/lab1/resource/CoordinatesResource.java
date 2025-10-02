package vnikolaenko.github.lab1.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.NoArgsConstructor;
import vnikolaenko.github.lab1.network.dto.CoordinatesDTO;
import vnikolaenko.github.lab1.network.mapper.CoordinatesMapper;
import vnikolaenko.github.lab1.service.CoordinatesService;
import vnikolaenko.github.lab1.util.GlobalExceptionHandler;

import java.util.List;

@Path("/coordinates")
@ApplicationScoped
@NoArgsConstructor
public class CoordinatesResource {
    @Inject
    private CoordinatesService coordinatesService;

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Integer id) {
        if (coordinatesService.delete(id)) return Response.ok().build();
        return Response.status(Response.Status.BAD_REQUEST).entity(new GlobalExceptionHandler.ErrorResponse("Coordinates is connected with other objects")).build();
    }


    @GET
    @Path("/get-all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<CoordinatesDTO> carDTOs = coordinatesService.getAll().stream()
                .map(CoordinatesMapper.INSTANCE::toDTO)
                .toList();
        return Response.ok(carDTOs).build();
    }
}
