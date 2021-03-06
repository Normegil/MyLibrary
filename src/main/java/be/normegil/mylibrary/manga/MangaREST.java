package be.normegil.mylibrary.manga;

import be.normegil.mylibrary.ApplicationProperties;
import be.normegil.mylibrary.framework.URIHelper;
import be.normegil.mylibrary.framework.Updatable;
import be.normegil.mylibrary.framework.exception.WebApplicationException;
import be.normegil.mylibrary.framework.rest.CollectionResource;
import be.normegil.mylibrary.framework.rest.HttpStatus;
import be.normegil.mylibrary.framework.rest.RESTHelper;
import be.normegil.mylibrary.framework.rest.RESTService;
import be.normegil.mylibrary.framework.rest.error.ErrorCode;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@Path("/mangas")
public class MangaREST implements RESTService<Manga> {

	@Inject
	private MangaDatabaseDAO dao;

	@Inject
	private URIHelper uriHelper;

	@Inject
	private RESTHelper restHelper;

	@Override
	public Response getAll(@Context final UriInfo info, @QueryParam("offset") final Long requestOffset, @QueryParam("limit") final Integer requestLimit) {
		URI baseUri = uriHelper.removeParameters(info.getRequestUri());
		long offset = requestOffset == null ? 0L : requestOffset;
		int limit = requestLimit == null ? ApplicationProperties.REST.DEFAULT_LIMIT : requestLimit;

		List<Manga> entities = new ArrayList<>();
		dao.getAll(offset, limit).forEach(entities::add);
		CollectionResource.Builder collectionResourceBuilder = CollectionResource.builder()
				.setOffset(offset)
				.setLimit(limit)
				.setTotalNumberOfItems(dao.getNumberOfElements())
				.setBaseURI(baseUri)
				.addAllItems(restHelper.toUri(info.getBaseUri(), entities));
		return Response.ok(collectionResourceBuilder.build()).build();
	}

	@Override
	public Response get(@Context final UriInfo info, @PathParam("ID") final UUID id) {
		Optional<Manga> optionalManga = dao.get(id);
		if (!optionalManga.isPresent()) {
			throw new WebApplicationException(ErrorCode.OBJECT_NOT_FOUND, new IllegalArgumentException("Object not found [ID=" + id.toString() + "]"));
		}
		Manga.Digest digest = new Manga.Digest();
		digest.fromBase(info.getBaseUri(), optionalManga.get());
		return Response.ok(digest).build();
	}

	@Override
	public Response create(@Context final UriInfo info, final Manga entity) {
		dao.persist(entity);

		URI baseUri = uriHelper.removeParameters(info.getRequestUri());

		return Response.status(HttpStatus.CREATED.value())
				.location(restHelper.toUri(baseUri, entity))
				.build();
	}

	@Override
	@Transactional
	public Response updateByPUT(final UUID id, final Manga givenManga) {
		return update(id, (manga) -> manga.from(givenManga, true));
	}

	@Override
	@Transactional
	public Response updateByPOST(@Context final UriInfo info, final UUID id, final Manga givenManga) {
		return update(id, (manga) -> manga.from(givenManga, false));
	}

	@Override
	@Transactional
	public Response delete(@PathParam("ID") final UUID id) {
		if (id == null) {
			throw new IllegalArgumentException("ID should not be null");
		}

		Optional<Manga> optionalManga = dao.get(id);
		if (!optionalManga.isPresent()) {
			throw new WebApplicationException(ErrorCode.OBJECT_NOT_FOUND, new IllegalArgumentException("Object not found [ID=" + id.toString() + "]"));
		}

		dao.remove(optionalManga.get());
		return Response.ok().build();
	}

	private Response update(UUID id, Consumer<Updatable> consumer) {
		if (id == null) {
			throw new WebApplicationException(ErrorCode.ID_NULL, new IllegalArgumentException("Path ID should not be null"));
		}
		Optional<Manga> optional = dao.get(id);
		if (!optional.isPresent()) {
			throw new WebApplicationException(ErrorCode.OBJECT_NOT_FOUND, new IllegalArgumentException("Object not found [ID=" + id.toString() + "]"));
		}
		Manga manga = optional.get();

		consumer.accept(manga);

		dao.persist(manga);
		return Response.ok().build();
	}

	@Override
	public Class<? extends Manga> getSupportedClass() {
		return Manga.class;
	}

}
