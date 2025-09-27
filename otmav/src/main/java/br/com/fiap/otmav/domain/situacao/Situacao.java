package br.com.fiap.otmav.domain.situacao;

import br.com.fiap.otmav.domain.moto.Moto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @OneToMany(mappedBy = "situacao", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Moto> motos;
}
