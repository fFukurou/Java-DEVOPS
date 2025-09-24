package br.com.fiap.otmav.domain.situacao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "situacao")
@Getter
@Setter
@NoArgsConstructor
public class Situacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_situacao")
    private Long id;

    @NotBlank
    @Size(max = 250)
    @Column(name = "nome", length = 250, nullable = false)
    private String nome;

    @Size(max = 250)
    @Column(name = "descricao", length = 250)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50, nullable = false)
    private SituacaoStatus status;
}
