package be.normegil.mylibrary.framework;

import be.normegil.mylibrary.tools.EntityHelper;
import be.normegil.mylibrary.tools.fake.FakeEntity;
import be.normegil.mylibrary.tools.generator.MangaGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;

public class UTEntity {

	private static final UUID DEFAULT_ID = UUID.fromString("dde907d3-3607-4163-a326-4fdf7dc3ffcd");
	private static final UUID ALTERNATIVE_ID = UUID.fromString("d52a73bb-7a43-43c3-8c00-db639c84d084");
	private Entity entity;

	@Before
	public void setUp() throws Exception {
		entity = new FakeEntity();
		new EntityHelper().setId(entity, DEFAULT_ID);
	}

	@After
	public void tearDown() throws Exception {
		entity = null;
	}

	@Test
	public void testGetId() throws Exception {
		Optional<UUID> idOptional = entity.getId();
		assertEquals(idOptional.get(), DEFAULT_ID);
	}

	@Test
	public void testGetId_NullID() throws Exception {
		new EntityHelper().setId(entity, null);
		Optional<UUID> idOptional = entity.getId();
		assertFalse(idOptional.isPresent());
	}

	@Test
	public void testEquality_Null() throws Exception {
		assertFalse(entity.equals(null));
	}

	@Test
	public void testEquality_SameObject() throws Exception {
		assertTrue(entity.equals(entity));
	}

	@Test
	public void testEquality_DifferentObject() throws Exception {
		assertFalse(entity.equals(new MangaGenerator().getDefault(false, false)));
	}

	@Test
	public void testEquality_NullIDs() throws Exception {
		new EntityHelper().setId(entity, null);
		Entity newEntity = new FakeEntity();
		assertTrue(entity.equals(newEntity));
	}

	@Test
	public void testEquality_SameIDs() throws Exception {
		Entity newEntity = new FakeEntity();
		new EntityHelper().setId(newEntity, entity.getId().get());
		assertTrue(entity.equals(newEntity));
	}

	@Test
	public void testEquality_DifferentIDs() throws Exception {
		Entity newEntity = new FakeEntity();
		new EntityHelper().setId(newEntity, ALTERNATIVE_ID);
		assertFalse(entity.equals(newEntity));
	}

	@Test
	public void testHashcode_NullID() throws Exception {
		new EntityHelper().setId(entity, null);
		assertEquals(629, entity.hashCode());
	}

	@Test
	public void testHashcode() throws Exception {
		assertEquals(889977111, entity.hashCode());
	}
}
