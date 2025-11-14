package vnikolaenko.github.car.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vnikolaenko.github.car.resource.dto.CarDto;
import vnikolaenko.github.car.infrastracture.mapper.CarMapper;
import vnikolaenko.github.car.application.CarService;


import java.util.List;

@Path("/car")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({"admin", "user"})
public class CarResource {
    @Inject
    CarService carService;
    @Inject
    CarMapper carMapper;

    @POST
    @Path("/create")
    public Response save(CarDto car) {
        carService.save(carMapper.toDomain(car));
        return Response.noContent().build();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Long id) {
        carService.deleteById(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/get-all")
    public Response getAll() {
        List<CarDto> carDTOs = carService.getAll().stream()
                .map(carMapper::entityToDTO)
                .toList();
        return Response.ok(carDTOs).build();
    }
}
