package com.jeanlima.springrestapiapp.rest.controllers;

import com.jeanlima.springrestapiapp.converters.EstoqueConverter;
import com.jeanlima.springrestapiapp.model.Estoque;
import com.jeanlima.springrestapiapp.model.Produto;
import com.jeanlima.springrestapiapp.repository.EstoqueRepository;
import com.jeanlima.springrestapiapp.repository.ProdutoRepository;
import com.jeanlima.springrestapiapp.rest.dto.EstoqueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {
    @Autowired
    private EstoqueRepository repository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping
    @ResponseStatus(CREATED)
    public Estoque save(@RequestBody Estoque estoque) {
        return repository.save(estoque);
    }

    @GetMapping("{id}")
    public EstoqueDTO get(@PathVariable Integer id) {
        EstoqueConverter converter = new EstoqueConverter();
        return repository.findById(id)
                .map(converter::toEstoqueDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estoque não encontrado."));

    }

//    @GetMapping
//    public List<Produto> find(Produto filtro ){
//        ExampleMatcher matcher = ExampleMatcher
//                .matching()
//                .withIgnoreCase()
//                .withStringMatcher(
//                        ExampleMatcher.StringMatcher.CONTAINING );
//
//        Example example = Example.of(filtro, matcher);
//        return repository.findAll(example);
//    }


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
    public EstoqueDTO addProduto(@PathVariable Integer id, @RequestBody Produto produto) {
        EstoqueConverter converter = new EstoqueConverter();
        Estoque estoque = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Estoque não encontrado."));

        Produto produtoExistente = produtoRepository.getProdutosByDescricao(produto.getDescricao());
        if (produtoExistente != null) {
            estoque.getProdutos().add(produto);
            repository.save(estoque);

        } else {
            Produto novo = new Produto(produto.getDescricao(), produto.getPreco(), produto.getQuantidade());
            produtoRepository.save(novo);
            estoque.getProdutos().add(novo);
            repository.save(estoque);
        }

        return converter.toEstoqueDTO(estoque);
    }
//    @PatchMapping("{id}")
//    @ResponseStatus(NO_CONTENT)
//    public void updatePreco ( @PathVariable Integer id, @RequestParam BigDecimal preco ){
//        repository
//                .findById(id)
//                .map( p -> {
//                    p.setPreco(preco);
//                    repository.save(p);
//                    return p;
//                }).orElseThrow( () ->
//                        new ResponseStatusException(HttpStatus.NOT_FOUND,
//                                "Produto não encontrado."));
//    }

}
