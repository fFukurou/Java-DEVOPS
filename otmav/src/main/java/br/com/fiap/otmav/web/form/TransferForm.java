package br.com.fiap.otmav.web.form;

import jakarta.validation.constraints.NotNull;

public class TransferForm {

    @NotNull
    private Long motoId;

    @NotNull
    private Long setorId;

    public TransferForm() {}

    public Long getMotoId() {
        return motoId;
    }

    public void setMotoId(Long motoId) {
        this.motoId = motoId;
    }

    public Long getSetorId() {
        return setorId;
    }

    public void setSetorId(Long setorId) {
        this.setorId = setorId;
    }
}
