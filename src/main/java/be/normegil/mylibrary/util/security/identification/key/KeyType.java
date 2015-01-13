package be.normegil.mylibrary.util.security.identification.key;

public enum KeyType {
	AES("oct", 128),
	RSA("RSA", 2048),
	ECDSA("EC", 521);

	private String algorythmName;
	private int keySize;

	private KeyType(final String algorythmName, final int keySize) {
		this.algorythmName = algorythmName;
		this.keySize = keySize;
	}

	public String getAlgorythmName() {
		return algorythmName;
	}

	public int getDefaultKeySize() {
		return keySize;
	}
}
