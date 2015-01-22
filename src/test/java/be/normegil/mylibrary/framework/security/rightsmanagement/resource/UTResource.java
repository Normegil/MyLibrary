package be.normegil.mylibrary.framework.security.rightsmanagement.resource;

import be.normegil.mylibrary.manga.MangaREST;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class UTResource {

	@Test
	public void testGetRestService() throws Exception {
		Class<MangaREST> restService = MangaREST.class;
		Resource resource = new Resource(restService);
		assertEquals(restService, resource.getRestService());
	}

	@Test
	public void testToString() throws Exception {
		Class<MangaREST> restService = MangaREST.class;
		Resource resource = new Resource(restService);
		assertFalse(StringUtils.isBlank(resource.toString()));
	}
}
