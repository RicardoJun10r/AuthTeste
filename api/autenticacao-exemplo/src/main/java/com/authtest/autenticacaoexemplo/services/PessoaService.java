package com.authtest.autenticacaoexemplo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.el.ELException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.authtest.autenticacaoexemplo.model.Pessoa;
import com.authtest.autenticacaoexemplo.repository.PessoaRepository;
import com.authtest.autenticacaoexemplo.shared.PessoaDTO;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Optional<PessoaDTO> findByLogin(String login)
    {
        Optional<Pessoa> pessoa = pessoaRepository.findBy(Example.of(pessoa), login)
    }

    public List<PessoaDTO> obterTodos()
    {
        List<Pessoa> pessoas = pessoaRepository.findAll();

        return pessoas.stream().map(pessoa -> new ModelMapper().map(pessoa, PessoaDTO.class)).collect(Collectors.toList());
    }

    public Optional<PessoaDTO> obterPorId(Integer id)
    {

        Optional<Pessoa> pessoa = pessoaRepository.findById(id);

        if(pessoa.isEmpty())
        {
            throw new ELException();
        }

        PessoaDTO dto = new ModelMapper().map(pessoa.get(), PessoaDTO.class);
        
        return Optional.of(dto);
    }

    public PessoaDTO adicionar(PessoaDTO pessoaDto)
    {
        pessoaDto.setId(null);

        ModelMapper mapper = new ModelMapper();

        Pessoa pessoa = mapper.map(pessoaDto, Pessoa.class);

        pessoa = pessoaRepository.save(pessoa);

        pessoaDto.setId(pessoa.getId());

        return pessoaDto;
    }

    public void deletar(Integer id) throws Exception
    {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);

        if(pessoa.isEmpty())
        {
            throw new Exception("eeroe");
        }

        pessoaRepository.deleteById(id);
    }

    public PessoaDTO atualizar(Integer id, PessoaDTO pessoaDto)
    {
        pessoaDto.setId(id);

        ModelMapper mapper = new ModelMapper();

        Pessoa pessoa = mapper.map(pessoaDto, Pessoa.class);

        pessoaRepository.save(pessoa);

        return pessoaDto;
    }

}
