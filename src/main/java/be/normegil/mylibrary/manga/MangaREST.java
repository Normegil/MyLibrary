package be.normegil.mylibrary.manga;

import be.normegil.mylibrary.ApplicationProperties;
import be.normegil.mylibrary.Constants;
import be.normegil.mylibrary.util.Entity;
import be.normegil.mylibrary.util.rest.CollectionResource;
import be.normegil.mylibrary.util.rest.RESTHelper;
import be.normegil.mylibrary.util.rest.RESTService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("/mangas")
public class MangaREST implements RESTService<Manga> {

	@Inject
	private MangaDatabaseDAO dao;

	@GET
	@Produces(Constants.MediaType.JSON)
	public CollectionResource getAll(@Context final UriInfo info, @QueryParam("offset") final Long requestOffset, @QueryParam("limit") final Integer requestLimit) {
		RESTHelper helper = new RESTHelper();
		URI baseUri = helper.removeParameters(info.getRequestUri());
		long offset = requestOffset == null ? 0L : requestOffset;
		int limit = requestLimit == null ? ApplicationProperties.REST.DEFAULT_LIMIT : requestLimit;

		List<Manga> entities = new ArrayList<>();
		dao.getAll(offset, limit).forEach(entities::add);
		CollectionResource.Builder collectionResourceBuilder = CollectionResource.builder()
				.setOffset(offset)
				.setLimit(limit)
				.setTotalNumberOfItems(dao.getNumberOfElements())
				.setBaseURI(baseUri)
				.addAllItems(Entity.helper().convertToURLs(entities, baseUri));
		return collectionResourceBuilder.build();
	}

	@Override
	public Manga get(@Context UriInfo info, @PathParam("ID") UUID id) {
		return dao.get(id);
	}

	@Override
	public Response create(@Context final UriInfo info, final Manga entity) {
		return null;
	}

	@Override
	public Response updateByPUT(final UUID id, final Manga entity) {
		return null;
	}

	@Override
	public Response updateByPOST(@Context final UriInfo info, final UUID id, final Manga entity) {
		return null;
	}

	@Override
	public Response delete(final UUID id) {
		return null;
	}

	@Override
	public Class<? extends Manga> getSupportedClass() {
		return Manga.class;
	}

}
