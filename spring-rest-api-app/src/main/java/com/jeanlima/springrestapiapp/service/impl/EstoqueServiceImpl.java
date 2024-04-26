package com.jeanlima.springrestapiapp.service.impl;

import com.jeanlima.springrestapiapp.model.Estoque;
import com.jeanlima.springrestapiapp.model.Produto;
import com.jeanlima.springrestapiapp.model.ProdutoComQuantidade;
import com.jeanlima.springrestapiapp.repository.*;
import com.jeanlima.springrestapiapp.service.EstoqueService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstoqueServiceImpl implements EstoqueService {

    private final EstoqueRepository estoqueRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    @Transactional
    public Estoque salvar(Estoque estoque) {
        Estoque novoEstoque = new Estoque();
        List<Produto> produtos = estoque.getProdutosQuantidade()
                .stream().map(ProdutoComQuantidade::getProduto).toList();
        if (!produtos.isEmpty()) {
            produtos.forEach(produto -> {
                    if (produtoRepository.getProdutosByDescricao(produto.getDescricao()) == null){
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


}
