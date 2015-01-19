package be.normegil.mylibrary.framework.security.rightsmanagement.group;

import be.normegil.mylibrary.user.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UTGroup {

	public static final String NAME = "Group";

	private Group group;

	@Before
	public void setUp() throws Exception {
		group = new Group(NAME);
	}

	@After
	public void tearDown() throws Exception {
		group = null;
	}

	@Test
	public void testConstructor() throws Exception {
		Group group = new Group(NAME);
		assertEquals(NAME, group.getName());
	}

	@Test
	public void testGetUser_Empty() throws Exception {
		group.removeAllUsers(group.getUsers());
		List<User> users = group.getUsers();
		assertEquals(0, users.size());
	}

	@Test
	public void testGetUser() throws Exception {
		group.addUser();
	}
}
