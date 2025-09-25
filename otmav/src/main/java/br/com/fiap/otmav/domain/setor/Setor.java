package br.com.fiap.otmav.domain.setor;

import br.com.fiap.otmav.domain.patio.Patio;
import br.com.fiap.otmav.domain.regiao.Regiao;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "setor")
@Getter
@Setter
@NoArgsConstructor
public class Setor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_setor")
    private Long id;

    @NotNull
    @Min(value = 0, message = "Quantidade de motos nao pode ser negativa.")
    @Column(name = "qtd_moto", nullable = false)
    private Integer qtdMoto;

    @NotNull
    @Min(value = 0, message = "Capacidade nao pode ser negativa.")
    @Column(name = "capacidade", nullable = false)
    private Integer capacidade;

    @Size(max = 250)
    @Column(name = "nome_setor", length = 250)
    private String nomeSetor;

    @Size(max = 250)
    @Column(name = "descricao", length = 250)
    private String descricao;

    @NotNull
    @Size(max = 20)
    @Column(name = "cor", length = 20, nullable = false)
    private String cor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_patio", referencedColumnName = "id_patio")
    private Patio patio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_regiao", referencedColumnName = "id_regiao")
    private Regiao regiao;
}
