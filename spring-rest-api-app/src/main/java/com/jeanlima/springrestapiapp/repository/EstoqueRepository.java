package com.jeanlima.springrestapiapp.repository;

import com.jeanlima.springrestapiapp.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Integer> {
}
