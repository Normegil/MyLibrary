package be.normegil.mylibrary.framework;

import be.normegil.mylibrary.framework.rest.RESTHelper;
import be.normegil.mylibrary.tools.EntityHelper;
import be.normegil.mylibrary.tools.fake.FakeEntity;
import org.junit.Test;

import javax.ws.rs.Path;
import java.net.URI;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class UTEntityDigest {

	private static final UUID DEFAULT_ID = UUID.fromString("2d961077-f604-4233-9953-3686d0fb84b1");
	private static final URI DEFAULT_URI = URI.create("http://www.example.com/rest");

	@Test
	public void testFromBase() throws Exception {
		Entity entity = new FakeEntity();
		new EntityHelper().setId(entity, DEFAULT_ID);
		Entity.Digest digest = new Entity.Digest() {
		};
		digest.fromBase(DEFAULT_URI, entity);

		Path pathAnnotation = FakeEntity.RestService.class.getAnnotation(Path.class);
		String path = pathAnnotation.value();
		assertEquals(URI.create(DEFAULT_URI + path + RESTHelper.PATH_SEPARATOR + DEFAULT_ID), digest.href);
	}

	@Test
	public void testToBase() throws Exception {
		Path pathAnnotation = FakeEntity.RestService.class.getAnnotation(Path.class);
		String path = pathAnnotation.value();

		Entity.Digest digest = new Entity.Digest() {
		};
		digest.href = URI.create(DEFAULT_URI + path + RESTHelper.PATH_SEPARATOR + DEFAULT_ID);
		Entity toTest = new FakeEntity();
		digest.toBase(toTest);

		Entity expected = new FakeEntity();
		new EntityHelper().setId(expected, DEFAULT_ID);
		assertEquals(expected, toTest);
	}
}
