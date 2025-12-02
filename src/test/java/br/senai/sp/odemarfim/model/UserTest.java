package br.senai.sp.odemarfim.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTest {

	@Test
	void criarUsuarioComEmailESenha() {
		User user = new User();
		user.setEmail("joao@example.com");
		user.setSenha("123456");

		assertEquals("joao@example.com", user.getUsername());
		assertEquals("123456", user.getPassword());
		assertNotNull(user.getAuthorities());
	}
}