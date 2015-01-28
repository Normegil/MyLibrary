package be.normegil.mylibrary.framework.security.identification.key;

import be.normegil.mylibrary.framework.constraint.NotEmpty;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.security.KeyPair;
import java.util.Optional;

@Stateless
public class KeyManager {

	@Inject
	private KeyDAO dao;

	//Should only be used by injection
	protected KeyManager() {
	}

	public KeyManager(final KeyDAO dao) {
		this.dao = dao;
	}

	public KeyPair load(@NotEmpty final String keyPairName, @NotNull final KeyType type) {
		Optional<Key> keyOptional = dao.getByName(keyPairName);
		Key key;
		if (!keyOptional.isPresent()) {
			key = generateNewKey(keyPairName, type);
		} else {
			key = keyOptional.get();
		}
		return new KeyPair(key.getPublicKey(), key.getPrivateKey());
	}

	private synchronized Key generateNewKey(final String keyPairName, final KeyType type) {
		Optional<Key> keyOptional = dao.getByName(keyPairName);
		Key key;
		if (!keyOptional.isPresent()) {
			key = new Key(keyPairName, type);
			dao.persist(key);
		} else {
			key = keyOptional.get();
		}
		return key;
	}
}
