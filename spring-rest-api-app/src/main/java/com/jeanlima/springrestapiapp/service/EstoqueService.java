package com.jeanlima.springrestapiapp.service;

import com.jeanlima.springrestapiapp.enums.StatusPedido;
import com.jeanlima.springrestapiapp.model.Estoque;
import com.jeanlima.springrestapiapp.model.Pedido;
import com.jeanlima.springrestapiapp.model.ProdutoComQuantidade;
import com.jeanlima.springrestapiapp.rest.dto.PedidoDTO;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;


public interface EstoqueService {
    @Transactional
    Estoque salvar(Estoque estoque);

    Estoque addProduto(Integer id, ProdutoComQuantidade produtoComQuantidade);

    Estoque updateQuantidadeProduto(Integer id, Integer idProduto, Integer quantidade);

    List<ProdutoComQuantidade> filterByDescricao(Integer id, String descricao);
}
