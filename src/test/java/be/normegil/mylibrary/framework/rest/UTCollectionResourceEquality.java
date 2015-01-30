package be.normegil.mylibrary.framework.rest;

import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.tools.IGenerator;
import be.normegil.mylibrary.tools.generator.CollectionResourceGenerator;
import be.normegil.mylibrary.tools.test.AbstractDataEqualityTest;
import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UTCollectionResourceEquality extends AbstractDataEqualityTest<CollectionResource> {

	private static final IGenerator<CollectionResource> GENERATOR = GeneratorRepository.get(CollectionResource.class);
	private static final IGenerator<URI> URI_GENERATOR = GeneratorRepository.get(URI.class);
	private static final ClassWrapper<CollectionResource> CLASS = new ClassWrapper<>(CollectionResource.class);

	@Override
	protected CollectionResource getNewEntity() {
		return GENERATOR.getNew(true, false);
	}

	@Test
	public void testUnchanged() throws Exception {
		CollectionResource entity = getEntity();
		CollectionResource copy = new CollectionResource(entity);
		assertEquals(entity, copy);
	}

	@Test
	public void testDifferentOffset() throws Exception {
		CollectionResource entity = getEntity();
		CollectionResource copy = CollectionResource.builder()
				.from(entity)
				.setOffset(entity.getOffset() + 1)
				.build();
		assertNotEquals(entity, copy);
	}

	@Test
	public void testDifferentLimit() throws Exception {
		CollectionResource entity = getEntity();
		CollectionResource copy = CollectionResource.builder()
				.from(entity)
				.setLimit(entity.getLimit() + 1)
				.build();
		assertNotEquals(entity, copy);
	}

	@Test
	public void testDifferentFirstLink() throws Exception {
		CollectionResource entity = getEntity();
		CollectionResource copy = new CollectionResource(entity);
		CLASS.getField("first").set(copy, URI_GENERATOR.getNew(true, false));
		assertNotEquals(entity, copy);
	}

	@Test
	public void testDifferentLastLink() throws Exception {
		CollectionResource entity = getEntity();
		CollectionResource copy = new CollectionResource(entity);
		CLASS.getField("last").set(copy, URI_GENERATOR.getNew(true, false));
		assertNotEquals(entity, copy);
	}

	@Test
	public void testDifferentPreviousLink() throws Exception {
		CollectionResource entity = getEntity();
		CollectionResource copy = new CollectionResource(entity);
		CLASS.getField("previous").set(copy, URI_GENERATOR.getNew(true, false));
		assertNotEquals(entity, copy);
	}

	@Test
	public void testDifferentNextLink() throws Exception {
		CollectionResource entity = getEntity();
		CollectionResource copy = new CollectionResource(entity);
		CLASS.getField("next").set(copy, URI_GENERATOR.getNew(true, false));
		assertNotEquals(entity, copy);
	}

	@Test
	public void testDifferentItems() throws Exception {
		CollectionResource entity = getEntity();
		CollectionResource copy = CollectionResource.builder()
				.from(entity)
				.addItem(URI_GENERATOR.getNew(true, false))
				.build();
		assertNotEquals(entity, copy);
	}

	@Test
	public void testHashCode() throws Exception {
		CollectionResource entity = GENERATOR.getDefault(true, false);
		assertEquals(CollectionResourceGenerator.DEFAULT_HASHCODE, entity.hashCode());
	}
}