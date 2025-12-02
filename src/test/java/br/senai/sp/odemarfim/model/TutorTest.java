package br.senai.sp.odemarfim.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TutorTest {

	@Test
	void criarTutorBasico() {
		Tutor tutor = new Tutor();
		tutor.setNome("Tutor");
		tutor.setEmail("tutor@email.com");
		tutor.setCpf("12345678900");
		tutor.setTelefone("11999999999");
		tutor.setSenha("senhaSegura");

		assertEquals("Tutor", tutor.getNome());
		assertEquals("tutor@email.com", tutor.getEmail());
		assertEquals("12345678900", tutor.getCpf());
		assertEquals("11999999999", tutor.getTelefone());
		assertEquals("senhaSegura", tutor.getSenha());
		assertNotNull(tutor.getUser());
	}
}
