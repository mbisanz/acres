package com.prodyna.pac.acres.user.password;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SecureHashServiceTest {

	private SecureHashService service;

	@Before
	public void setUp() throws Exception {
		service = new SecureHashServiceBean();
	}

	@Test
	public void testPasswordEncryptionService() throws Exception {
		String originalPassword = "mySecretPassword4711";
		String hashedPassword = service.calculateHash(originalPassword);
		Assert.assertEquals(service.calculateHash(originalPassword), hashedPassword);
		Assert.assertNotEquals(service.calculateHash("wrongPassword"), hashedPassword);
	}
}
