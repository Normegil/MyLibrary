package be.normegil.mylibrary.tools.dao;

import be.normegil.mylibrary.tools.CustomCollectors;
import be.normegil.mylibrary.framework.constraint.NotEmpty;
import be.normegil.mylibrary.user.User;
import be.normegil.mylibrary.user.UserDAO;

import java.util.Optional;

public class UserMemoryDAO extends MemoryDAO<User> implements UserDAO {

	@Override
	protected boolean correspondingID(final User user, final Object id) {
		return id.equals(user.getId());
	}

	@Override
	public Optional<User> getByPseudo(@NotEmpty final String pseudo) {
		return getAll()
				.filter((u) -> pseudo.equals(u.getPseudo()))
				.collect(new CustomCollectors().singletonCollector());
	}
}
