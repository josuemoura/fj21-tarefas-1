package br.com.caelum.tarefas.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.caelum.tarefas.model.Tarefa;

@Repository
public class JdbcTarefaDao implements TarefaDAO {

	private Connection connection;
	
	@Autowired
	public JdbcTarefaDao(DataSource dataSource){
		try {
			this.connection = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	public void adiciona(Tarefa tarefa){
		String sql = "insert into tarefas " +
				"(descricao,finalizado)" +
				" values (?,?)";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, tarefa.getDescricao());
			stmt.setBoolean(2, tarefa.isFinalizado());
			
			stmt.execute();
			stmt.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Tarefa> lista(){
		List<Tarefa> tarefas = new ArrayList<Tarefa>();
		
		String sql = "select * from tarefas";
		
		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()){
				Tarefa tarefa = new Tarefa();
				tarefa.setId(rs.getLong("id"));
				tarefa.setDescricao(rs.getString("descricao"));
				tarefa.setFinalizado(rs.getBoolean("finalizado"));
				
				if (rs.getDate("dataFinalizacao") != null){
					Calendar dataFinalizacao = Calendar.getInstance();
					dataFinalizacao.setTime(rs.getDate("dataFinalizacao"));
					
					tarefa.setDataFinalizacao(dataFinalizacao);
				}
				
				tarefas.add(tarefa);
			}
			
			rs.close();
			stmt.close();
			
			return tarefas;
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	public void remove(Tarefa tarefa) {
		
		String sql = "delete from tarefas where id=?";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			stmt.setLong(1, tarefa.getId());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	public void remove(Long id) {
		String sql = "delete from tarefas where id=?";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			stmt.setLong(1, id);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		
	}

	public Tarefa buscaPorId(Long id) {
//		return tarefas.get(id.intValue());
		
		String sql = "select * from tarefas where id=?";
		
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			
			Tarefa tarefa = new Tarefa();
			while (rs.next()){
				tarefa.setId(rs.getLong("id"));
				tarefa.setDescricao(rs.getString("descricao"));
				tarefa.setFinalizado(rs.getBoolean("finalizado"));
				
				if (rs.getDate("dataFinalizacao") != null){
					Calendar dataFinalizacao = Calendar.getInstance();
					dataFinalizacao.setTime(rs.getDate("dataFinalizacao"));
					
					tarefa.setDataFinalizacao(dataFinalizacao);
				}
			}
			
			rs.close();
			stmt.close();
			
			return tarefa;
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	public void alterar(Tarefa tarefa) {
		String sql = "update tarefas set descricao=?, finalizado=?"; 
		
		if (tarefa.getDataFinalizacao() != null){
			sql = sql + ", dataFinalizacao=?";
		}
		
		sql = sql + " where id=?";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, tarefa.getDescricao());
			stmt.setBoolean(2, tarefa.isFinalizado());
			if (tarefa.getDataFinalizacao() != null){
				stmt.setDate(3, new Date(tarefa.getDataFinalizacao().getTimeInMillis()));
				stmt.setLong(4, tarefa.getId());
			} else{
				stmt.setLong(3, tarefa.getId());
			}
			
			stmt.execute();
			stmt.close();
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	public void finaliza(Long id) {
		String sql = "update tarefas set finalizado=?, dataFinalizacao=?" +
				"where id=?";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setBoolean(1, true);
			stmt.setDate(2, new Date(Calendar.getInstance().getTimeInMillis()));
			stmt.setLong(3, id);

			stmt.execute();
			stmt.close();
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
}
