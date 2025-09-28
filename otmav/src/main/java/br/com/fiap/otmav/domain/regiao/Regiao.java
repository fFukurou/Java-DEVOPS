package br.com.fiap.otmav.domain.regiao;

import br.com.fiap.otmav.domain.patio.Patio;
import br.com.fiap.otmav.domain.setor.Setor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "regiao")
@Getter
@Setter
@NoArgsConstructor
public class Regiao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_regiao")
    private Long id;

    @Column(name = "localizacao", length = 255)
    private String localizacaoWkt;

    @NotNull
    @Column(name = "area", nullable = false)
    private Double area;

    @OneToMany(mappedBy = "regiao")
    private List<Patio> patios;

    @OneToMany(mappedBy = "regiao")
    private List<Setor> setores;
}
