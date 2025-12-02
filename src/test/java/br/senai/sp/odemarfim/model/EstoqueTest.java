package br.senai.sp.odemarfim.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EstoqueTest{

	@Test
	void criarEstoqueComCamposValidos() {
		Estoque estoque = new Estoque();
		estoque.setTipo("Entrada");
		estoque.setNome("Ração Premium");
		estoque.setDescricao("Ração para cães adultos 10kg");
		estoque.setQntd(23);
		estoque.setPrecoVenda(199.90f);
		estoque.setImagem("/uploads/racao.png");

		assertEquals("Entrada", estoque.getTipo());
		assertEquals("Ração Premium", estoque.getNome());
		assertEquals("Ração para cães adultos 10kg", estoque.getDescricao());
		assertEquals(23, estoque.getQntd());
		assertEquals(199.90f, estoque.getPrecoVenda());
		assertNotNull(estoque.getImagem());
	}
}


