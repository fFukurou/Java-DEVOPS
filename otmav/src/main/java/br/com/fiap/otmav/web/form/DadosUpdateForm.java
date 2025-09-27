package br.com.fiap.otmav.web.form;

import br.com.fiap.otmav.domain.dados.UpdateDadosDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

/**
 * Used for the edit form. CPF is read-only on edit (DadosService.update doesn't change CPF).
 */
public class DadosUpdateForm {

    @Size(max = 13)
    private String telefone;

    @Email @Size(max = 255)
    private String email;

    @Size(max = 200)
    private String senha;

    @NotBlank @Size(max = 150)
    private String nome;

    public DadosUpdateForm() {}

    // constructor used in editForm
    public DadosUpdateForm(String telefone, String email, String nome) {
        this.telefone = telefone;
        this.email = email;
        this.nome = nome;
    }

    public UpdateDadosDto toUpdateDto() {
        return new UpdateDadosDto(telefone, email, senha, nome);
    }

    // getters/setters
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
