package be.normegil.mylibrary.framework.security.identification.key;

import be.normegil.mylibrary.framework.constraint.NotEmpty;
import be.normegil.mylibrary.framework.dao.DAO;

import java.util.Optional;

public interface KeyDAO extends DAO<Key> {

	public Optional<Key> getByName(@NotEmpty String name);

}
