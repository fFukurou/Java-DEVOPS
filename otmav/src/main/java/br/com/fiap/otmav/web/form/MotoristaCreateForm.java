package br.com.fiap.otmav.web.form;

import br.com.fiap.otmav.domain.dados.CreateDadosDto;
import br.com.fiap.otmav.domain.motorista.MotoristaPlano;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MotoristaCreateForm {

    // DADOS
    @NotBlank @Size(min = 11, max = 11)
    private String cpf;

    @Size(max = 13)
    private String telefone;

    @Email @Size(max = 255)
    private String email;

    @Size(max = 200)
    private String senha;

    @NotBlank @Size(max = 150)
    private String nome;

//    MOTORISTA
    @NotNull
    private MotoristaPlano plano;

    public MotoristaCreateForm() {}

    public CreateDadosDto toCreateDadosDto() {
        return new CreateDadosDto(cpf, telefone, email, senha, nome);
    }


    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public MotoristaPlano getPlano() {
        return plano;
    }

    public void setPlano(MotoristaPlano plano) {
        this.plano = plano;
    }
}
