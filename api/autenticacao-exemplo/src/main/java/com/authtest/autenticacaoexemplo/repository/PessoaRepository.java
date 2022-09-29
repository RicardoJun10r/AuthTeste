package com.authtest.autenticacaoexemplo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authtest.autenticacaoexemplo.model.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
    
    // public Optional<Pessoa> findByLogin(String login);
    
}
