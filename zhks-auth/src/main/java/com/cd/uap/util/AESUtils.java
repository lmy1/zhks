package com.cd.uap.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@SuppressWarnings("restriction")
public class AESUtils {
	
	private static final String KEY = "dbxqIx7nNIVOq09Q";
	
	private static final String IV = "penSxyQeYDV42ERI";
	
	public static void main(String args[]) throws Exception {
		System.out.println("encrypted: " + encrypt("#cd123456"));//dcGaG/f3qlrczwZ7067nuA==
		System.out.println("decrypted: " + desEncrypt("aK7+UX24ttBgfTnAndz9aQ=="));
	}

	public static String encrypt(String data) throws InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {

		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		int blockSize = cipher.getBlockSize();
		byte[] dataBytes = data.getBytes();
		int plaintextLength = dataBytes.length;
		if (plaintextLength % blockSize != 0) {
			plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
		}
		byte[] plaintext = new byte[plaintextLength];
		System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
		SecretKeySpec keyspec = new SecretKeySpec(KEY.getBytes(), "AES");
		IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
		byte[] encrypted = cipher.doFinal(plaintext);
		return new sun.misc.BASE64Encoder().encode(encrypted);

	}

	public static String desEncrypt(String data) throws IllegalBlockSizeException, IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException {

		byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		SecretKeySpec keyspec = new SecretKeySpec(KEY.getBytes(), "AES");
		IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
		byte[] original = cipher.doFinal(encrypted1);
		String originalString = new String(original);
		return originalString.trim();

	}
}