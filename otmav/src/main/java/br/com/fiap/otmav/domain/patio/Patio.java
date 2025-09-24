package br.com.fiap.otmav.domain.patio;

import br.com.fiap.otmav.domain.filial.Filial;
import br.com.fiap.otmav.domain.regiao.Regiao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "patio")
@Getter
@Setter
@NoArgsConstructor
public class Patio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_patio")
    private Long id;

    @NotNull
    @Column(name = "total_motos", nullable = false)
    private Integer totalMotos;

    @NotNull
    @Column(name = "capacidade_moto", nullable = false)
    private Integer capacidadeMoto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_filial", referencedColumnName = "id_filial")
    private Filial filial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_regiao", referencedColumnName = "id_regiao")
    private Regiao regiao;
}
