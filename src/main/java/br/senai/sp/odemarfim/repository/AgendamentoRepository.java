package br.senai.sp.odemarfim.repository;

import br.senai.sp.odemarfim.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface AgendamentoRepository  extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findAll();

    // REMOVED: List<Agendamento> findByDataContainingIgnoreCase(String data); (This caused an error)

    // Buscar por data exata
    List<Agendamento> findByData(LocalDate data);

    // Buscar por intervalo de datas
    List<Agendamento> findByDataBetween(LocalDate inicio, LocalDate fim);

    // Buscar por pet
    List<Agendamento> findByPetId(Long petId);

    // Buscar agendamentos por tutor (através dos pets)
    @Query("SELECT a FROM Agendamento a WHERE a.pet.tutorId = :tutorId ORDER BY a.data DESC, a.horario DESC")
    List<Agendamento> findByTutorId(@Param("tutorId") Long tutorId);
}