package br.com.fiap.otmav.web.form;

import jakarta.validation.constraints.NotNull;

public class AssignForm {

    @NotNull
    private Long motoId;

    @NotNull
    private Long motoristaId;

    public AssignForm() {}

    public Long getMotoId() {
        return motoId;
    }

    public void setMotoId(Long motoId) {
        this.motoId = motoId;
    }

    public Long getMotoristaId() {
        return motoristaId;
    }

    public void setMotoristaId(Long motoristaId) {
        this.motoristaId = motoristaId;
    }
}
