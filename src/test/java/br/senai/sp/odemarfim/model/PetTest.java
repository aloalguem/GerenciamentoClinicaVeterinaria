package br.senai.sp.odemarfim.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PetTest {

	@Test
	void criarPetComCamposCorretos() {
		Pet pet = new Pet();
		pet.setNome("Bolt");
		pet.setRaca("Labrador");
		pet.setEspecie("Cachorro");
		pet.setDataNascimento(LocalDate.of(2020, 5, 20));
		pet.setPorte("Grande");
		pet.setDoenca("Nenhuma");
		pet.setPeso("30kg");

		assertEquals("Bolt", pet.getNome());
		assertEquals("Labrador", pet.getRaca());
		assertEquals("Cachorro", pet.getEspecie());
		assertEquals(LocalDate.of(2020, 5, 20), pet.getDataNascimento());
		assertEquals("Grande", pet.getPorte());
		assertEquals("Nenhuma", pet.getDoenca());
		assertEquals("30kg", pet.getPeso());
		assertNotNull(pet.toString());
	}
}

