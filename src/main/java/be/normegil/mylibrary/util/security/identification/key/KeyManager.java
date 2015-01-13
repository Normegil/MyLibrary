package be.normegil.mylibrary.util.security.identification.key;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.security.KeyPair;
import java.util.Optional;

@Stateless
public class KeyManager {

	@Inject
	private KeyDatabaseDAO dao;

	public KeyPair load(String keyPairName, KeyType type) {
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
