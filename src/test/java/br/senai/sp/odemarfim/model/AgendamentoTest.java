package br.senai.sp.odemarfim.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AgendamentoTest {

	@Test
	void criarAgendamentoComTiposCorretos() {
		Pet pet = new Pet();
		pet.setNome("Rex");
		pet.setEspecie("Cachorro");
		pet.setRaca("Vira-lata");
		pet.setPorte("Médio");
		pet.setPeso("12kg");

		Procedimento procedimento = new Procedimento();
		procedimento.setNome("Banho e Tosa");
		procedimento.setValor(99.90);

		Agendamento agendamento = new Agendamento();
		agendamento.setPet(pet);
		agendamento.setProcedimento(procedimento);
		agendamento.setData(LocalDate.of(2025, 8, 26));
		agendamento.setHorario(LocalTime.of(8, 30));
		agendamento.setStatus("Pendente");

		assertEquals(LocalDate.of(2025, 8, 26), agendamento.getData());
		assertEquals(LocalTime.of(8, 30), agendamento.getHorario());
		assertEquals("Banho e Tosa", agendamento.getServico());
		assertNotNull(agendamento.getPet());
		assertNotNull(agendamento.getProcedimento());
	}
}









