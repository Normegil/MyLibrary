package be.normegil.mylibrary.tools.generator;

import be.normegil.mylibrary.framework.security.identification.key.Key;
import be.normegil.mylibrary.framework.security.identification.key.KeyType;
import be.normegil.mylibrary.tools.EntityHelper;
import be.normegil.mylibrary.tools.Generator;
import be.normegil.mylibrary.tools.IGenerator;

import java.util.UUID;

@Generator(Key.class)
public class KeyGenerator implements IGenerator<Key> {

	private static final UUID DEFAULT_ID = UUID.fromString("880d4127-d5bc-4690-b8b7-518752d8a215");
	public static final String KEY_NAME = "TestKeyName";
	public static final KeyType KEY_TYPE = KeyType.ECDSA;
	private static long index = 0L;

	@Override
	public Key getDefault(final boolean withLink, final boolean withIds) {
		Key key = new Key(KEY_NAME, KEY_TYPE);
		if (withIds) {
			new EntityHelper().setId(key, DEFAULT_ID);
		}
		return key;
	}

	@Override
	public Key getNew(final boolean withLink, final boolean withIds) {
		Key key = new Key(KEY_NAME + index, KEY_TYPE);
		if (withIds) {
			new EntityHelper().setId(key, UUID.randomUUID());
		}
		return key;
	}

}
