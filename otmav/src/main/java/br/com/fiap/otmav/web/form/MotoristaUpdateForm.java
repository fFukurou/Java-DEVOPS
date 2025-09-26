package br.com.fiap.otmav.web.form;

import br.com.fiap.otmav.domain.motorista.MotoristaPlano;
import br.com.fiap.otmav.domain.motorista.UpdateMotoristaDto;
import jakarta.validation.constraints.NotNull;

public class MotoristaUpdateForm {

    @NotNull
    private MotoristaPlano plano;

    private Long dadosId;

    public MotoristaUpdateForm() {}

    public MotoristaUpdateForm(MotoristaPlano plano, Long dadosId) {
        this.plano = plano;
        this.dadosId = dadosId;
    }

    public UpdateMotoristaDto toUpdateDto() {
        return new UpdateMotoristaDto(plano, dadosId);
    }

    public MotoristaPlano getPlano() {
        return plano;
    }

    public void setPlano(MotoristaPlano plano) {
        this.plano = plano;
    }

    public Long getDadosId() {
        return dadosId;
    }

    public void setDadosId(Long dadosId) {
        this.dadosId = dadosId;
    }
}
