package com.jeanlima.springrestapiapp.rest.controllers;

import com.jeanlima.springrestapiapp.converters.EstoqueConverter;
import com.jeanlima.springrestapiapp.model.Estoque;
import com.jeanlima.springrestapiapp.model.Produto;
import com.jeanlima.springrestapiapp.model.ProdutoComQuantidade;
import com.jeanlima.springrestapiapp.repository.EstoqueRepository;
import com.jeanlima.springrestapiapp.repository.ProdutoRepository;
import com.jeanlima.springrestapiapp.rest.dto.EstoqueDTO;
import com.jeanlima.springrestapiapp.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {
    @Autowired
    private EstoqueRepository repository;

    @Autowired
    private EstoqueService service;

    @PostMapping
    @ResponseStatus(CREATED)
    public Estoque save(@RequestBody Estoque estoque) {
        return service.salvar(estoque);
    }

    @GetMapping("{id}")
    public EstoqueDTO get(@PathVariable Integer id) {
        EstoqueConverter converter = new EstoqueConverter();
        return repository.findById(id)
                .map(converter::toEstoqueDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estoque não encontrado."));

    }

    @GetMapping("{id}/filter")
    public List<ProdutoComQuantidade> find(@PathVariable Integer id, @RequestParam String filtro ){
        return service.filterByDescricao(id, filtro);
    }


    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        repository
                .findById(id)
                .map(p -> {
                    repository.delete(p);
                    return Void.TYPE;
                }).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Estoque não encontrado."));
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public EstoqueDTO addProduto(@PathVariable Integer id, @RequestBody ProdutoComQuantidade produtoComQuantidade) {
        EstoqueConverter converter = new EstoqueConverter();
        return converter.toEstoqueDTO(service.addProduto(id, produtoComQuantidade));
    }

    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateQuantidadeNoEstoque( @PathVariable Integer id, @RequestParam Integer idProduto, @RequestParam Integer quantidade ){
        service.updateQuantidadeProduto(id, idProduto, quantidade);
    }



}
