package fiap.com.sensorium.domain.funcionario;

import fiap.com.sensorium.domain.dados.Dados;
import fiap.com.sensorium.domain.filial.Filial;
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
@Table(name = "funcionario")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_func")
    private Long id;

    @Column(nullable = false, length = 100)
    private String cargo;

    @OneToOne
    @JoinColumn(name = "id_dados", nullable = false)
    private Dados dados;

    @OneToMany(mappedBy = "funcionario")
    private List<Filial> filiais = new ArrayList<>();
}