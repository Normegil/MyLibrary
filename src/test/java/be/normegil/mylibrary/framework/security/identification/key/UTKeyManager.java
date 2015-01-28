package be.normegil.mylibrary.framework.security.identification.key;

import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.tools.IGenerator;
import be.normegil.mylibrary.tools.dao.KeyMemoryDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.KeyPair;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UTKeyManager {

	private static final IGenerator<Key> GENERATOR = GeneratorRepository.get(Key.class);
	private KeyDAO dao;
	private KeyManager keyManager;

	@Before
	public void setUp() throws Exception {
		dao = new KeyMemoryDAO();
		keyManager = new KeyManager(dao);
	}

	@After
	public void tearDown() throws Exception {
		keyManager = null;
		dao = null;
	}

	@Test
	public void testLoad_ExistingKey_checkPublicKey() throws Exception {
		Key key = GENERATOR.getNew(false, true);
		dao.persist(key);
		KeyPair keyPair = keyManager.load(key.getName(), key.getType());
		assertEquals(key.getPublicKey(), keyPair.getPublic());
	}

	@Test
	public void testLoad_ExistingKey_checkPrivateKey() throws Exception {
		Key key = GENERATOR.getNew(false, true);
		dao.persist(key);
		KeyPair keyPair = keyManager.load(key.getName(), key.getType());
		assertEquals(key.getPrivateKey(), keyPair.getPrivate());
	}

	@Test
	public void testLoad_NonExistingKey_checkCreatedKeyExist() throws Exception {
		String testKey = "NewTestKey";
		keyManager.load(testKey, KeyType.RSA);
		Optional<Key> keyOptional = dao.getByName(testKey);
		assertTrue(keyOptional.isPresent());
	}

	@Test
	public void testLoad_NonExistingKey_checkCreatedKeyType() throws Exception {
		String testKey = "NewTestKey";
		KeyType keyType = KeyType.RSA;
		keyManager.load(testKey, keyType);
		Optional<Key> keyOptional = dao.getByName(testKey);
		assertEquals(keyType, keyOptional.get().getType());
	}
}
