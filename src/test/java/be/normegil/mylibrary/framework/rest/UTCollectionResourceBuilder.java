package be.normegil.mylibrary.framework.rest;

import be.normegil.mylibrary.ApplicationProperties;
import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.tools.IGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UTCollectionResourceBuilder {

	private static final IGenerator<URI> GENERATOR = GeneratorRepository.get(URI.class);
	private static final long DEFAULT_OFFSET = 5L;
	private static final int DEFAULT_LIMIT = 30;
	private static final int DEFAULT_NUMBER_OF_ITEMS = DEFAULT_LIMIT * 2;
	private static final long FIRST_OFFSET = 0L;
	private CollectionResource.Builder entity;

	@Before
	public void setUp() throws Exception {
		entity = CollectionResource.builder();
	}

	@After
	public void tearDown() throws Exception {
		entity = null;
	}

	@Test
	public void testSetOffset() throws Exception {
		URI uri = GENERATOR.getNew(true, false);
		CollectionResource collectionResource = entity
				.setOffset(DEFAULT_OFFSET)
				.setBaseURI(uri)
				.setTotalNumberOfItems(DEFAULT_NUMBER_OF_ITEMS)
				.build();
		assertEquals(DEFAULT_OFFSET, collectionResource.getOffset());
	}

	@Test
	public void testOffsetNotPresent() throws Exception {
		URI uri = GENERATOR.getNew(true, false);
		CollectionResource collectionResource = entity
				.setLimit(DEFAULT_LIMIT)
				.setBaseURI(uri)
				.setTotalNumberOfItems(DEFAULT_NUMBER_OF_ITEMS)
				.build();
		assertEquals(FIRST_OFFSET, collectionResource.getOffset());
	}

	@Test
	public void testSetLimit() throws Exception {
		URI uri = GENERATOR.getNew(true, false);
		CollectionResource collectionResource = entity
				.setLimit(DEFAULT_LIMIT)
				.setBaseURI(uri)
				.setTotalNumberOfItems(DEFAULT_NUMBER_OF_ITEMS)
				.build();
		assertEquals(DEFAULT_LIMIT, collectionResource.getLimit());
	}

	@Test
	public void testLimitNotPresent() throws Exception {
		URI uri = GENERATOR.getNew(true, false);
		CollectionResource collectionResource = entity
				.setOffset(DEFAULT_OFFSET)
				.setBaseURI(uri)
				.setTotalNumberOfItems(DEFAULT_NUMBER_OF_ITEMS)
				.build();
		assertEquals(ApplicationProperties.REST.DEFAULT_LIMIT, collectionResource.getLimit());
	}

	@Test
	public void testSetLimit_HigherThanMaximum() throws Exception {
		URI uri = GENERATOR.getNew(true, false);
		CollectionResource collectionResource = entity
				.setLimit(ApplicationProperties.REST.MAX_LIMIT + 10)
				.setBaseURI(uri)
				.setTotalNumberOfItems(DEFAULT_NUMBER_OF_ITEMS)
				.build();
		assertEquals(ApplicationProperties.REST.MAX_LIMIT, collectionResource.getLimit());
	}

	@Test
	public void testFirstPageURL() throws Exception {
		URI uri = GENERATOR.getNew(true, false);
		CollectionResource collectionResource = entity
				.setOffset(DEFAULT_OFFSET)
				.setLimit(DEFAULT_LIMIT)
				.setBaseURI(uri)
				.setTotalNumberOfItems(DEFAULT_NUMBER_OF_ITEMS)
				.build();
		URI expected = CollectionResource.helper().getCollectionURI(uri, FIRST_OFFSET, DEFAULT_LIMIT);
		assertEquals(expected, collectionResource.getUriToFirstPage());
	}

	@Test
	public void testLastPageURL() throws Exception {
		URI uri = GENERATOR.getNew(true, false);
		CollectionResource collectionResource = entity
				.setOffset(DEFAULT_OFFSET)
				.setLimit(DEFAULT_LIMIT)
				.setBaseURI(uri)
				.setTotalNumberOfItems(DEFAULT_LIMIT + 5)
				.build();
		URI expected = CollectionResource.helper().getCollectionURI(uri, DEFAULT_LIMIT, DEFAULT_LIMIT);
		assertEquals(expected, collectionResource.getUriToLastPage());
	}

	@Test
	public void testLastPageURL_LimitHigherThanTotalNumberOfItems() throws Exception {
		URI uri = GENERATOR.getNew(true, false);
		CollectionResource collectionResource = entity
				.setOffset(DEFAULT_OFFSET)
				.setLimit(DEFAULT_LIMIT)
				.setTotalNumberOfItems(DEFAULT_LIMIT - 5)
				.setBaseURI(uri)
				.build();
		URI expected = CollectionResource.helper().getCollectionURI(uri, FIRST_OFFSET, DEFAULT_LIMIT);
		assertEquals(expected, collectionResource.getUriToLastPage());
	}

	@Test
	public void testLastPageURL_NumberOfItemsMultipleOfLimit() throws Exception {
		URI uri = GENERATOR.getNew(true, false);
		CollectionResource collectionResource = entity
				.setOffset(DEFAULT_OFFSET)
				.setLimit(DEFAULT_LIMIT)
				.setTotalNumberOfItems(DEFAULT_LIMIT * 3)
				.setBaseURI(uri)
				.build();
		URI expected = CollectionResource.helper().getCollectionURI(uri, DEFAULT_LIMIT * 2, DEFAULT_LIMIT);
		assertEquals(expected, collectionResource.getUriToLastPage());
	}

	@Test
	public void testPreviousPageURL_OffsetHigherThanLimit() throws Exception {
		URI uri = GENERATOR.getNew(true, false);
		long offset = DEFAULT_LIMIT + 5;
		CollectionResource collectionResource = entity
				.setOffset(offset)
				.setLimit(DEFAULT_LIMIT)
				.setBaseURI(uri)
				.setTotalNumberOfItems(DEFAULT_NUMBER_OF_ITEMS)
				.build();
		URI expected = CollectionResource.helper().getCollectionURI(uri, offset - DEFAULT_LIMIT, DEFAULT_LIMIT);
		assertEquals(expected, collectionResource.getUriToPreviousPage());
	}

	@Test
	public void testPreviousPageURL_OffsetLowerThanLimit() throws Exception {
		URI uri = GENERATOR.getNew(true, false);
		long offset = DEFAULT_LIMIT - 5;
		CollectionResource collectionResource = entity
				.setOffset(offset)
				.setLimit(DEFAULT_LIMIT)
				.setBaseURI(uri)
				.setTotalNumberOfItems(DEFAULT_NUMBER_OF_ITEMS)
				.build();
		URI expected = CollectionResource.helper().getCollectionURI(uri, FIRST_OFFSET, DEFAULT_LIMIT);
		assertEquals(expected, collectionResource.getUriToPreviousPage());
	}

	@Test
	public void testPreviousPageURL_FirstOffset() throws Exception {
		URI uri = GENERATOR.getNew(true, false);
		CollectionResource collectionResource = entity
				.setOffset(FIRST_OFFSET)
				.setLimit(DEFAULT_LIMIT)
				.setTotalNumberOfItems(DEFAULT_NUMBER_OF_ITEMS)
				.setBaseURI(uri)
				.build();
		assertNull(collectionResource.getUriToPreviousPage());
	}

	@Test
	public void testNextPageURL() throws Exception {
		URI uri = GENERATOR.getNew(true, false);
		CollectionResource collectionResource = entity
				.setOffset(DEFAULT_OFFSET)
				.setLimit(DEFAULT_LIMIT)
				.setBaseURI(uri)
				.setTotalNumberOfItems(DEFAULT_NUMBER_OF_ITEMS)
				.build();
		URI expected = CollectionResource.helper().getCollectionURI(uri, DEFAULT_OFFSET + DEFAULT_LIMIT, DEFAULT_LIMIT);
		assertEquals(expected, collectionResource.getUriToNextPage());
	}

	@Test
	public void testNextPageURL_LastPage() throws Exception {
		URI uri = GENERATOR.getNew(true, false);
		long totalNumberOfItems = DEFAULT_LIMIT + 5;
		CollectionResource collectionResource = entity
				.setOffset(DEFAULT_LIMIT)
				.setLimit(DEFAULT_LIMIT)
				.setBaseURI(uri)
				.setTotalNumberOfItems(totalNumberOfItems)
				.build();
		assertNull(collectionResource.getUriToNextPage());
	}

	@Test
	public void testAddAllItems() throws Exception {
		URI uri = GENERATOR.getNew(true, false);
		List<URI> toAdd = new ArrayList<>();
		toAdd.add(GENERATOR.getNew(true, false));
		toAdd.add(GENERATOR.getNew(true, false));
		toAdd.add(GENERATOR.getNew(true, false));

		CollectionResource collectionResource = entity
				.addAllItems(toAdd)
				.setBaseURI(uri)
				.setTotalNumberOfItems(DEFAULT_NUMBER_OF_ITEMS)
				.build();

		assertEquals(toAdd, collectionResource.getItems());
	}

	@Test
	public void testAddItem() throws Exception {
		URI uri = GENERATOR.getNew(true, false);
		URI baseUri = GENERATOR.getNew(true, false);
		CollectionResource collectionResource = entity
				.addItem(uri)
				.setBaseURI(baseUri)
				.setTotalNumberOfItems(DEFAULT_NUMBER_OF_ITEMS)
				.build();

		assertEquals(1, collectionResource.getItems().size());
		assertTrue(collectionResource.getItems().contains(uri));
	}


}