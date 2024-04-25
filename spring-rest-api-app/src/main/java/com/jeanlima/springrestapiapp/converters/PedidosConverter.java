package com.jeanlima.springrestapiapp.converters;

import com.jeanlima.springrestapiapp.model.Cliente;
import com.jeanlima.springrestapiapp.model.ItemPedido;
import com.jeanlima.springrestapiapp.model.Pedido;
import com.jeanlima.springrestapiapp.rest.dto.ClienteComPedidosDTO;
import com.jeanlima.springrestapiapp.rest.dto.InformacaoItemPedidoDTO;
import com.jeanlima.springrestapiapp.rest.dto.InformacoesPedidoDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidosConverter {
    public InformacoesPedidoDTO toInformacoesPedidoDTO(Pedido pedido) {
        return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .items(convertToInformacaoItemPedidoDTO(pedido.getItens()))
                .build();
    }

    private List<InformacaoItemPedidoDTO> convertToInformacaoItemPedidoDTO(List<ItemPedido> itens) {
        if (CollectionUtils.isEmpty(itens)) {
            return Collections.emptyList();
        }
        return itens.stream().map(
                item -> InformacaoItemPedidoDTO
                        .builder()
                        .descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build()
        ).collect(Collectors.toList());
    }

    public ClienteComPedidosDTO toClienteComPedidosDTO(Cliente cliente) {
        return ClienteComPedidosDTO
                .builder()
                .clienteId(cliente.getId())
                .clienteNome(cliente.getNome())
                .clienteCpf(cliente.getCpf())
                .pedidos(cliente.getPedidos().stream().map(this::toInformacoesPedidoDTO).toList())
                .build();
    }

}
