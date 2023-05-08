package services;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Usuario;

public class ConexaoBancoSQL {
	 private Connection conexao;
	 //url -> jdbc:mysql://localhost/smaicc
	 //user -> root
	 //senha ->ju010203

	 public ConexaoBancoSQL(String url, String usuario, String senha) throws SQLException{
		conexao = DriverManager.getConnection(url, usuario, senha);
		System.out.println("Conexão realizada com sucesso!");
		
     }
	 
	 public Connection getConexao() {
	        return conexao;
	 }
	 
	 public void desconectar() {
	        if (conexao != null) {
	            try {
					conexao.close();
					System.out.println("Conexão encerrada!");
				} catch (SQLException e) {
					
					System.out.println("Erro ao encerrar conexão com o banco: "+e);
				}
	        }
	  }
	 
	 public void inserirUsuario (Usuario user) throws SQLException{
			PreparedStatement stmt = conexao.prepareStatement("INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)");
			stmt.setString(1, user.getNome());
			stmt.setString(2, user.getEmail());
			stmt.setString(3, user.getSenha());
			stmt.executeUpdate();
		    System.out.println("Usuário inserido com sucesso!");
		
	 }
	 
	 public Usuario buscarUsuario(String loginEmail) throws SQLException {
	        String sql = "SELECT * FROM usuario WHERE email = ?";
	        PreparedStatement stmt = conexao.prepareStatement(sql);
	        stmt.setString(1, loginEmail);
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	        	System.out.println("Usuário encontrado\n");
	            Integer id = rs.getInt("id");
	            String nome = rs.getString("nome");
	            String email  = rs.getString("email");
	            String senha = rs.getString("senha");
	            Usuario usuario = new Usuario(nome, email, senha);
	            usuario.setId(id);
	            return usuario;
	        }
	        return null;
	    }
	
}
