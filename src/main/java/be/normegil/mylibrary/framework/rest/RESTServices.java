package be.normegil.mylibrary.framework.rest;

import be.normegil.mylibrary.framework.annotation.DefaultRESTService;
import be.normegil.mylibrary.framework.constraint.NotEmpty;
import be.normegil.mylibrary.framework.exception.IllegalAccessRuntimeException;
import be.normegil.mylibrary.framework.exception.InstantiationRuntimeException;
import be.normegil.mylibrary.framework.exception.RESTServiceNotFoundException;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RESTServices {

	public static final String PATH_SEPARATOR = "/";
	private static final Logger LOG = LoggerFactory.getLogger(RESTServices.class);
	protected static final Set<RESTService> restServices = new HashSet<>();

	static {
		Reflections reflections = new Reflections("be.normegil");
		Set<Class<? extends RESTService>> subTypes = reflections.getSubTypesOf(RESTService.class);

		for (Class<? extends RESTService> subType : subTypes) {
			try {
				RESTService restService = subType.newInstance();
				restServices.add(restService);
				LOG.info("RestService found : [" + restService.getSupportedClass().getSimpleName() + "] " + restService.getClass().getSimpleName());
			} catch (InstantiationException e) {
				throw new InstantiationRuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new IllegalAccessRuntimeException(e);
			}
		}
	}

	public String getPathForResourceType(@NotNull final Class aClass) {
		Optional<RESTService> service = getDefaultServiceFor(aClass);
		if (service.isPresent()) {
			Class<? extends RESTService> restServiceClass = service.get().getClass();
			return getPathFor(restServiceClass);
		} else {
			throw new RESTServiceNotFoundException("Rest Service not found for ressource : " + aClass.getSimpleName());
		}
	}

	public String getPathFor(@NotNull final Class<? extends RESTService> service) {
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

	public Stream<RESTService> getServicesForResourceType(@NotNull final Class aClass) {
		return restServices.stream()
				.filter((service) -> aClass.isAssignableFrom(service.getSupportedClass()));
	}

	public Stream<RESTService> getServicesFor(@NotNull @NotEmpty final String path) {
		return restServices.stream()
				.filter((restService) -> getPathFor(restService.getClass()).equals(path));
	}

	public Optional<RESTService> getDefaultServiceFor(@NotNull @NotEmpty final String path) {
		List<RESTService> restServices = getServicesFor(path)
				.collect(Collectors.toList());
		return getDefaultService(restServices, path);
	}

	public Optional<RESTService> getDefaultServiceFor(@NotNull final Class aClass) {
		List<RESTService> restServices = getServicesForResourceType(aClass)
				.collect(Collectors.toList());
		return getDefaultService(restServices, aClass.getName());
	}

	private Optional<RESTService> getDefaultService(final List<RESTService> restServices, final String className) {
		if (restServices.size() == 1) {
			return Optional.of(restServices.iterator().next());
		} else {
			List<RESTService> defaultServices = restServices.stream()
					.filter((restService) -> restService.getClass().getAnnotation(DefaultRESTService.class) != null)
					.collect(Collectors.toList());

			if (defaultServices.size() == 0) {
				return Optional.empty();
			} else if (defaultServices.size() == 1) {
				return Optional.of(defaultServices.iterator().next());
			} else {
				throw new IllegalStateException("Application has more than one REST services for " + className + " and several annotated with @DefaultRESTService");
			}
		}
	}
}
