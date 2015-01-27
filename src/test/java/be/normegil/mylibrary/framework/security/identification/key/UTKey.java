package be.normegil.mylibrary.framework.security.identification.key;

import be.normegil.mylibrary.tools.generator.KeyGenerator;
import org.junit.Test;
import org.mockito.Mockito;

import java.security.KeyPairGenerator;
import java.security.SecureRandom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class UTKey {

	public static final String KEY_NAME = "Test";
	public static final KeyType KEY_TYPE = KeyType.ECDSA;

	@Test
	public void testConstructor_Name() throws Exception {
		Key key = new Key(KEY_NAME, KEY_TYPE);
		assertEquals(KEY_NAME, key.getName());
	}

	@Test
	public void testConstructor_KeyType() throws Exception {
		Key key = new Key(KEY_NAME, KEY_TYPE);
		assertEquals(KEY_TYPE, key.getType());
	}

	@Test
	public void testConstructor_PublicKey_Algorithm() throws Exception {
		Key key = new Key(KEY_NAME, KEY_TYPE);
		assertEquals(KEY_TYPE.getAlgorythmName(), key.getPublicKey().getAlgorithm());
	}

	@Test
	public void testConstructor_PrivateKey_Algorithm() throws Exception {
		Key key = new Key(KEY_NAME, KEY_TYPE);
		assertEquals(KEY_TYPE.getAlgorythmName(), key.getPrivateKey().getAlgorithm());
	}

	@Test
	public void testConstructor_PrivateKey() throws Exception {
		Key key = new Key(KEY_NAME, KEY_TYPE);
		assertNotNull(key.getPrivateKey());
	}

	@Test
	public void testConstructor_PublicKey() throws Exception {
		Key key = new Key(KEY_NAME, KEY_TYPE);
		assertNotNull(key.getPublicKey());
	}

	@Test
	public void testGetKeyPairGenerator_InitializedWithKeySize() throws Exception {
		final KeyType defaultKeyType = KeyGenerator.KEY_TYPE;

		SecureRandom secureRandom = new SecureRandom();
		KeyPairGenerator mockGenerator = Mockito.mock(KeyPairGenerator.class);
		KeyPairGenerator generator = KeyPairGenerator.getInstance(defaultKeyType.getAlgorythmName());
		generator.initialize(defaultKeyType.getDefaultKeySize(), secureRandom);

		when(mockGenerator.generateKeyPair())
				.thenReturn(generator.generateKeyPair());
		new Key(KeyGenerator.KEY_NAME, defaultKeyType) {
			@Override
			protected KeyPairGenerator getKeyPairGenerator(final String algorythmName) {
				return mockGenerator;
			}
		};
		verify(mockGenerator, times(1))
				.initialize(eq(defaultKeyType.getDefaultKeySize()), Mockito.any());
	}
}
