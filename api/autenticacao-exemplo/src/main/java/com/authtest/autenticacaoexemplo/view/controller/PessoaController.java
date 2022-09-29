package com.authtest.autenticacaoexemplo.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authtest.autenticacaoexemplo.services.PessoaService;
import com.authtest.autenticacaoexemplo.shared.PessoaDTO;
import com.authtest.autenticacaoexemplo.view.model.PessoaRequest;
import com.authtest.autenticacaoexemplo.view.model.PessoaResponse;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {
    
    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public ResponseEntity<List<PessoaResponse>> obterTodos()
    {
        List<PessoaDTO> pessoas = pessoaService.obterTodos();

        ModelMapper mapper = new ModelMapper();

        List<PessoaResponse> resposta = pessoas.stream()
        .map(pessoaDto -> mapper.map(pessoaDto, PessoaResponse.class))
        .collect(Collectors.toList());

        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PessoaResponse>> obterPorId(@PathVariable Integer id)
    {
        Optional<PessoaDTO> dto = pessoaService.obterPorId(id);

        PessoaResponse pessoa = new ModelMapper().map(dto.get(), PessoaResponse.class);

        return new ResponseEntity<>(Optional.of(pessoa), HttpStatus.OK);
    } 

    @PostMapping
    public ResponseEntity<PessoaResponse> adicionar(@RequestBody PessoaRequest pessoaReq)
    {
        ModelMapper mapper = new ModelMapper();

        PessoaDTO pessoaDto = mapper.map(pessoaReq, PessoaDTO.class);

        pessoaDto = pessoaService.adicionar(pessoaDto);

        return new ResponseEntity<>(mapper.map(pessoaDto, PessoaResponse.class), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) throws Exception
    {
        pessoaService.deletar(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaResponse> atualizar(@RequestBody PessoaRequest pessoaReq, @PathVariable Integer id)
    {
        ModelMapper mapper = new ModelMapper();

        PessoaDTO pessoaDto = mapper.map(pessoaReq, PessoaDTO.class);

        pessoaDto = pessoaService.atualizar(id, pessoaDto);

        return new ResponseEntity<>(mapper.map(pessoaDto, PessoaResponse.class), HttpStatus.OK);
    }

}
