package fiap.com.sensorium.domain.patio;

import fiap.com.sensorium.domain.filial.Filial;
import fiap.com.sensorium.domain.setor.Setor;
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
@Table(name = "patio")
public class Patio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_patio")
    private Long id;

    @Column(name = "total_motos", nullable = false)
    private Integer totalMotos;

    @Column(name = "capacidade_moto", nullable = false)
    private Integer capacidade;

    @Column(name = "area_patio", nullable = false)
    private Integer area;

    @ManyToOne
    @JoinColumn(name = "id_filial", nullable = false)
    private Filial filial;

    @OneToMany(mappedBy = "patio")
    private List<Setor> setores = new ArrayList<>();
}