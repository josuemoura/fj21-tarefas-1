package br.com.caelum.tarefas.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.tarefas.jpa.JpaTarefaDAO;
import br.com.caelum.tarefas.model.Tarefa;

@Transactional
@Controller
public class TarefasController {
	
//	private final JdbcTarefaDao dao;
	@Autowired
	JpaTarefaDAO dao;

	@RequestMapping("novaTarefa")
	public String form(){
		return "tarefa/formulario";
	}
	
	@RequestMapping("adicionaTarefa")
	public String adicionaTarefa(@Valid Tarefa tarefa, BindingResult result){
		
		if (result.hasFieldErrors("descricao")){
			System.out.println("Erro desc!");
			return "tarefa/formulario";
		}
		
		dao.adiciona(tarefa);
		System.out.println("Adicionado!!");
		return "tarefa/adicionada";
	}
	
	@RequestMapping("listaTarefas")
	public ModelAndView listaTarefa() {
		List<Tarefa> tarefas = dao.lista();
		ModelAndView mv = new ModelAndView("tarefa/lista");
		mv.addObject("tarefas", tarefas);
		return mv;
	}
	
	@RequestMapping("removeTarefa")
	public String removeTarefa(Tarefa tarefa){
		dao.remove(tarefa);
		System.out.println("Removido!!!");
		return "redirect:listaTarefas";
	}
	
	@RequestMapping("mostraTarefa")
	public String mostra(Long id, Model model) {
		model.addAttribute("tarefa", dao.buscaPorId(id));
		return "tarefa/mostra";
	}
	
	@RequestMapping("alteraTarefa")
	public String alterarTarefa(Tarefa tarefa){
		dao.alterar(tarefa);
		return "redirect:listaTarefas";
	}
	
	@RequestMapping("finalizaTarefa")
	public String finaliza(Long id, Model model) {
		dao.finaliza(id);
		model.addAttribute("tarefa", dao.buscaPorId(id));
		return "tarefa/finalizada";
	}
	
	@RequestMapping("removeTarefa2")
	public void remove(Long id, HttpServletResponse response) throws IOException{
//		dao.remove(id);
		System.out.println("Removido Ajax");
		response.setStatus(200);
	}
	
}
