package be.normegil.mylibrary.util.rest;

import be.normegil.mylibrary.manga.MangaREST;
import be.normegil.mylibrary.util.Entity;
import be.normegil.mylibrary.util.constraint.URIWithID;
import be.normegil.mylibrary.util.exception.RESTServiceNotFoundException;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.inject.Default;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Path;
import java.net.URI;
import java.util.*;

public class RESTHelper {

	public static final String PATH_SEPARATOR = "/";
	private Set<RESTService> restServices = new HashSet<>();

	public RESTHelper() {
		restServices.addAll(Arrays.asList(
				new MangaREST()
		));
	}

	public String getPathFor(Class aClass) {
		RESTService service = getDefaultServiceFor(aClass);
		Path annotation = service.getClass().getAnnotation(Path.class);
		String path = annotation.value();
		if (path.startsWith(PATH_SEPARATOR)) {
			path = path.substring(PATH_SEPARATOR.length());
		}
		return path;
	}

	private RESTService getDefaultServiceFor(final Class aClass) {
		Set<RESTService> services = getServicesFor(aClass);
		for (RESTService service : services) {
			Default isDefault = service.getClass().getAnnotation(Default.class);
			if (isDefault != null) {
				return service;
			}
		}

		if (services.size() == 1) {
			return services.iterator().next();
		} else if (services.size() == 0) {
			throw new RESTServiceNotFoundException("No REST Service found for " + aClass);
		} else {
			throw new IllegalStateException("Application has more than one services for " + aClass + " but no @Default annotated services");
		}
	}

	public Set<RESTService> getServicesFor(final Class aClass) {
		Set<RESTService> services = new HashSet<>();
		for (RESTService restService : restServices) {
			if (aClass.isAssignableFrom(restService.getSupportedClass())) {
				services.add(restService);
			}
		}
		return services;
	}

	public Collection<URI> getRESTUri(final URI baseUri, final Class<? extends Entity> dataClass, final Collection<? extends Entity> entities) {
		Set<URI> uris = new HashSet<>();
		for (Entity entity : entities) {
			uris.add(getRESTUri(baseUri, dataClass, entity));
		}
		return uris;
	}

	public URI getRESTUri(final URI baseUri, final Class<? extends Entity> dataClass, final Entity entity) {
		String baseUriAsString = baseUri.toString();
		RESTHelper helper = new RESTHelper();

		if (!baseUriAsString.endsWith(PATH_SEPARATOR)) {
			baseUriAsString += PATH_SEPARATOR;
		}

		return URI.create(baseUriAsString + helper.getPathFor(dataClass) + PATH_SEPARATOR + entity.getId());
	}

	public UUID toUUID(@NotNull @URIWithID final URI uri) {
		String uuidString = StringUtils.substringAfterLast(uri.toString(), PATH_SEPARATOR);
		return UUID.fromString(uuidString);
	}

	public URI toURI(@NotNull URI baseURL, @NotNull final UUID uuid) {
		return URI.create(baseURL.toString() + PATH_SEPARATOR + uuid.toString());
	}

	public URI removeParameters(final URI uri) {
		String uriWithoutParameters = StringUtils.substringBeforeLast(uri.toString(), "?");
		return URI.create(uriWithoutParameters);
	}
}
