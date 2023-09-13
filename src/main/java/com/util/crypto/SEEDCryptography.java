package com.util.crypto;

public class SEEDCryptography {

	private byte[] keyBytes;

	public SEEDCryptography(String key) {
		this.keyBytes = key.getBytes();
	}

	public byte[] encrypt(byte[] plainBytes) {
		return Seed128Cipher.encrypt(plainBytes, keyBytes);
	}

	public byte[] decrypt(byte[] ecryptedBytes) {
		return Seed128Cipher.decrypt(ecryptedBytes, keyBytes);
	}
}
