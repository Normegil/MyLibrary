package be.normegil.mylibrary.tools.dao;

import be.normegil.mylibrary.framework.constraint.NotEmpty;
import be.normegil.mylibrary.user.User;
import be.normegil.mylibrary.user.UserDAO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserMemoryDAO extends MemoryDAO<User> implements UserDAO {

	@Override
	protected boolean correspondingID(final User user, final Object id) {
		return id.equals(user.getId());
	}

	@Override
	public Optional<User> getByPseudo(@NotEmpty final String pseudo) {
		List<User> users = getAll()
				.filter((u) -> pseudo.equals(u.getPseudo()))
				.collect(Collectors.toList());
		if (users.size() == 0) {
			return Optional.empty();
		} else if (users.size() == 1) {
			return Optional.of(users.iterator().next());
		} else {
			throw new IllegalStateException("More than one user with Pseudo : " + pseudo);
		}
	}
}
