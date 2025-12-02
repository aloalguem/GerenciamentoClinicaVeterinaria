package br.senai.sp.odemarfim.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    @NotNull(message = "O Pet é obrigatório.")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "procedimento_id")
    @NotNull(message = "O Procedimento é obrigatório.")
    private Procedimento procedimento;

    @NotNull(message = "A data é obrigatória.")
    private LocalDate data;

    @NotNull(message = "O horário é obrigatório.")
    private LocalTime horario;

    private String servico;

    private String status = "PENDENTE";
}