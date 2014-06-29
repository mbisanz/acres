package com.prodyna.pac.acres.user.password;

import java.security.MessageDigest;

import javax.ejb.Stateless;
import javax.xml.bind.DatatypeConverter;

import com.prodyna.pac.acres.common.logging.Logged;

@Stateless
@Logged
public class SecureHashServiceBean implements SecureHashService {

	private static final String HASH_ALGORITHM = "SHA-256";
	private static final String PASSWORD_ENCODING = "UTF-8";

	@Override
	public String calculateHash(String plain) {
		try {

			byte[] digest = MessageDigest.getInstance(HASH_ALGORITHM).digest(
					plain.getBytes(PASSWORD_ENCODING));
			return DatatypeConverter.printHexBinary(digest);
		} catch (Exception e) {
			// should not happen, as SHA-256 and UTF-8 are always supported
			throw new RuntimeException("Could not calculate hash", e);
		}
	}
}
