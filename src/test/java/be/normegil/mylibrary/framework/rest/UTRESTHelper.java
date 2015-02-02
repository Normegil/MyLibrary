package be.normegil.mylibrary.framework.rest;

import be.normegil.mylibrary.framework.Entity;
import be.normegil.mylibrary.framework.exception.WebApplicationException;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.Resource;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.SpecificResource;
import be.normegil.mylibrary.manga.Manga;
import be.normegil.mylibrary.manga.MangaREST;
import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.tools.IGenerator;
import be.normegil.mylibrary.tools.dao.ResourceMemoryDAO;
import be.normegil.mylibrary.tools.dao.SpecificResourceMemoryDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class UTRESTHelper {

	private static final IGenerator<URI> URI_GENERATOR = GeneratorRepository.get(URI.class);
	private static final IGenerator<Manga> ENTITY_GENERATOR = GeneratorRepository.get(Manga.class);
	private RESTHelper restHelper;

	@Before
	public void setUp() throws Exception {
		ResourceMemoryDAO resourceDAO = new ResourceMemoryDAO();
		RESTServices restServices = new RESTServices();
		List<Resource> resources = restServices.getAllRESTServicesPaths()
				.map(restServices::getDefaultServiceFor)
				.map((restServiceOptional) -> new Resource(restServiceOptional.get().getClass()))
				.collect(Collectors.toList());
		for (Resource resource : resources) {
			resourceDAO.persist(resource);
		}
		restHelper = new RESTHelper(resourceDAO, new SpecificResourceMemoryDAO());
	}

	@After
	public void tearDown() throws Exception {
		restHelper = null;
	}

	@Test
	public void testConstructor_SpecificResourceDAO() throws Exception {
		SpecificResourceMemoryDAO specificResourceDAO = new SpecificResourceMemoryDAO();
		RESTHelper restHelper = new RESTHelper(new ResourceMemoryDAO(), specificResourceDAO);
		assertSame(specificResourceDAO, restHelper.specificResourceDAO);
	}

	@Test
	public void testConstructor_ResourceDAO() throws Exception {
		ResourceMemoryDAO resourceDAO = new ResourceMemoryDAO();
		RESTHelper restHelper = new RESTHelper(resourceDAO, new SpecificResourceMemoryDAO());
		assertSame(resourceDAO, restHelper.resourceDAO);
	}

	@Test
	public void testToUri_Collection() throws Exception {
		URI baseURI = URI_GENERATOR.getNew(false, false);
		Collection<Entity> entities = new ArrayList<>();
		entities.add(ENTITY_GENERATOR.getNew(false, true));
		entities.add(ENTITY_GENERATOR.getNew(false, true));
		entities.add(ENTITY_GENERATOR.getNew(false, true));

		List<URI> expectedURI = new ArrayList<>();
		for (Entity entity : entities) {
			expectedURI.add(RESTHelper.toUri(baseURI, entity));
		}

		List<URI> toTestURIs = RESTHelper.toUri(baseURI, entities);
		assertEquals(expectedURI, toTestURIs);
	}

	@Test
	public void testToURI() throws Exception {
		URI baseURI = URI_GENERATOR.getNew(false, false);
		Entity entity = ENTITY_GENERATOR.getNew(false, true);
		URI expected = URI.create(baseURI.toString() + "/" + new RESTServices().getPathForResourceType(entity.getClass()) + "/" + entity.getId().get());
		URI toTest = RESTHelper.toUri(baseURI, entity);
		assertEquals(expected, toTest);
	}

	@Test
	public void testToURI_WithPathSeparator() throws Exception {
		URI uriWithoutPathSeparator = URI_GENERATOR.getNew(false, false);
		URI baseURI = URI.create(uriWithoutPathSeparator + "/");
		Entity entity = ENTITY_GENERATOR.getNew(false, true);
		URI expected = URI.create(uriWithoutPathSeparator.toString() + "/" + new RESTServices().getPathForResourceType(entity.getClass()) + "/" + entity.getId().get());
		URI toTest = RESTHelper.toUri(baseURI, entity);
		assertEquals(expected, toTest);
	}

	@Test
	public void testToUUID() throws Exception {
		URI baseURI = URI_GENERATOR.getNew(false, false);
		Entity entity = ENTITY_GENERATOR.getNew(false, true);
		URI toTest = RESTHelper.toUri(baseURI, entity);
		assertEquals(entity.getId().get(), RESTHelper.toUUID(toTest));
	}

	@Test
	public void testGetResourceFor() throws Exception {
		UriInfo uriInfo = Mockito.mock(UriInfo.class);
		URI uri = URI.create("http://www.example.com:7050/rest/mangas");
		when(uriInfo.getRequestUri())
				.thenReturn(uri);

		Resource expected = new Resource(MangaREST.class);
		Resource toTest = restHelper.getResourceFor(uriInfo);
		assertEquals(expected, toTest);
	}

	@Test
	public void testGetResourceFor_ResourceNotInDatabase() throws Exception {
		UriInfo uriInfo = Mockito.mock(UriInfo.class);
		URI uri = URI.create("http://www.example.com:7050/rest/mangas");
		when(uriInfo.getRequestUri())
				.thenReturn(uri);

		Class<MangaREST> restService = MangaREST.class;
		ResourceMemoryDAO dao = new ResourceMemoryDAO();
		new RESTHelper(dao, new SpecificResourceMemoryDAO()).getResourceFor(uriInfo);
		assertTrue(dao.getByClass(restService).isPresent());
	}

	@Test
	public void testGetResourceFor_SpecificResourceURI() throws Exception {
		UriInfo uriInfo = Mockito.mock(UriInfo.class);
		UUID uuid = UUID.randomUUID();
		URI uri = URI.create("http://www.example.com:7050/rest/mangas/" + uuid);
		when(uriInfo.getRequestUri())
				.thenReturn(uri);

		Resource expected = new SpecificResource(MangaREST.class, uuid.toString());
		Resource toTest = restHelper.getResourceFor(uriInfo);
		assertEquals(expected, toTest);
	}

	@Test(expected = WebApplicationException.class)
	public void testGetResourceFor_WrongURI() throws Exception {
		UriInfo uriInfo = Mockito.mock(UriInfo.class);
		URI wrongUri = URI.create("http://www.example.com:7050/rest");
		when(uriInfo.getRequestUri())
				.thenReturn(wrongUri);
		restHelper.getResourceFor(uriInfo);
	}
}
