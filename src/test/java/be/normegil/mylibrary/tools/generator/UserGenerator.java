package be.normegil.mylibrary.tools.generator;

import be.normegil.mylibrary.framework.security.rightsmanagement.group.Group;
import be.normegil.mylibrary.tools.EntityHelper;
import be.normegil.mylibrary.tools.Generator;
import be.normegil.mylibrary.tools.IGenerator;
import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.user.User;

import java.util.UUID;

@Generator
public class UserGenerator implements IGenerator<User> {

	private static final IGenerator<Group> GROUP_GENERATOR = GeneratorRepository.get(Group.class);
	public static final String PSEUDO = "Pseudo";
	public static final String HASHED_PASSWORD = "HashedPassword";
	public static final UUID DEFAULT_ID = UUID.fromString("d81596e0-fc7f-4631-80f0-62f3fef5f6fc");
	public static final long DEFAULT_GROUPS_SIZE = 2L;
	private static long index = 0L;

	@Override
	public User getDefault(final boolean withLink, final boolean withIds) {
		User user = new User();
		user.setPseudo(PSEUDO);
		user.setHashedPassword(HASHED_PASSWORD);
		if (withLink) {
			Group group = GROUP_GENERATOR.getDefault(false, withIds);
			user.addGroup(group);
			group.addUser(user);
		}
		if (withIds) {
			new EntityHelper().setId(user, DEFAULT_ID);
		}
		return user;
	}

	@Override
	public User getNew(final boolean withLink, final boolean withIds) {
		User user = new User();
		user.setPseudo(PSEUDO + index);
		user.setHashedPassword(HASHED_PASSWORD + index);
		for (long l = 0L; l < DEFAULT_GROUPS_SIZE & withLink; l++) {
			Group group = GROUP_GENERATOR.getNew(false, withIds);
			user.addGroup(group);
			group.addUser(user);
		}
		if (withIds) {
			new EntityHelper().setId(user, UUID.randomUUID());
		}
		index++;
		return user;
	}

	@Override
	public Class<User> getSupportedClass() {
		return User.class;
	}
}
