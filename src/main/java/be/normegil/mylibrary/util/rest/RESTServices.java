package be.normegil.mylibrary.util.rest;

import be.normegil.mylibrary.manga.MangaREST;
import be.normegil.mylibrary.util.exception.RESTServiceNotFoundException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.ws.rs.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RESTServices {

	public static final String PATH_SEPARATOR = "/";
	private Set<RESTService> restServices = new HashSet<>();

	public RESTServices() {
		restServices.addAll(Arrays.asList(
				new MangaREST()
		));
	}

	public String getPathForResourceType(final Class aClass) {
		Optional<RESTService> service = getDefaultServiceFor(aClass);
		if (service.isPresent()) {
			Class<? extends RESTService> restServiceClass = service.get().getClass();
			return getPathFor(restServiceClass);
		} else {
			throw new RESTServiceNotFoundException("Rest Service not found for ressource : " + aClass.getSimpleName());
		}
	}

	public String getPathFor(final Class<? extends RESTService> service) {
		Path annotation = service.getAnnotation(Path.class);
		String path = annotation.value();
		if (path.startsWith(PATH_SEPARATOR)) {
			path = path.substring(PATH_SEPARATOR.length());
		}
		return path;
	}

	public Stream<String> getAllRESTServicesPaths() {
		return restServices.stream()
				.map((x) -> getPathFor(x.getClass()));
	}

	public Stream<RESTService> getServicesForResourceType(final Class aClass) {
		return restServices.stream()
				.filter((service) -> aClass.isAssignableFrom(service.getSupportedClass()));
	}

	public Stream<RESTService> getServicesFor(final String path) {
		return restServices.stream()
				.filter((restService) -> getPathFor(restService.getClass()).equals(path));
	}

	public Optional<RESTService> getDefaultServiceFor(final String path) {
		List<RESTService> restServices = getServicesFor(path)
				.collect(Collectors.toList());
		return getDefaultService(restServices, path);
	}

	public Optional<RESTService> getDefaultServiceFor(final Class aClass) {
		List<RESTService> restServices = getServicesForResourceType(aClass)
				.collect(Collectors.toList());
		return getDefaultService(restServices, aClass.getName());
	}

	private Optional<RESTService> getDefaultService(final List<RESTService> restServices, final String className) {
		if (restServices.size() == 1) {
			return Optional.of(restServices.iterator().next());
		} else {
			List<RESTService> defaultServices = restServices.stream()
					.filter((restService) -> restService.getClass().getAnnotation(Default.class) != null)
					.collect(Collectors.toList());

			if (defaultServices.size() == 0) {
				return Optional.empty();
			} else if (defaultServices.size() == 1) {
				return Optional.of(defaultServices.iterator().next());
			} else {
				throw new IllegalStateException("Application has more than one services for " + className + " but no @Default annotated services");
			}
		}
	}
}
