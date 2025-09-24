package br.com.fiap.otmav.domain.endereco;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "endereco")
@Getter
@Setter
@NoArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco")
    private Long id;

    // Optional fields — remove/replace with your real columns:
    @Column(name = "rua", length = 200)
    private String rua;

    @Column(name = "cidade", length = 100)
    private String cidade;
}