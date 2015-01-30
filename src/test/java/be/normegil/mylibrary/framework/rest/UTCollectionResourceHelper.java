package be.normegil.mylibrary.framework.rest;

import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.tools.IGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UTCollectionResourceHelper {

	private static final IGenerator<URI> GENERATOR = GeneratorRepository.get(URI.class);
	private CollectionResource.Helper entity;

	@Before
	public void setUp() throws Exception {
		entity = CollectionResource.helper();
	}

	@After
	public void tearDown() throws Exception {
		entity = null;
	}

	@Test
	public void testGetCollectionURI() throws Exception {
		URI uri = GENERATOR.getNew(true, false);
		long offset = 5L;
		int limit = 25;
		URI toTest = entity.getCollectionURI(uri, offset, limit);
		URI expected = URI.create(uri.toString() + "?offset=" + offset + "&limit=" + limit);
		assertEquals(expected, toTest);
	}

	@Test
	public void testGetBaseURI() throws Exception {
		URI uri = GENERATOR.getNew(true, false);
		long offset = 5L;
		int limit = 25;
		URI uriToParse = URI.create(uri.toString() + "?offset=" + offset + "&limit=" + limit);
		URI toTest = entity.getBaseURI(uriToParse);
		assertEquals(uri, toTest);
	}

	@Test
	public void testGenerateNextURL_NoNewPage() throws Exception {
		URI uri = GENERATOR.getNew(true, false);
		long offset = 5L;
		int limit = 25;
		long totalNumberOfItems = 30L;
		URI toTest = entity.generateNextURL(uri, offset, limit, totalNumberOfItems);
		assertNull(toTest);
	}
}
