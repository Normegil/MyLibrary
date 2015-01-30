package be.normegil.mylibrary.tools.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public abstract class AbstractDataEqualityTest<E> {

	private static final Object OTHER_OBJECT = "OtherObject";
	private E entity;

	protected abstract E getNewEntity();

	protected E getEntity() {
		return entity;
	}

	@Before
	public void setUp() throws Exception {
		entity = getNewEntity();
	}

	@After
	public void tearDown() throws Exception {
		entity = null;
	}

	@Test
	public void testNull() throws Exception {
		assertNotEquals(entity, null);
	}

	@Test
	public void testSameObject() throws Exception {
		assertEquals(entity, entity);
		assertEquals(entity.hashCode(), entity.hashCode());
	}

	@Test
	public void testOtherObject() throws Exception {
		assertNotEquals(entity, OTHER_OBJECT);
		assertNotEquals(entity.hashCode(), OTHER_OBJECT.hashCode());
	}
}
