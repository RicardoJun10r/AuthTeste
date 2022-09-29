package com.authtest.autenticacaoexemplo.services;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.authtest.autenticacaoexemplo.data.DetalheUsuarioData;
import com.authtest.autenticacaoexemplo.model.Pessoa;
import com.authtest.autenticacaoexemplo.repository.PessoaRepository;
import com.authtest.autenticacaoexemplo.shared.PessoaDTO;

@Component
public class DetalheUsuarioServiceImpl implements UserDetailsService {

    private final PessoaRepository repository;

    public DetalheUsuarioServiceImpl(PessoaRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Pessoa> pessoa = repository.findByLogin(username);

        if(pessoa.isEmpty())
        {
            throw new UsernameNotFoundException("Usuário [" + username + "] não encontrado");
        }

        ModelMapper mapper = new ModelMapper();

        PessoaDTO pessoaDto = mapper.map(pessoa, PessoaDTO.class);

        return new DetalheUsuarioData(Optional.of(pessoaDto));
        
    }
}
