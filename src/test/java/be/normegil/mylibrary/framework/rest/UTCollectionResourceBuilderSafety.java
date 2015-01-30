package be.normegil.mylibrary.framework.rest;

import be.normegil.mylibrary.ApplicationProperties;
import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.tools.IGenerator;
import be.normegil.mylibrary.tools.Validator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolationException;
import java.lang.reflect.Constructor;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class UTCollectionResourceBuilderSafety {

	private static final ClassWrapper<CollectionResource.Builder> CLASS = new ClassWrapper<>(CollectionResource.Builder.class);
	private static final IGenerator<URI> GENERATOR = GeneratorRepository.get(URI.class);
	private static final int DEFAULT_LIMIT = ApplicationProperties.REST.DEFAULT_LIMIT;
	private CollectionResource.Builder entity;

	@Before
	public void setUp() throws Exception {
		entity = CollectionResource.builder();
	}

	@After
	public void tearDown() throws Exception {
		entity = null;
	}

	@Test(expected = ConstraintViolationException.class)
	public void testSetOffset_Negative() throws Exception {
		Validator.validate(entity, CLASS.getMethod("setOffset", long.class), -1L);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testSetLimit_Zero() throws Exception {
		Validator.validate(entity, CLASS.getMethod("setLimit", int.class), 0);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testSetLimit_Negative() throws Exception {
		Validator.validate(entity, CLASS.getMethod("setLimit", int.class), -1);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testSetBaseURI_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("setBaseURI", URI.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testSetTotalNumberOfItems_Negative() throws Exception {
		Validator.validate(entity, CLASS.getMethod("setTotalNumberOfItems", long.class), -1L);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testAddAllItems_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("addAllItems", List.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testAddItem_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("addItem", URI.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testBuild_Empty() throws Exception {
		Validator.validate(getConstructorFromBuilder(), entity);
		Validator.validate(entity, CLASS.getMethod("build"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuild_OffsetHigherThanNumberOfElements() throws Exception {
		List<URI> urls = new ArrayList<>();
		urls.add(GENERATOR.getNew(true, false));
		urls.add(GENERATOR.getNew(true, false));
		urls.add(GENERATOR.getNew(true, false));
		URI baseURI = GENERATOR.getNew(true, false);

		entity
				.addAllItems(urls)
				.setOffset(2L)
				.setLimit(DEFAULT_LIMIT)
				.setBaseURI(baseURI)
				.setTotalNumberOfItems(1L);

		entity.build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuild_OffsetEqualsToNumberOfElementsWhenNotZero() throws Exception {
		List<URI> urls = new ArrayList<>();
		urls.add(GENERATOR.getNew(true, false));
		urls.add(GENERATOR.getNew(true, false));
		urls.add(GENERATOR.getNew(true, false));
		URI baseURI = GENERATOR.getNew(true, false);

		entity
				.addAllItems(urls)
				.setOffset(2L)
				.setLimit(DEFAULT_LIMIT)
				.setBaseURI(baseURI)
				.setTotalNumberOfItems(2L);

		entity.build();
	}

	private Constructor<CollectionResource> getConstructorFromBuilder() {
		return new ClassWrapper<>(CollectionResource.class).getConstructor(CollectionResource.Init.class);
	}
}