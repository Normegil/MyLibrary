package be.normegil.mylibrary.tools.generator;

import be.normegil.mylibrary.framework.security.rightsmanagement.group.Group;
import be.normegil.mylibrary.tools.EntityHelper;
import be.normegil.mylibrary.tools.Generator;
import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.user.User;

import java.util.UUID;

public class GroupGenerator implements Generator<Group> {

	private static final Generator<User> USER_GENERATOR = GeneratorRepository.get(User.class);
	public static final String NAME = "GroupName";
	public static final UUID DEFAULT_ID = UUID.fromString("ece90d13-eb11-47a4-b5c5-fb952738630b");
	public static final long DEFAULT_USERS_SIZE = 2L;
	private static long index = 0L;

	@Override
	public Group getDefault(final boolean withLink, final boolean withIds) {
		Group group = new Group(NAME);
		if (withLink) {
			User user = USER_GENERATOR.getDefault(false, withIds);
			group.addUser(user);
			user.addGroup(group);
		}
		if (withIds) {
			new EntityHelper().setId(group, DEFAULT_ID);
		}
		return group;
	}

	@Override
	public Group getNew(final boolean withLink, final boolean withIds) {
		Group group = new Group(NAME + index);
		for (long l = 0L; l < DEFAULT_USERS_SIZE & withLink; l++) {
			User user = USER_GENERATOR.getNew(false, withIds);
			group.addUser(user);
			user.addGroup(group);
		}
		if (withIds) {
			new EntityHelper().setId(group, UUID.randomUUID());
		}
		index++;
		return group;
	}
}
