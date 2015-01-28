package be.normegil.mylibrary.tools.dao;

import be.normegil.mylibrary.framework.constraint.NotEmpty;
import be.normegil.mylibrary.framework.security.identification.key.Key;
import be.normegil.mylibrary.framework.security.identification.key.KeyDAO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class KeyMemoryDAO extends MemoryDAO<Key> implements KeyDAO {
	@Override
	public Optional<Key> getByName(@NotEmpty final String name) {
		List<Key> keys = getAll()
				.filter((k) -> k.getName().equals(name))
				.collect(Collectors.toList());
		if (keys.size() == 0) {
			return Optional.empty();
		} else if (keys.size() == 1) {
			return Optional.of(keys.iterator().next());
		} else {
			throw new IllegalStateException("Key name is not unique");
		}
	}

	@Override
	protected boolean correspondingID(final Key key, final Object id) {
		return id.equals(key.getId());
	}
}
