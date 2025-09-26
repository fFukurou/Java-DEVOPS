package br.com.fiap.otmav.web.form;

import br.com.fiap.otmav.domain.motorista.MotoristaPlano;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MotoristaCreateForm {
    @NotNull(message = "Plano is required")
    private MotoristaPlano plano;

    @NotNull(message = "Dados (user) is required")
    private Long dadosId;
}
