package com.example.practicejpa.utils.other;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.util.Base64;

public class AES256 {
	
	public static String encryptAES256(String msg, String key) throws Exception {
		
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[20];
		random.nextBytes(bytes);
		
		// Password-Based Key Derivation function 2
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		
		// 70000번 해시하여 256 bit 길이의 키를 만든다.
		PBEKeySpec spec = new PBEKeySpec(key.toCharArray(), bytes, 70000, 256);
		
		SecretKey secretKey = factory.generateSecret(spec);
		SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
		
		// 알고리즘/모드/패딩
		// CBC : Cipher Block Chaining Mode
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secret);
		AlgorithmParameters params = cipher.getParameters();
		
		// Initial Vector(1단계 암호화 블록용) - 처음 만들어질 때 이전 값이 없기 떄문에 첫 암호화시 필요한 값
		byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
		byte[] encryptedTextBytes = cipher.doFinal(msg.getBytes("UTF-8"));
		
		byte[] buffer = new byte[bytes.length + ivBytes.length + encryptedTextBytes.length];
		System.arraycopy(bytes, 0, buffer, 0, bytes.length);
		System.arraycopy(ivBytes, 0, buffer, bytes.length, ivBytes.length);
		System.arraycopy(encryptedTextBytes, 0, buffer, bytes.length + ivBytes.length, encryptedTextBytes.length);
		
		return Base64.getEncoder().encodeToString(buffer);
	}
	
	public static String decryptAES256(String msg, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		ByteBuffer buffer = ByteBuffer.wrap(Base64.getDecoder().decode(msg));
		
		byte[] saltBytes = new byte[20];
		buffer.get(saltBytes, 0, saltBytes.length);
		byte[] ivBytes = new byte[cipher.getBlockSize()];
		buffer.get(ivBytes, 0, ivBytes.length);
		byte[] encryoptedTextBytes = new byte[buffer.capacity() - saltBytes.length - ivBytes.length];
		buffer.get(encryoptedTextBytes);
		
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		PBEKeySpec spec = new PBEKeySpec(key.toCharArray(), saltBytes, 70000, 256);
		
		SecretKey secretKey = factory.generateSecret(spec);
		SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
		
		cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));
		
		byte[] decryptedTextBytes = cipher.doFinal(encryoptedTextBytes);
		return new String(decryptedTextBytes);
	}
	
}
