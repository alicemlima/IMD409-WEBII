package com.jeanlima.springmvcdatajpaapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeanlima.springmvcdatajpaapp.model.Aluno;
import com.jeanlima.springmvcdatajpaapp.model.Curso;
import com.jeanlima.springmvcdatajpaapp.service.AlunoService;
import com.jeanlima.springmvcdatajpaapp.service.CursoService;
import com.jeanlima.springmvcdatajpaapp.service.MockDataService;




@Controller
@RequestMapping("/aluno")
public class AlunoController {


    @Autowired
    @Qualifier("alunoServiceImpl")
    AlunoService alunoService;

    @Autowired
    CursoService cursoService;


    @RequestMapping("/showForm")
    public String showFormAluno(Model model){

        model.addAttribute("aluno", new Aluno());
        model.addAttribute("cursos", cursoService.getCursos());
        return "aluno/formAluno";
    }

    @RequestMapping("/addAluno")
    public String showFormAluno(@ModelAttribute("aluno") Aluno aluno, Model model){


        Curso cursoSelecionado = cursoService.getCursoById(aluno.getCurso().getId());
        aluno.setCurso(cursoSelecionado);
        alunoService.salvarAluno(aluno);
        model.addAttribute("aluno", aluno);
        return "aluno/paginaAluno";
    }

    @RequestMapping("/getListaAlunos")
    public String showListaAluno(Model model){

        List<Aluno> alunos = alunoService.getListaAluno();
        model.addAttribute("alunos",alunos);
        return "aluno/listaAlunos";

    }

    
    
}