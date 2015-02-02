package be.normegil.mylibrary.framework.rest;

import be.normegil.mylibrary.framework.Entity;
import be.normegil.mylibrary.framework.constraint.URIWithIDValidator;
import be.normegil.mylibrary.framework.exception.WebApplicationException;
import be.normegil.mylibrary.framework.rest.error.ErrorCode;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.Resource;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.ResourceDAO;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.SpecificResource;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.SpecificResourceDAO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.*;

@Stateless
@LocalBean
public class RESTHelper {

	@Inject
	protected ResourceDAO resourceDAO;

	@Inject
	protected SpecificResourceDAO specificResourceDAO;

	public RESTHelper() {
	}

	public RESTHelper(@NotNull final ResourceDAO resourceDAO, @NotNull final SpecificResourceDAO specificResourceDAO) {
		this.resourceDAO = resourceDAO;
		this.specificResourceDAO = specificResourceDAO;
	}

	public static final String PATH_SEPARATOR = "/";

	public static List<URI> toUri(final URI baseUri, final Collection<? extends Entity> entities) {
		Validate.notNull(baseUri);
		Validate.notNull(entities);

		List<URI> uris = new ArrayList<>();
		for (Entity entity : entities) {
			uris.add(toUri(baseUri, entity));
		}
		return uris;
	}

	public static URI toUri(final URI baseUri, final Entity entity) {
		Validate.notNull(baseUri);
		Validate.notNull(entity);

		String baseUriAsString = baseUri.toString();
		if (!baseUriAsString.endsWith(PATH_SEPARATOR)) {
			baseUriAsString += PATH_SEPARATOR;
		}

		Optional<UUID> idOptional = entity.getId();
		if (idOptional.isPresent()) {
			return URI.create(baseUriAsString + new RESTServices().getPathForResourceType(entity.getClass()) + PATH_SEPARATOR + idOptional.get());
		} else {
			throw new IllegalArgumentException("Entity without IDs cannot be transform into URI");
		}
	}

	public static UUID toUUID(final URI uri) {
		Validate.notNull(uri);
		Validate.isTrue(new URIWithIDValidator().isValid(uri, null));

		String uuidString = StringUtils.substringAfterLast(uri.toString(), PATH_SEPARATOR);
		return UUID.fromString(uuidString);
	}

	public Resource getResourceFor(@NotNull final UriInfo uriInfo) {
		URI baseUri = uriInfo.getRequestUri();
		if (isMainResource(baseUri)) {
			String mainResourcePath = getMainResourcePath(baseUri);
			Optional<RESTService> defaultServiceOptional = new RESTServices().getDefaultServiceFor(mainResourcePath);
			Class<? extends RESTService> restServiceClass = defaultServiceOptional.get().getClass();
			Optional<Resource> resourceOptional = resourceDAO.getByClass(restServiceClass);
			if (!resourceOptional.isPresent()) {
				resourceDAO.persist(new Resource(restServiceClass));
				resourceOptional = resourceDAO.getByClass(restServiceClass);
			}
			return resourceOptional.get();

		} else if (isSpecificResource(baseUri)) {
			String specificResourcePath = getSpecificResourcePath(baseUri);
			Optional<RESTService> restServiceOptional = new RESTServices().getDefaultServiceFor(specificResourcePath);
			String resourceID = getSpecificResourceID(baseUri);
			Class<? extends RESTService> restServiceClass = restServiceOptional.get().getClass();
			Optional<SpecificResource> specificResourceOptional = specificResourceDAO.get(restServiceClass, resourceID);
			return specificResourceOptional
					.orElse(new SpecificResource(restServiceClass, resourceID));
		} else {
			throw new WebApplicationException(ErrorCode.INVALID_URI, new IllegalArgumentException("Cannot determine the type of resource from uri : " + uriInfo));
		}
	}

	private boolean isMainResource(final URI uri) {
		String pathPart = getMainResourcePath(uri);
		return new RESTServices().getDefaultServiceFor(pathPart).isPresent();
	}

	private String getMainResourcePath(final URI uri) {
		return getMainResourcePath(uri.toString());
	}

	private String getMainResourcePath(final String uri) {
		return StringUtils.substringAfterLast(uri, PATH_SEPARATOR);
	}

	private boolean isSpecificResource(final URI uri) {
		String resourcePath = getSpecificResourcePath(uri);
		return new RESTServices().getDefaultServiceFor(resourcePath).isPresent();
	}

	private String getSpecificResourceID(final URI uri) {
		return StringUtils.substringAfterLast(uri.toString(), PATH_SEPARATOR);
	}

	private String getSpecificResourcePath(final URI uri) {
		String ressourceURI = StringUtils.substringBeforeLast(uri.toString(), PATH_SEPARATOR);
		return getMainResourcePath(ressourceURI);
	}
}
