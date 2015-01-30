package be.normegil.mylibrary.framework.rest;

import be.normegil.mylibrary.ApplicationProperties;
import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.tools.IGenerator;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UTCollectionResource {

	private static final IGenerator<CollectionResource> GENERATOR = GeneratorRepository.get(CollectionResource.class);
	private CollectionResource entity;

	@Before
	public void setUp() throws Exception {
		entity = GENERATOR.getDefault(true, false);
	}

	@After
	public void tearDown() throws Exception {
		entity = null;
	}

	@Test
	public void testCopyConstructor() throws Exception {
		CollectionResource copy = new CollectionResource(entity);
		assertEquals(entity, copy);
	}

	@Test
	public void testToString() throws Exception {
		String expected = new ToStringBuilder(entity, ApplicationProperties.TO_STRING_STYLE)
				.append("offset", entity.getOffset())
				.append("limit", entity.getLimit())
				.append("totalNumberOfItems", entity.getTotalNumberOfItems())
				.toString();
		assertEquals(expected, entity.toString());
	}
}
