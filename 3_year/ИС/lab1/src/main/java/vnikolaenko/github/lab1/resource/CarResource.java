package vnikolaenko.github.lab1.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.NoArgsConstructor;
import vnikolaenko.github.lab1.network.dto.CarDTO;
import vnikolaenko.github.lab1.network.mapper.CarMapper;
import vnikolaenko.github.lab1.service.CarService;
import vnikolaenko.github.lab1.util.GlobalExceptionHandler;

import java.util.List;

@Path("/car")
@ApplicationScoped
@NoArgsConstructor
public class CarResource {
    @Inject
    private CarService carService;

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Integer id) {
        if (carService.delete(id)) return Response.ok().build();
        return Response.status(Response.Status.CONFLICT).entity(new GlobalExceptionHandler.ErrorResponse("Car is connected with other objects")).build();
    }

    @GET
    @Path("/get-all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<CarDTO> carDTOs = carService.getAll().stream()
                .map(CarMapper.INSTANCE::toDTO)
                .toList();
        return Response.ok(carDTOs).build();
    }
}
