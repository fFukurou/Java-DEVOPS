package fiap.com.sensorium.domain.setor;

import fiap.com.sensorium.domain.moto.Moto;
import fiap.com.sensorium.domain.patio.Patio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "setor")
public class Setor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_setor")
    private Long id;

    @Column(name = "qtd_moto", nullable = false)
    private Integer quantidadeMotos;

    @Column(nullable = false)
    private Integer capacidade;

    @Column(name = "area_setor", nullable = false)
    private Integer area;

    @Column(name = "nome_setor", length = 250)
    private String nome;

    @Column(length = 250)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "id_patio", nullable = false)
    private Patio patio;

    @OneToMany(mappedBy = "setor")
    private List<Moto> motos = new ArrayList<>();
}