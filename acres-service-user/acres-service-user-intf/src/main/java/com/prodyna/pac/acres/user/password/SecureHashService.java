package com.prodyna.pac.acres.user.password;

public interface SecureHashService {

	String calculateHash(String plain);
}
