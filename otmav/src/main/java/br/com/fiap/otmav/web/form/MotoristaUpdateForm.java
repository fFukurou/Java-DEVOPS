package br.com.fiap.otmav.web.form;

import br.com.fiap.otmav.domain.motorista.MotoristaPlano;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MotoristaUpdateForm {
    // allow nulls if you want partial updates — validation enforced in controller if needed
    private MotoristaPlano plano;
    private Long dadosId;
}
