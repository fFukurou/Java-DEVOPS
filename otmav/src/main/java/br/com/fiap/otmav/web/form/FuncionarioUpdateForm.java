package br.com.fiap.otmav.web.form;

import br.com.fiap.otmav.domain.funcionario.UpdateFuncionarioDto;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

public class FuncionarioUpdateForm {
    @NotBlank @Size(max = 100)
    private String cargo;
    private Long dadosId;
    private Long filialId;

    public FuncionarioUpdateForm() {}

    public FuncionarioUpdateForm(String cargo, Long dadosId, Long filialId) {
        this.cargo = cargo;
        this.dadosId = dadosId;
        this.filialId = filialId;
    }

    public FuncionarioUpdateForm(String cargo, Long filialId) {
        this.cargo = cargo;
        this.dadosId = null;
        this.filialId = filialId;
    }

    public UpdateFuncionarioDto toUpdateDto() {
        return new UpdateFuncionarioDto(cargo, dadosId, filialId);
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Long getDadosId() {
        return dadosId;
    }

    public void setDadosId(Long dadosId) {
        this.dadosId = dadosId;
    }

    public Long getFilialId() {
        return filialId;
    }

    public void setFilialId(Long filialId) {
        this.filialId = filialId;
    }
}
