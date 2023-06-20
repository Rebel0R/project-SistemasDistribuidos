package services;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import entity.Incidente;
import entity.Usuario;

public class ConexaoBancoSQL {
	 private Connection conexao;
	 //url -> jdbc:mysql://localhost/smaicc
	 //user -> root
	 //senha ->ju010203

	 public ConexaoBancoSQL(String url, String usuario, String senha) throws SQLException{
		conexao = DriverManager.getConnection(url, usuario, senha);
		System.out.println("SQL - Conexão com o banco realizada com sucesso!");
		
     }
	 
	 public Connection getConexao() {
	        return conexao;
	 }
	 
	 public void desconectar() {
	        if (conexao != null) {
	            try {
					conexao.close();
					System.out.println("SQL - Conexão com o banco encerrada!");
				} catch (SQLException e) {
					
					System.out.println("SQL - Erro ao encerrar conexão com o banco: "+e);
				}
	        }
	  }
	 
	 public void inserirUsuario (Usuario user) throws SQLException{
			PreparedStatement stmt = conexao.prepareStatement("INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)");
			stmt.setString(1, user.getNome());
			stmt.setString(2, user.getEmail());
			stmt.setString(3, user.getSenha());
			stmt.executeUpdate();
		    System.out.println("SQL - Usuário inserido com sucesso no banco!");
		
	 }
	 
	 public void alterarDadosUsuario (Usuario user) throws SQLException{
		 Usuario userBusca = buscarUsuarioId(user.getId());
		 if(userBusca.getNome() != user.getNome() || userBusca.getEmail() !=user.getEmail() || userBusca.getSenha() != user.getSenha()) {
			 PreparedStatement stmt = conexao.prepareStatement("UPDATE Usuario SET Nome = ?, Email = ?, Senha = ? WHERE ID = ?");
			 stmt.setString(1, user.getNome());
			 stmt.setString(2, user.getEmail());
			 stmt.setString(3, user.getSenha());
			 stmt.setInt(4, user.getId());

			 stmt.executeUpdate();
			 System.out.println("SQL - Alteração dos dados cadastrais realizadas com sucesso!");
		 }else {
			 System.out.println("SQL - Alteração dos dados não realizada pois os dados fornecidos não diferem dos dados do banco!");
		 }
		 
	 }
	 
	 public boolean excluirUsuario(int idUser, String senhaUser) throws SQLException {
		    PreparedStatement stmt = conexao.prepareStatement("DELETE FROM usuario WHERE id = ? AND senha = ?");
	        stmt.setInt(1, idUser);
	        stmt.setString(2, senhaUser);
	        int linhasAfetadas = stmt.executeUpdate();
	        return linhasAfetadas > 0;
	 }
	 
	 public Usuario buscarUsuarioLogin(String loginEmail, String loginSenha) throws SQLException {
		    PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM usuario WHERE email = ? AND senha = ?");
		    stmt.setString(1, loginEmail);
		    stmt.setString(2, loginSenha);
		    ResultSet rs = stmt.executeQuery();

		    if (rs.next()) {
		        System.out.println("SQL - Usuário encontrado\n");
		        Integer id = rs.getInt("id");
		        String nome = rs.getString("nome");
		        String email = rs.getString("email");
		        String senha = rs.getString("senha");
		        Usuario usuario = new Usuario(nome, email, senha);
		        usuario.setId(id);
		        return usuario;
		    }
		    return null;
		}

	 
	 public Usuario buscarUsuarioId(int idUser) throws SQLException {
	        PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM usuario WHERE id = ?");
	        stmt.setInt(1, idUser);
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	        	System.out.println("SQL - Usuário encontrado\n");
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
	 
	 public void inserirIncidente (Incidente incidente) throws SQLException{
			PreparedStatement stmt = conexao.prepareStatement("INSERT INTO Incidentes (data, hora, estado, cidade, bairro, rua, tipo_incidente, token, id_user) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, incidente.getData());
			stmt.setString(2, incidente.getHora());
			stmt.setString(3, incidente.getEstado());
			stmt.setString(4, incidente.getCidade());
			stmt.setString(5, incidente.getBairro());
			stmt.setString(6, incidente.getRua());
			stmt.setInt(7, incidente.getTipoIncidente());
			stmt.setString(8, incidente.getToken());
			stmt.setInt(9, incidente.getIdUsuario());
			stmt.executeUpdate();
		    System.out.println("SQL - Incidente inserido com sucesso no banco!");
		    
	 }
	 
	 public List<Incidente> buscarIncidentes (String data, String cidade, String uf) throws SQLException {
		 List<Incidente> incidentes = new ArrayList<>();
		 PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM incidentes WHERE data = ? AND cidade = ? AND estado = ?");
		 stmt.setString(1, data);
		 stmt.setString(2, cidade);
		 stmt.setString(3, uf);
		 
		 ResultSet rs = stmt.executeQuery();
		 
		 while (rs.next()) {
             int tipoIncidente = rs.getInt("tipo_incidente");
             String hora = rs.getString("hora");
             String rua = rs.getString("rua");
             String bairro = rs.getString("bairro");
             String token =  rs.getString("token");
             int idUser = rs.getInt("id_user");
             int idIncidente = rs.getInt("id");

             // Criando objeto incidente e adicionando na lista
             Incidente incidente = new Incidente(tipoIncidente, data, hora, cidade, bairro, rua, uf, token, idUser);
             incidente.setId(idIncidente);
             incidentes.add(incidente);
         }
		 
		 return incidentes;
	 }
	 
	 public List<Incidente> buscarIncidentesIdUser (int idUser) throws SQLException {
		 List<Incidente> incidentes = new ArrayList<>();
		 PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM incidentes WHERE id_user = ?");
		 stmt.setInt(1, idUser);
		 ResultSet rs = stmt.executeQuery();
		 while (rs.next()) {
             int tipoIncidente = rs.getInt("tipo_incidente");
             String hora = rs.getString("hora");
             String data = rs.getString("data");
             String cidade = rs.getString("cidade");
             String uf = rs.getString("estado");
             String rua = rs.getString("rua");
             String bairro = rs.getString("bairro");
             String token =  rs.getString("token");
             int idUserBanco = rs.getInt("id_user");
             int idIncidente = rs.getInt("id");

             // Criando objeto incidente e adicionando na lista
             Incidente incidente = new Incidente(tipoIncidente, data, hora, cidade, bairro, rua, uf, token, idUserBanco);
             incidente.setId(idIncidente);
             incidentes.add(incidente);
         }
		 
		 return incidentes;
	 }
	 
	 public boolean excluirIncidente(int idIncidente, int idUsuario) throws SQLException {
		    PreparedStatement stmt = conexao.prepareStatement("DELETE FROM incidentes WHERE id = ? AND id_user = ?");
	        stmt.setInt(1, idIncidente);
	        stmt.setInt(2, idUsuario);
	        int linhasAfetadas = stmt.executeUpdate();
	        return linhasAfetadas > 0;
	 }
	
}
