package be.normegil.mylibrary.framework.security.rightsmanagement.resource;

import be.normegil.mylibrary.manga.MangaREST;
import be.normegil.mylibrary.tools.fake.FakeEntity;
import be.normegil.mylibrary.tools.test.AbstractDataEqualityTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class UTResourceEquality extends AbstractDataEqualityTest<Resource> {

	@Override
	protected Resource getNewEntity() {
		return new Resource(MangaREST.class);
	}

	@Test
	public void testUnchanged() throws Exception {
		Resource entity = getEntity();
		Resource copy = new Resource(entity.getRestService());
		assertEquals(entity, copy);
	}

	@Test
	public void testDifferentRestService() throws Exception {
		Resource entity = getEntity();
		Resource copy = new Resource(FakeEntity.RestService.class);
		assertNotEquals(entity, copy);
	}

	@Test
	public void testHashcode() throws Exception {
		Resource entity = getNewEntity();
		assertNotNull(entity.hashCode());
	}
}
