package br.com.caelum.tarefas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.caelum.tarefas.jdbc.ConnectionFactory;
import br.com.caelum.tarefas.model.Usuario;

public class JdbcUsuarioDao {
	
	private Connection connection;
	
	public JdbcUsuarioDao(){
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public boolean existeUsuario(Usuario usuario){
		
		String sql = "select * from usuarios where login = ? and senha = ?";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, usuario.getLogin());
			stmt.setString(2, usuario.getSenha());
			
			ResultSet rs = stmt.executeQuery();
			
			boolean isValidUser = rs.next();
			
			rs.close();
			stmt.close();
			
			return isValidUser;
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

}
