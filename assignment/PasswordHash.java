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
	
	public static String checkInput(String query) {
		query = query.toUpperCase();
		if(query.contains(";")) {
			query = query.substring(0,query.indexOf(';'));
		}
		if(query.contains("DROP TABLE")) {
			query = query.substring(0,query.indexOf("DROP TABLE"));
		}
		if(query.contains("DELETE FROM")) {
			query = query.substring(0,query.indexOf("DELETE FROM"));
		}
		if(query.contains("INSERT INTO")) {
			query = query.substring(0,query.indexOf("INSERT INTO"));
		}
		return query;
	}
}
