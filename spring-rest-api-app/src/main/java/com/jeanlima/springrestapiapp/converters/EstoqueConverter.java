package com.jeanlima.springrestapiapp.converters;

import com.jeanlima.springrestapiapp.model.Estoque;
import com.jeanlima.springrestapiapp.model.Produto;
import com.jeanlima.springrestapiapp.model.ProdutoComQuantidade;
import com.jeanlima.springrestapiapp.rest.dto.EstoqueDTO;
import com.jeanlima.springrestapiapp.rest.dto.ProdutoComQuantidadeDTO;
import com.jeanlima.springrestapiapp.rest.dto.ProdutoDTO;
import org.springframework.stereotype.Component;

@Component
public class EstoqueConverter {

    public EstoqueDTO toEstoqueDTO(Estoque estoque) {
        return EstoqueDTO
                .builder()
                .id(estoque.getId())
                .quantidadeTotal(estoque.getProdutosQuantidade().size())
                .produtosComQuantidae(estoque.getProdutosQuantidade().stream().map(this::toProdutoComQuantidadeDTO).toList())
                .build();
    }
    public ProdutoComQuantidadeDTO toProdutoComQuantidadeDTO(ProdutoComQuantidade produtoComQuantidade) {
        return ProdutoComQuantidadeDTO
                .builder()
                .produto(toProdutoDTO(produtoComQuantidade.getProduto()))
                .quantidade(produtoComQuantidade.getQuantidade())
                .build();
    }
    public ProdutoDTO toProdutoDTO(Produto produto) {
        return ProdutoDTO
                .builder()
                .id(produto.getId())
                .descricao(produto.getDescricao())
                .preco(produto.getPreco())
                .build();
    }


}