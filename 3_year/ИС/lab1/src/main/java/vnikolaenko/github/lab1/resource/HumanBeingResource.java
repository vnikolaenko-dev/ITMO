package vnikolaenko.github.lab1.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.NoArgsConstructor;
import vnikolaenko.github.lab1.model.obj.HumanBeing;
import vnikolaenko.github.lab1.model.obj.WeaponType;
import vnikolaenko.github.lab1.network.mapper.HumanBeingMapper;
import vnikolaenko.github.lab1.service.HumanBeingService;
import vnikolaenko.github.lab1.util.GlobalExceptionHandler;

@Path("/human-being")
@ApplicationScoped
@NoArgsConstructor
public class HumanBeingResource {
    @Inject
    private HumanBeingService humanBeingService;


    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HumanBeing humanBeing) {
        try {
            if (humanBeingService.save(humanBeing)) {
                return Response.ok().build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to save humanBeing").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new GlobalExceptionHandler.ErrorResponse("Error: " + e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Integer id) {
        if (humanBeingService.delete(id)) return Response.ok().build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("/delete-with-impact-speed/{speed}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteWithImpactSpeed(@PathParam("speed") Integer speed) {
        if (humanBeingService.deleteWithImpactSpeed(speed)) return Response.ok("HumanBeing were deleted").build();
        return Response.ok("No HumanBeings found").build();
    }


    @DELETE
    @Path("/delete-without-scraper")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteWithoutScraper() {
        int deleted = humanBeingService.deleteHumanBeingWithoutScraper();
        return Response.ok(deleted + " HumanBeings were deleted").build();
    }

    @GET
    @Path("/avg-speed")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvgSpeed() {
        return Response.ok(humanBeingService.avgImpactSpeed()).build();
    }


    @GET
    @Path("/give-lada")
    @Produces(MediaType.APPLICATION_JSON)
    public Response giveLada() {
        humanBeingService.assignRedLadaToHumansWithoutCar();
        return Response.ok().build();
    }

    @GET
    @Path("/get-all-weapon/{weapon}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByWeaponTypeLessThan(@PathParam("weapon") String weapon) {
        return Response.ok(humanBeingService.findByWeaponTypeLessThan(WeaponType.valueOf(weapon)).stream()
                .map(HumanBeingMapper.INSTANCE::toDTO)
                .toList()).build();
    }

    @GET
    @Path("/get-all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("page") @DefaultValue("0") int page,
                           @QueryParam("size") @DefaultValue("20") int size,
                           @QueryParam("sort") @DefaultValue("id") String sort,
                           @QueryParam("order") @DefaultValue("ASC") String order,
                           @QueryParam("nameFilter") @DefaultValue("null") String name) {
        return Response.ok(humanBeingService.getAllWithOrder(page, size, sort, order, name).stream()
                .map(HumanBeingMapper.INSTANCE::toDTO)
                .toList()).build();
    }
}
