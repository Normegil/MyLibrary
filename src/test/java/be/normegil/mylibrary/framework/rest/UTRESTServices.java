package be.normegil.mylibrary.framework.rest;

import be.normegil.mylibrary.framework.annotation.DefaultRESTService;
import be.normegil.mylibrary.manga.Manga;
import be.normegil.mylibrary.manga.MangaREST;
import be.normegil.mylibrary.tools.CustomCollectors;
import be.normegil.mylibrary.tools.fake.FakeEntity;
import org.junit.Test;
import org.reflections.Reflections;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UTRESTServices {

	private static final String REST_SERVICE_PATH = "mangas";
	private static final Class<Manga> RESOURCE_CLASS = Manga.class;
	public static final Class<MangaREST> REST_SERVICE_CLASS = MangaREST.class;

	@Test
	public void testStaticInitialization() throws Exception {
		Set<RESTService> expected = new HashSet<>();
		Reflections reflections = new Reflections("be.normegil");
		Set<Class<? extends RESTService>> subTypes = reflections.getSubTypesOf(RESTService.class);
		for (Class<? extends RESTService> subType : subTypes) {
			RESTService restService = subType.newInstance();
			expected.add(restService);
		}

		assertSameRestServices(expected);
	}

	@Test
	public void testGetPathForResourceType() throws Exception {
		assertEquals(REST_SERVICE_PATH, new RESTServices().getPathForResourceType(RESOURCE_CLASS));
	}

	@Test
	public void testGetPathFor() throws Exception {
		assertEquals(REST_SERVICE_PATH, new RESTServices().getPathFor(MangaREST.class));
	}

	@Test
	public void testGetAllRESTServicesPaths() throws Exception {
		RESTServices restServices = new RESTServices();
		List<String> expectedPaths = RESTServices.restServices.stream()
				.map((rs) -> restServices.getPathFor(rs.getClass()))
				.collect(Collectors.toList());
		List<String> toTest = restServices.getAllRESTServicesPaths()
				.collect(Collectors.toList());
		assertEquals(expectedPaths, toTest);
	}

	@Test
	public void testGetServicesForResourceType() throws Exception {
		List<RESTService> expected = RESTServices.restServices.stream()
				.filter((service) -> RESOURCE_CLASS.isAssignableFrom(service.getSupportedClass()))
				.collect(Collectors.toList());
		List<RESTService> toTest = new RESTServices().getServicesForResourceType(RESOURCE_CLASS)
				.collect(Collectors.toList());
		assertEquals(expected, toTest);
	}

	@Test
	public void testGetServicesFor() throws Exception {
		RESTServices restServices = new RESTServices();
		List<RESTService> expected = RESTServices.restServices.stream()
				.filter((service) -> REST_SERVICE_PATH.equals(restServices.getPathFor(service.getClass())))
				.collect(Collectors.toList());
		List<RESTService> toTest = restServices.getServicesFor(REST_SERVICE_PATH)
				.collect(Collectors.toList());
		assertEquals(expected, toTest);
	}

	@Test
	public void testGetDefaultServiceFor() throws Exception {
		RESTServices restServices = new RESTServices();
		Optional<RESTService> restServiceOptional = restServices.getServicesForResourceType(RESOURCE_CLASS)
				.filter((rs) -> rs.getClass().getAnnotation(DefaultRESTService.class) != null)
				.collect(new CustomCollectors().singletonCollector());
		Optional<RESTService> toTestOptional = restServices.getDefaultServiceFor(REST_SERVICE_PATH);
		assertEquals(restServiceOptional.get(), toTestOptional.get());
	}

	@Test
	public void testGetDefaultServiceForClass() throws Exception {
		RESTServices restServices = new RESTServices();
		Optional<RESTService> restServiceOptional = restServices.getServicesForResourceType(RESOURCE_CLASS)
				.filter((rs) -> rs.getClass().getAnnotation(DefaultRESTService.class) != null)
				.collect(new CustomCollectors().singletonCollector());
		Optional<RESTService> toTestOptional = restServices.getDefaultServiceFor(RESOURCE_CLASS);
		assertEquals(restServiceOptional.get(), toTestOptional.get());
	}

	private void assertSameRestServices(final Set<RESTService> expected) {
		assertEquals(expected.size(), RESTServices.restServices.size());
		for (RESTService restService : expected) {
			Class<? extends RESTService> restServiceClass = restService.getClass();
			assertTrue(
					RESTServices.restServices.stream()
							.filter((r) -> r.getClass().equals(restServiceClass))
							.collect(new CustomCollectors().singletonCollector())
							.isPresent()
			);
		}
	}

	@Path("/mangas2")
	public static final class FakeMangaREST implements RESTService<Manga> {

		@Override
		public Response getAll(@Context final UriInfo info, final Long offset, final Integer limit) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Response get(@Context final UriInfo info, final UUID id) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Response create(@Context final UriInfo info, final Manga entity) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Response updateByPUT(final UUID id, final Manga entity) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Response updateByPOST(@Context final UriInfo info, final UUID id, final Manga entity) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Response delete(final UUID id) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Class<? extends Manga> getSupportedClass() {
			return Manga.class;
		}
	}
}
