package br.com.fiap.otmav.infra.security;

import br.com.fiap.otmav.domain.dados.Dados;
import br.com.fiap.otmav.domain.funcionario.Funcionario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class FuncionarioUserDetails implements UserDetails {

    private final String username; // email from Dados
    private final Funcionario funcionario; // keep the whole object
    private final String password; // hashed password from Dados
    private final List<GrantedAuthority> authorities;

    public FuncionarioUserDetails(Funcionario funcionario) {
        this.funcionario = funcionario;
        Dados dados = funcionario.getDados();

        this.username = dados.getEmail();   // copy primitive value
        this.password = dados.getSenha();   // copy primitive value

        // You can expand this later if Funcionario has roles field
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
