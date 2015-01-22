package be.normegil.mylibrary.framework.security.rightsmanagement.resource;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UTSpecificResourceDatabaseDAO {
	@Test
	public void testGetEntityClass() throws Exception {
		assertEquals(SpecificResource.class, new SpecificResourceDatabaseDAO().getEntityClass());
	}
}
