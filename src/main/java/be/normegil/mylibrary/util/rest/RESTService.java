package be.normegil.mylibrary.util.rest;

import be.normegil.mylibrary.Constants;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.UUID;

public interface RESTService<E> {

	@GET
	@Produces(Constants.MediaType.JSON)
	Response getAll(@Context final UriInfo info, @QueryParam("offset") final Long offset, @QueryParam("limit") final Integer limit);

	@Path("/{ID}")
	@GET
	@Produces(Constants.MediaType.JSON)
	Response get(@Context final UriInfo info, @PathParam("ID") final UUID id);

	@POST
	@Consumes(Constants.MediaType.JSON)
	@Produces(Constants.MediaType.JSON)
	Response create(@Context final UriInfo info, final E entity);

	@Path("/{ID}")
	@PUT
	@Consumes(Constants.MediaType.JSON)
	@Produces(Constants.MediaType.JSON)
	Response updateByPUT(@PathParam("ID") final UUID id, final E entity);

	@Path("/{ID}")
	@POST
	@Consumes(Constants.MediaType.JSON)
	@Produces(Constants.MediaType.JSON)
	Response updateByPOST(@Context final UriInfo info, @PathParam("ID") final UUID id, final E entity);

	@DELETE
	@Path("/{ID}")
	@Produces(Constants.MediaType.JSON)
	Response delete(@PathParam("ID") final UUID id);

	Class<? extends E> getSupportedClass();
}
