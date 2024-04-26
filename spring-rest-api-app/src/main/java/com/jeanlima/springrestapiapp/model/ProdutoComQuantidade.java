package com.jeanlima.springrestapiapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "produto_quantidade")
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoComQuantidade {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    @Getter
    @Setter
    private Produto produto;

    @Setter @Getter
    private Integer quantidade;

    public ProdutoComQuantidade(Produto produto, Integer quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }
}
