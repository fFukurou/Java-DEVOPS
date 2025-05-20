package fiap.com.sensorium.domain.filial;

import fiap.com.sensorium.domain.endereco.Endereco;
import fiap.com.sensorium.domain.funcionario.Funcionario;
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
@Table(name = "filial")
public class Filial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_filial")
    private Long id;

    @Column(name = "nome_filial", length = 150)
    private String nomeFilial;

    @ManyToOne
    @JoinColumn(name = "id_endereco", nullable = false)
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "id_responsavel", nullable = false)
    private Funcionario responsavel;

    @ManyToOne
    @JoinColumn(name = "id_func", nullable = false)
    private Funcionario funcionario;

    @OneToMany(mappedBy = "filial")
    private List<Patio> patios = new ArrayList<>();


}