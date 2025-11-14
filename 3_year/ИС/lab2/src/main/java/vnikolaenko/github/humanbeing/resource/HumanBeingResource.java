package vnikolaenko.github.humanbeing.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.NoArgsConstructor;
import vnikolaenko.github.humanbeing.infrastracture.entity.HumanBeingEntity;
import vnikolaenko.github.humanbeing.resource.dto.HumanBeingDTO;
import vnikolaenko.github.humanbeing.valueobject.WeaponType;
import vnikolaenko.github.humanbeing.infrastracture.mapper.HumanBeingMapper;
import vnikolaenko.github.humanbeing.application.HumanBeingService;


@Path("/human-being")
@ApplicationScoped
@NoArgsConstructor
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({"admin", "user"})
public class HumanBeingResource {
    @Inject
    HumanBeingService humanBeingService;
    @Inject
    HumanBeingMapper humanBeingMapper;


    @POST
    @Path("/save")
    public Response save(HumanBeingDTO humanBeingDTO, @QueryParam("carId") @DefaultValue("null") Long carId) {
        humanBeingService.save(humanBeingMapper.toDomain(humanBeingDTO), carId);
        return Response.ok().build();
    }

    @POST
    @Path("/update")
    public Response update(HumanBeingDTO humanBeingDTO, @QueryParam("carId") @DefaultValue("null") Long carId) {
        humanBeingService.update(humanBeingMapper.toDomain(humanBeingDTO), carId);
        return Response.ok().build();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Long id) {
        humanBeingService.deleteById(id);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/delete-with-impact-speed/{speed}")
    public Response deleteWithImpactSpeed(@PathParam("speed") Integer speed) {
        humanBeingService.deleteWithImpactSpeed(speed);
        return Response.noContent().build();
    }


    @DELETE
    @Path("/delete-without-scraper")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteWithoutScraper() {
        Long deleted = humanBeingService.deleteHumanBeingWithoutScraper();
        return Response.ok(deleted + " HumanBeings were deleted").build();
    }

    @GET
    @Path("/avg-speed")
    public Response getAvgSpeed() {
        return Response.ok(humanBeingService.avgImpactSpeed()).build();
    }


    @GET
    @Path("/give-lada")
    public Response giveLada() {
        humanBeingService.assignRedLadaToHumansWithoutCar();
        return Response.ok().build();
    }

    @GET
    @Path("/get-all-weapon/{weapon}")
    public Response getByWeaponTypeLessThan(@PathParam("weapon") String weapon) {
        return Response.ok(humanBeingService.findByWeaponTypeLessThan(WeaponType.valueOf(weapon)).stream()
                .map(humanBeingMapper::entityToDTO)
                .toList()).build();
    }

    @GET
    @Path("/get-all")
    public Response getAll(@QueryParam("page") @DefaultValue("0") int page,
                           @QueryParam("size") @DefaultValue("20") int size,
                           @QueryParam("sort") @DefaultValue("id") String sort,
                           @QueryParam("order") @DefaultValue("ASC") String order,
                           @QueryParam("nameFilter") @DefaultValue("null") String name) {
        return Response.ok(humanBeingService.getAllWithOrder(page, size, sort, order, name).stream()
                .map(humanBeingMapper::entityToDTO)
                .toList()).build();
    }
}
