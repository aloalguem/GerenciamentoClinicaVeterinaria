package br.senai.sp.odemarfim.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProcedimentoTest {

	@Test
	void criarProcedimentoBasico() {
		Veterinario veterinario = new Veterinario();
		veterinario.setNome("Dra. Ana");

		Procedimento procedimento = new Procedimento();
		procedimento.setNome("Consulta");
		procedimento.setValor(150.0);
		procedimento.setVeterinario(veterinario);

		assertEquals("Consulta", procedimento.getNome());
		assertEquals(150.0, procedimento.getValor());
		assertNotNull(procedimento.getVeterinario());
	}
}