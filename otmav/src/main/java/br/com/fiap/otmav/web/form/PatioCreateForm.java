package br.com.fiap.otmav.web.form;

import jakarta.validation.constraints.NotNull;

public class PatioCreateForm {

    @NotNull
    private Integer totalMotos;

    @NotNull
    private Integer capacidadeMoto;

    private Long filialId;
    private Long regiaoId;

    public PatioCreateForm() {}

    public PatioCreateForm(Integer totalMotos, Integer capacidadeMoto, Long filialId, Long regiaoId) {
        this.totalMotos = totalMotos;
        this.capacidadeMoto = capacidadeMoto;
        this.filialId = filialId;
        this.regiaoId = regiaoId;
    }

    public Integer getTotalMotos() {
        return totalMotos;
    }

    public void setTotalMotos(Integer totalMotos) {
        this.totalMotos = totalMotos;
    }

    public Integer getCapacidadeMoto() {
        return capacidadeMoto;
    }

    public void setCapacidadeMoto(Integer capacidadeMoto) {
        this.capacidadeMoto = capacidadeMoto;
    }

    public Long getFilialId() {
        return filialId;
    }

    public void setFilialId(Long filialId) {
        this.filialId = filialId;
    }

    public Long getRegiaoId() {
        return regiaoId;
    }

    public void setRegiaoId(Long regiaoId) {
        this.regiaoId = regiaoId;
    }
}
