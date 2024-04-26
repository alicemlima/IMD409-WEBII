package com.jeanlima.springrestapiapp.service;

import com.jeanlima.springrestapiapp.enums.StatusPedido;
import com.jeanlima.springrestapiapp.model.Estoque;
import com.jeanlima.springrestapiapp.model.Pedido;
import com.jeanlima.springrestapiapp.rest.dto.PedidoDTO;
import jakarta.transaction.Transactional;

import java.util.Optional;


public interface EstoqueService {
    @Transactional
    Estoque salvar(Estoque estoque);
}
