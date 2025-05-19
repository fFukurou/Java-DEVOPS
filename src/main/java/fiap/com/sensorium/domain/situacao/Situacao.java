package fiap.com.sensorium.domain.situacao;

import fiap.com.sensorium.domain.moto.Moto;
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
@Table(name = "situacao")
public class Situacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_situacao")
    private Long id;

    @Column(nullable = false, length = 250)
    private String nome;

    @Column(length = 250)
    private String descricao;

    @Column(nullable = false, length = 50)
    private String status;

    @OneToMany(mappedBy = "situacao")
    private List<Moto> motos = new ArrayList<>();
}