package com.jeanlima.springrestapiapp.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteComPedidosDTO {
    private Integer clienteId;
    private String clienteNome;

    private String clienteCpf;

    private List<InformacoesPedidoDTO> pedidos;
}
