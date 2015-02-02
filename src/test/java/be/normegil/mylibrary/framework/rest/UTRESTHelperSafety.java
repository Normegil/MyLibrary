package be.normegil.mylibrary.framework.rest;

import be.normegil.mylibrary.framework.Entity;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.ResourceDAO;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.SpecificResourceDAO;
import be.normegil.mylibrary.manga.Manga;
import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.tools.IGenerator;
import be.normegil.mylibrary.tools.Validator;
import org.junit.Test;
import org.mockito.Mockito;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UTRESTHelperSafety {

	private static final IGenerator<URI> URI_GENERATOR = GeneratorRepository.get(URI.class);
	private static final IGenerator<Manga> ENTITY_GENERATOR = GeneratorRepository.get(Manga.class);
	private static final ClassWrapper<RESTHelper> CLASS = new ClassWrapper<>(RESTHelper.class);
	private RESTHelper restHelper = new RESTHelper();

	@Test(expected = ConstraintViolationException.class)
	public void testConstructor_NullSpecificResourceDAO() throws Exception {
		ResourceDAO resourceDAO = Mockito.mock(ResourceDAO.class);
		Validator.validate(CLASS.getConstructor(ResourceDAO.class, SpecificResourceDAO.class),
				resourceDAO, null);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testConstructor_NullResourceDAO() throws Exception {
		SpecificResourceDAO specificResourceDAO = Mockito.mock(SpecificResourceDAO.class);
		Validator.validate(CLASS.getConstructor(ResourceDAO.class, SpecificResourceDAO.class),
				new Object[]{null, specificResourceDAO});
	}

	@Test(expected = NullPointerException.class)
	public void testToUri_Collection_NullBaseURI() throws Exception {
		RESTHelper.toUri(null, new ArrayList<>());
	}

	@Test(expected = NullPointerException.class)
	public void testToUri_NullBaseURI() throws Exception {
		RESTHelper.toUri(null, ENTITY_GENERATOR.getDefault(false, false));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testToURI_EntityWithoutID() throws Exception {
		Entity entityWithoutID = ENTITY_GENERATOR.getNew(false, false);
		RESTHelper.toUri(URI_GENERATOR.getDefault(false, false), entityWithoutID);
	}

	@Test(expected = NullPointerException.class)
	public void testToUUID_Null() throws Exception {
		RESTHelper.toUUID(null);
	}

	@Test
	public void testToUUID_URIWithoutID() throws Exception {
		URI uri = URI_GENERATOR.getDefault(false, false);
		try {
			RESTHelper.toUUID(uri);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("The validated expression is false", e.getMessage());
		}
	}

	@Test(expected = ConstraintViolationException.class)
	public void testGetResourceFor_Null() throws Exception {
		Validator.validate(restHelper, CLASS.getMethod("getResourceFor", UriInfo.class), new Object[]{null});
	}
}
