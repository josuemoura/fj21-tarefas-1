package br.com.caelum.tarefas.dao;

import java.util.List;

import br.com.caelum.tarefas.model.Tarefa;

public interface TarefaDAO {

	Tarefa buscaPorId(Long id);
	List<Tarefa> lista();
	void adiciona(Tarefa t);
	void alterar(Tarefa t);
	void remove(Tarefa t);
	void finaliza(Long id);
	
}
