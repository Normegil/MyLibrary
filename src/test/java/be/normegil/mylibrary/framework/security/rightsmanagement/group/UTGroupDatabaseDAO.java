package be.normegil.mylibrary.framework.security.rightsmanagement.group;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UTGroupDatabaseDAO {
	@Test
	public void testGetEntityClass() throws Exception {
		assertEquals(Group.class, new GroupDatabaseDAO().getEntityClass());
	}
}
