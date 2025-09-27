package br.com.fiap.otmav.web.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class RegiaoCreateForm {

    @Size(max = 4000)
    private String localizacao;

    @NotNull(message = "Área é obrigatória")
    @Positive(message = "Área deve ser positiva")
    private Double area;

    public RegiaoCreateForm() {}

    public RegiaoCreateForm(String localizacao, Double area) {
        this.localizacao = localizacao;
        this.area = area;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }
}
