package be.normegil.mylibrary.user;

import be.normegil.mylibrary.framework.constraint.NotEmpty;
import be.normegil.mylibrary.framework.dao.DAO;

import java.util.Optional;

public interface UserDAO extends DAO<User> {

	public Optional<User> getByPseudo(@NotEmpty String pseudo);

}
