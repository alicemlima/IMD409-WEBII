package com.jeanlima.springrestapiapp.repository;

import com.jeanlima.springrestapiapp.model.Produto;
import com.jeanlima.springrestapiapp.model.ProdutoComQuantidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProdutoQuantidadeRepository extends JpaRepository<ProdutoComQuantidade,Integer>{
    ProdutoComQuantidade getByProdutoIs(Produto produto);

    List<ProdutoComQuantidade> findAllByProdutoDescricao(String descricao);
}
