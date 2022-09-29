package com.authtest.autenticacaoexemplo.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.authtest.autenticacaoexemplo.shared.PessoaDTO;

public class DetalheUsuarioData implements UserDetails {


    private final Optional<PessoaDTO> pessoa;

    public DetalheUsuarioData(Optional<PessoaDTO> pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return pessoa.orElse(new PessoaDTO()).getSenha();
    }

    @Override
    public String getUsername() {
        return pessoa.orElse(new PessoaDTO()).getEmail();
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
