package com.jeanlima.springrestapiapp.converters;

import com.jeanlima.springrestapiapp.model.Estoque;
import com.jeanlima.springrestapiapp.model.Produto;
import com.jeanlima.springrestapiapp.rest.dto.EstoqueDTO;
import com.jeanlima.springrestapiapp.rest.dto.ProdutoDTO;
import org.springframework.stereotype.Component;

@Component
public class EstoqueConverter {

    public EstoqueDTO toEstoqueDTO(Estoque estoque) {
        return EstoqueDTO
                .builder()
                .id(estoque.getId())
                .quantidadeTotal(estoque.getProdutos().size())
                .produtos(estoque.getProdutos().stream().map(this::toProdutoDTO).toList())
                .build();
    }

    public ProdutoDTO toProdutoDTO(Produto produto) {
        return ProdutoDTO
                .builder()
                .id(produto.getId())
                .descricao(produto.getDescricao())
                .preco(produto.getPreco())
                .quantidade(produto.getQuantidade())
                .build();
    }


}