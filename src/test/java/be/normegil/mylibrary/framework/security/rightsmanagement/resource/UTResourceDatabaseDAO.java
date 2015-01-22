package be.normegil.mylibrary.framework.security.rightsmanagement.resource;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UTResourceDatabaseDAO {
	@Test
	public void testGetEntityClass() throws Exception {
		assertEquals(Resource.class, new ResourceDatabaseDAO().getEntityClass());
	}
}
