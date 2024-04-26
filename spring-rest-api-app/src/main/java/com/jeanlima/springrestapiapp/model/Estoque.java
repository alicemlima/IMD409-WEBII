package com.jeanlima.springrestapiapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "estoque")
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Estoque {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL)
    private List<ProdutoComQuantidade> produtosQuantidade;

}
