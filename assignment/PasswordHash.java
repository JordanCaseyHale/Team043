package assignment;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class PasswordHash {
	public static String getHashedString(String input) {
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] hashBytes = sha.digest(input.getBytes(StandardCharsets.UTF_8));
			String encoded = Base64.getEncoder().encodeToString(hashBytes);
			return encoded;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
