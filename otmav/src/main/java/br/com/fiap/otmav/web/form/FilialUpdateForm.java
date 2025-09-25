package br.com.fiap.otmav.web.form;

import jakarta.validation.constraints.Size;

public class FilialUpdateForm {

    @Size(max = 150)
    private String nome;

    private Long enderecoId;

    public FilialUpdateForm() {}

    public FilialUpdateForm(String nome, Long enderecoId) {
        this.nome = nome;
        this.enderecoId = enderecoId;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Long getEnderecoId() { return enderecoId; }
    public void setEnderecoId(Long enderecoId) { this.enderecoId = enderecoId; }
}
