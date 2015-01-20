package be.normegil.mylibrary.framework.security.rightsmanagement.group;

import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.FieldWrapper;
import be.normegil.mylibrary.tools.Generator;
import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.user.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UTGroup {

	private static final int USER_INITIAL_SIZE = 2;
	private Generator<User> userGenerator = GeneratorRepository.get(User.class);
	private static final String NAME = "Group";

	private Group group;

	@Before
	public void setUp() throws Exception {
		group = new Group(NAME);
		for (int i = 0; i < USER_INITIAL_SIZE; i++) {
			group.addUser(userGenerator.getNew(false, false));
		}
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
	public void testAddAllUsers() throws Exception {
		List<User> users = group.getUsers();

		HashSet<User> toAdd = new HashSet<>();
		toAdd.add(userGenerator.getNew(true, false));
		toAdd.add(userGenerator.getNew(true, false));
		toAdd.add(userGenerator.getNew(true, false));

		users.addAll(toAdd);
		group.addAllUsers(toAdd);
		assertEquals(users, group.getUsers());
	}

	@Test
	public void testAddAllUsers_UserConsistency() throws Exception {
		HashSet<User> toAdd = new HashSet<>();
		User user1 = userGenerator.getNew(true, false);
		User user2 = userGenerator.getNew(true, false);
		User user3 = userGenerator.getNew(true, false);
		toAdd.add(user1);
		toAdd.add(user2);
		toAdd.add(user3);
		group.addAllUsers(toAdd);

		assertTrue(user1.getGroups().contains(group));
		assertTrue(user2.getGroups().contains(group));
		assertTrue(user3.getGroups().contains(group));
	}

	@Test
	public void testAddUser() throws Exception {
		List<User> users = group.getUsers();
		User toAdd = userGenerator.getNew(false, false);
		group.addUser(toAdd);
		users.add(toAdd);
		assertEquals(users, group.getUsers());
	}

	@Test
	public void testAddUser_UserConsistency() throws Exception {
		User toAdd = userGenerator.getNew(false, false);
		group.addUser(toAdd);
		assertTrue(toAdd.getGroups().contains(group));
	}

	@Test
	public void testRemoveAllUsers() throws Exception {
		User user1 = userGenerator.getNew(false, false);
		User user2 = userGenerator.getNew(false, false);
		User user3 = userGenerator.getNew(false, false);

		Collection<User> toAdd = new HashSet<>();
		toAdd.add(user1);
		toAdd.add(user2);
		toAdd.add(user3);

		HashSet<User> toRemove = new HashSet<>();
		toRemove.add(user2);
		toRemove.add(user3);

		List<User> users = group.getUsers();
		users.add(user1);

		group.addAllUsers(toAdd);
		group.removeAllUsers(toRemove);

		assertEquals(users, group.getUsers());
	}

	@Test
	public void testRemoveAllUsers_UserConsistency() throws Exception {
		User user1 = userGenerator.getNew(false, false);
		User user2 = userGenerator.getNew(false, false);
		User user3 = userGenerator.getNew(false, false);

		Collection<User> toAdd = new HashSet<>();
		toAdd.add(user1);
		toAdd.add(user2);
		toAdd.add(user3);

		HashSet<User> toRemove = new HashSet<>();
		toRemove.add(user2);
		toRemove.add(user3);

		group.addAllUsers(toAdd);
		group.removeAllUsers(toRemove);

		assertTrue(user1.getGroups().contains(group));
		assertFalse(user2.getGroups().contains(group));
		assertFalse(user3.getGroups().contains(group));
	}

	@Test
	public void testRemoveUser() throws Exception {
		List<User> users = group.getUsers();

		User toRemove = group.getUsers().iterator().next();
		group.removeUser(toRemove);
		users.remove(toRemove);

		assertEquals(users, group.getUsers());
	}

	@Test
	public void testRemoveUser_UserConsistency() throws Exception {
		List<User> users = group.getUsers();

		User toRemove = group.getUsers().iterator().next();
		group.removeUser(toRemove);
		users.remove(toRemove);

		assertEquals(users, group.getUsers());
	}

	@Test
	public void testAddUser_IgnoreUserInconsitency() throws Exception {
		User user = Mockito.mock(User.class);
		HashSet<Group> groups = new HashSet<>();
		groups.add(group);
		when(user.getGroups()).thenReturn(groups);

		group.addUser(user);
		verify(user, Mockito.never()).addGroup(group);
	}

	@Test
	public void testRemoveUser_IgnoreUserInconsitency() throws Exception {
		User user = Mockito.mock(User.class);
		FieldWrapper usersField = new ClassWrapper<>(Group.class).getField("users");
		List<User> users = (List<User>) usersField.get(group);
		users.add(user);

		group.removeUser(user);
		verify(user, Mockito.never()).removeGroup(group);
	}
}
