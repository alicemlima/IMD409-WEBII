package com.jeanlima.springrestapiapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeanlima.springrestapiapp.model.Produto;


public interface ProdutoRepository extends JpaRepository<Produto,Integer>{
    Produto getProdutosByDescricao (String descricao);

    Produto getByDescricao(String descricao);
}
