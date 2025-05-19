package fiap.com.sensorium.domain.dados;

import fiap.com.sensorium.domain.funcionario.Funcionario;
import fiap.com.sensorium.domain.motorista.Motorista;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dados")
public class Dados {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dados")
    private Long id;

    @Column(nullable = false, length = 11)
    private String cpf;

    @Column(nullable = false, length = 13)
    private String telefone;

    @Column(length = 255)
    private String email;

    @Column(length = 20)
    private String senha;

    @Column(nullable = false, length = 150)
    private String nome;

    @OneToMany(mappedBy = "dados", cascade = CascadeType.ALL)
    private List<Funcionario> funcionarios = new ArrayList<>();

    @OneToMany(mappedBy = "dados", cascade = CascadeType.ALL)
    private List<Motorista> motoristas = new ArrayList<>();
}