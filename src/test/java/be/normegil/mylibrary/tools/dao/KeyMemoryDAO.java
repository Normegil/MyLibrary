package be.normegil.mylibrary.tools.dao;

import be.normegil.mylibrary.tools.CustomCollectors;
import be.normegil.mylibrary.framework.constraint.NotEmpty;
import be.normegil.mylibrary.framework.security.identification.key.Key;
import be.normegil.mylibrary.framework.security.identification.key.KeyDAO;

import java.util.Optional;

public class KeyMemoryDAO extends MemoryDAO<Key> implements KeyDAO {
	@Override
	public Optional<Key> getByName(@NotEmpty final String name) {
		return getAll()
				.filter((k) -> k.getName().equals(name))
				.collect(new CustomCollectors().singletonCollector());
	}

	@Override
	protected boolean correspondingID(final Key key, final Object id) {
		return id.equals(key.getId());
	}
}
