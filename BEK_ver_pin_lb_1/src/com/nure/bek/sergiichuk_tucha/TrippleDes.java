package com.nure.bek.sergiichuk_tucha;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;



public class TrippleDes {
	private static final String UNICODE_FORMAT = "UTF8";
	public static final String DESEDE_ENCRYPTION_SHEME = "DESede";
	private KeySpec ks;
	private SecretKeyFactory skf;
	private Cipher cipher;
	byte[] arrayBytes;
	private String myEncryptionKey;
	private String myEncryptionScheme;
	SecretKey key;

	public TrippleDes() throws Exception {
		
		myEncryptionKey = "ThisIsSpartaThisIsSparta";
		myEncryptionScheme = DESEDE_ENCRYPTION_SHEME;
		try {
			arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);

			ks = new DESedeKeySpec(arrayBytes);

			skf = SecretKeyFactory.getInstance(myEncryptionScheme);

			cipher = Cipher.getInstance(myEncryptionScheme);

			key = skf.generateSecret(ks);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new Exception(e);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new Exception(e);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception(e);
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			throw new Exception(e);
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	public String encrypt(String unencryptedString){
		String encryptedString = null;
			try {
				cipher.init(Cipher.ENCRYPT_MODE, key);
				byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
				
				byte[] encryptedText = cipher.doFinal(plainText);
				encryptedString = Base64.encodeBase64String(encryptedText);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
		
		return encryptedString;
	}
	
	public String decrypt(String encryptedString){
		String decryptedText = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] encryptedText = Base64.decodeBase64(encryptedString);
			byte[] plainText = cipher.doFinal(encryptedText);
			decryptedText = new String(plainText);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return decryptedText;
	}
	
	public static void main(String[] args) {
		TrippleDes td = null;
		try {
			td = new TrippleDes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (td == null)
			return;
		String target = "imparator";
		String encrypted = td.encrypt(target);
		String decrypted = td.decrypt(encrypted);
		System.out.println("String to enc: "+target);
		System.out.println("Enc: " + encrypted);
		System.out.println("Dec: "+decrypted);
	}
}