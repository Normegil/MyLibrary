package be.normegil.mylibrary.util.rest;

import be.normegil.mylibrary.Constants;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.UUID;

public interface RESTService<E> {

//	@GET
//	@Produces(Constants.MediaType.JSON)
//	Response getAll(@Context UriInfo info, @PathParam("offset") Long offset, @PathParam("limit") Integer limit);

	@Path("/{ID}")
	@GET
	@Produces(Constants.MediaType.JSON)
	E get(@Context UriInfo info, @PathParam("ID") UUID id);

	@PUT
	@Consumes({Constants.MediaType.JSON, Constants.MediaType.XML})
	Response create(@Context UriInfo info, E entity);

	@Path("/{ID}")
	@PUT
	Response updateByPUT(@PathParam("ID") UUID id, E entity);

	@Path("/{ID}")
	@POST
	Response updateByPOST(@Context UriInfo info, @PathParam("ID") UUID id, E entity);

	@DELETE
	@Path("/{ID}")
	Response delete(@PathParam("ID") UUID id);

	Class<? extends E> getSupportedClass();
}
