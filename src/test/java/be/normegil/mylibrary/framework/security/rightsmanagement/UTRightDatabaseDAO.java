package be.normegil.mylibrary.framework.security.rightsmanagement;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UTRightDatabaseDAO {

	@Test
	public void testGetEntityClass() throws Exception {
		RightDatabaseDAO rightDatabaseDAO = new RightDatabaseDAO();
		assertEquals(Right.class, rightDatabaseDAO.getEntityClass());
	}
}
