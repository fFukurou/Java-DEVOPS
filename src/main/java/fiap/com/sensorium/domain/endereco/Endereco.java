package fiap.com.sensorium.domain.endereco;

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
@Table(name = "endereco")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco")
    private Long id;

    @Column(nullable = false)
    private Integer numero;

    @Column(nullable = false, length = 30)
    private String estado;

    @Column(name = "codigo_pais", nullable = false, length = 50)
    private String codigoPais;

    @Column(name = "codigo_postal", nullable = false, length = 50)
    private String cep;

    @Column(length = 150)
    private String complemento;

    @Column(nullable = false, length = 100)
    private String rua;

    @OneToMany(mappedBy = "endereco")
    private List<Filial> filiais = new ArrayList<>();
}