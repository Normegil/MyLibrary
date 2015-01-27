package be.normegil.mylibrary.framework.security.identification.key;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UTKeyDatabaseDAO {

	@Test
	public void testGetEntityClass() throws Exception {
		assertEquals(Key.class, new KeyDatabaseDAO().getEntityClass());
	}

}
