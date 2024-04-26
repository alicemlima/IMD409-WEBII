package com.jeanlima.springrestapiapp.service.impl;

import com.jeanlima.springrestapiapp.model.Estoque;
import com.jeanlima.springrestapiapp.model.Produto;
import com.jeanlima.springrestapiapp.model.ProdutoComQuantidade;
import com.jeanlima.springrestapiapp.repository.EstoqueRepository;
import com.jeanlima.springrestapiapp.repository.ProdutoQuantidadeRepository;
import com.jeanlima.springrestapiapp.repository.ProdutoRepository;
import com.jeanlima.springrestapiapp.service.EstoqueService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstoqueServiceImpl implements EstoqueService {

    private final EstoqueRepository estoqueRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoQuantidadeRepository produtoQuantidadeRepository;

    @Override
    @Transactional
    public Estoque salvar(Estoque estoque) {
        Estoque novoEstoque = new Estoque();
        List<Produto> produtos = estoque.getProdutosQuantidade()
                .stream().map(ProdutoComQuantidade::getProduto).toList();
        if (!produtos.isEmpty()) {
            produtos.forEach(produto -> {
                if (produtoRepository.getProdutosByDescricao(produto.getDescricao()) == null) {
                    Produto novoProduto = new Produto(produto.getDescricao(), produto.getPreco());
                    produtoRepository.save(novoProduto);
                }
            });
        }

        List<ProdutoComQuantidade> produtoComQuantidadesList = estoque.getProdutosQuantidade().stream().map(produtoComQuantidade -> {
            Produto produto = produtoRepository.getByDescricao(produtoComQuantidade.getProduto().getDescricao());
            return new ProdutoComQuantidade(produto, produtoComQuantidade.getQuantidade());
        }).toList();
        novoEstoque.setProdutosQuantidade(produtoComQuantidadesList);
        estoqueRepository.save(novoEstoque);
        return estoque;
    }

    @Override
    public Estoque addProduto(Integer id, ProdutoComQuantidade produtoComQuantidade) {
        Estoque estoque = estoqueRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Estoque não encontrado."));

        Produto produtoExistente = produtoRepository
                .getProdutosByDescricao(produtoComQuantidade.getProduto().getDescricao());

        if (produtoExistente != null) {
            ProdutoComQuantidade novoProdutoQuantidade = new ProdutoComQuantidade(produtoExistente, produtoComQuantidade.getQuantidade());
            estoque.getProdutosQuantidade().add(novoProdutoQuantidade);
            estoqueRepository.save(estoque);

        } else {
            Produto novo = new Produto(
                    produtoComQuantidade.getProduto().getDescricao(),
                    produtoComQuantidade.getProduto().getPreco());
            produtoRepository.save(novo);
            ProdutoComQuantidade novoProdutoQuantidade = new ProdutoComQuantidade(novo, produtoComQuantidade.getQuantidade());
            estoque.getProdutosQuantidade().add(novoProdutoQuantidade);
            estoqueRepository.save(estoque);
        }
        return estoque;
    }

    @Override
    public Estoque updateQuantidadeProduto(Integer id, Integer idProduto, Integer quantidade) {
        Estoque estoque = estoqueRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Estoque não encontrado."));

        Produto produto = produtoRepository.findById(idProduto).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));

        ProdutoComQuantidade produtoComQuantidade = estoque.getProdutosQuantidade().stream()
                .filter(pcq -> pcq.getProduto().equals(produto))
                .findFirst()
                .orElse(null);

        if (produtoComQuantidade != null) {
            produtoComQuantidade.setQuantidade(quantidade);
            produtoQuantidadeRepository.save(produtoComQuantidade);
        } else {
            ProdutoComQuantidade novoProdutoComQuantidade = new ProdutoComQuantidade(produto, quantidade);
            estoque.getProdutosQuantidade().add(novoProdutoComQuantidade);
            estoqueRepository.save(estoque);
            produtoQuantidadeRepository.save(novoProdutoComQuantidade);

        }
        return estoque;
    }

    @Override
    public List<ProdutoComQuantidade> filterByDescricao(Integer id, String descricao) {
        Estoque estoque = estoqueRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Estoque não encontrado."));

        return estoque.getProdutosQuantidade().stream()
                .filter(produtoComQuantidade ->
                        produtoComQuantidade.getProduto().getDescricao().toLowerCase().contains(descricao.toLowerCase()))
                .toList();
    }
}