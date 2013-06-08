package br.com.caelum.tarefas.jpa;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.caelum.tarefas.dao.TarefaDAO;
import br.com.caelum.tarefas.model.Tarefa;

@Repository
public class JpaTarefaDAO implements TarefaDAO{
	
	@PersistenceContext
	private EntityManager manager;
	
	public void adiciona(Tarefa tarefa){
		manager.persist(tarefa);
	}
	
	public void alterar(Tarefa tarefa){
		manager.merge(tarefa);
	}
	
	public List<Tarefa> lista(){
		return manager.createQuery("select t from Tarefa t").getResultList();
	}
	
	public Tarefa buscaPorId(Long id){
		return manager.find(Tarefa.class, id);
	}
	
	public void remove(Tarefa tarefa){
		Tarefa tarefaARemover = buscaPorId(tarefa.getId());
		manager.remove(tarefaARemover);
	}
	
	public void finaliza(Long id){
		Tarefa tarefa = buscaPorId(id);
		tarefa.setFinalizado(true);
		tarefa.setDataFinalizacao(Calendar.getInstance());
	}

}
