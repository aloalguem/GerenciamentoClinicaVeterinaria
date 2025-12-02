package br.senai.sp.odemarfim.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    // caminho da imagem (ex: /uploads/produto123.png)
    private String imagem;

    @NotEmpty(message = "O tipo não pode ser vazio")
    private String tipo;

    @NotEmpty(message = "O nome não pode ser vazio")
    private String nome;

    @NotEmpty(message = "A descrição não pode ser vazia")
    private String descricao;

    @NotNull(message = "Insira um número")
    private Integer qntd;  // melhor usar wrapper para funcionar com @NotNull

    @NotNull(message = "Insira um valor válido")
    private Float precoVenda; // idem: wrapper evita erro
}
