package servidorECHO;
import java.net.*;
import java.io.*;
import org.json.*;

import java.sql.SQLException;
import services.Token;
import services.ValidaDados;
import services.ConexaoBancoSQL;
import services.CriptografiaSenha;
import entity.Usuario;
//import screen.TelaFeed;

import java.util.Scanner;

public class ServidorECHO {
	
	 static ServerSocket server;
	
	 public static void main(String[] args) throws IOException {
		    int portaServidor = -1;
		    Scanner scanner = new Scanner(System.in);
		    while (portaServidor < 0) {
		        System.out.println("Informe a porta em que deseja acessar: (20000-25000)");
		        portaServidor = scanner.nextInt();
		        if (portaServidor < 20000 || portaServidor > 25000) {
		            portaServidor = -1;
		        }
		    }
		    scanner.close();

		    try {
		        // Cria o socket do servidor e o vincula � porta 20000
		        server = new ServerSocket(portaServidor);
		        System.out.println("Servidor iniciado na porta " + portaServidor + "...");
		        while (true) {
		            try {
		                // Tenta realizar conex�o de um cliente
		                Socket client = server.accept();
		                System.out.println("Cliente do endere�o conectado: " + client.getInetAddress().getHostAddress());
		                try {
		                    // Cria um buffer de entrada e de sa�da
		                    BufferedReader bfDentro = new BufferedReader(new InputStreamReader(client.getInputStream()));
		                    PrintWriter prFora = new PrintWriter(client.getOutputStream(), true);

		                    // L� a mensagem do cliente e avalia a opera��o
		                    String mensagemRecebida = bfDentro.readLine();
		                    JSONObject mensagemConvertida = new JSONObject(mensagemRecebida);
		                    System.out.println("\tSERVIDOR");
		                    System.out.println("Mensagem recebida: " + mensagemConvertida);

		                    int op = mensagemConvertida.getInt("operacao");
		                    System.out.println("Operaca��o recebida: " + op);
		                    ConexaoBancoSQL conexao;
		                    switch (op) {
		                        case 1:
		                        
		                            System.out.println("Cadastro em processo...");
		                            String nomeC = mensagemConvertida.getString("nome");
		                            String emailC = mensagemConvertida.getString("email");
		                            String senhaC = mensagemConvertida.getString("senha");
		                            String senhaDescC = CriptografiaSenha.descriptografar(senhaC);
		                            try {
		                            	conexao = new ConexaoBancoSQL("jdbc:mysql://localhost/smaicc", "root", "ju010203");
		                            	Usuario user;
			                            if (ValidaDados.validarCadastro(nomeC, emailC, senhaDescC)) {
			                            	user = new Usuario(nomeC, emailC, senhaDescC);
			                            	try {
				                            	conexao.inserirUsuario(user);
				                                JSONObject objJson = new JSONObject();
				                                objJson.put("operacao", op);
				                                objJson.put("status", "OK");
				                                prFora.println(objJson);
				                                System.out.println("Cadastro realizado com sucesso!");
			                            	}catch(SQLException e) {
			                            		System.out.println("Erro ao inserir usu�rio: "+user.toString()+"\n"+e);
			                            		JSONObject objJson = new JSONObject();
				                                objJson.put("operacao", op);
				                                objJson.put("status", "Erro Generico");
				                                prFora.println(objJson);
				                                System.out.println("Cadastro n�o realizado!");
			                            	}
			                            }else {
			                            	JSONObject objJson = new JSONObject();
			                                objJson.put("operacao", op);
			                                objJson.put("status", "Erro Generico");
			                                prFora.println(objJson);
			                                System.out.println("Cadastro n�o realizado!");
			                            }
			                            conexao.desconectar();
		                            	
		                            }catch(SQLException e) {
		                            	System.out.println("Erro ao se conectar com o banco: "+e);
		                            	JSONObject objJson = new JSONObject();
		                                objJson.put("operacao", op);
		                                objJson.put("status", "Erro Generico");
		                                prFora.println(objJson);
		                                System.out.println("Cadastro n�o realizado!");
		                            }
		                            
		                            break;
		                        case 2:
		                            System.out.println("Login em processo...");
		                            String emailL = mensagemConvertida.getString("email");
		                            String senhaL = mensagemConvertida.getString("senha");
		                            String senhaDescL = CriptografiaSenha.descriptografar(senhaL);
		                            try {
		                            	conexao = new ConexaoBancoSQL("jdbc:mysql://localhost/smaicc", "root", "ju010203");
		                            	 if(ValidaDados.validarLogin(emailL, senhaDescL)) {
	                            		 	try {
	                            		 		Usuario userLogin = conexao.buscarUsuario(emailL);
	                            		 		JSONObject objJson = new JSONObject();
				                            	String novoToken = Token.gerarToken();				                            	
				                                objJson.put("operacao", op);
				                                objJson.put("status", "OK");
				                                objJson.put("token", novoToken);
				                                objJson.put("id", userLogin.getId());
				                                objJson.put("nome", userLogin.getNome());
				                                prFora.println(objJson);
				                                System.out.println(objJson+"\nLogin realizado com sucesso!");
				                                Token.getInstance().anexarUsuario(novoToken, userLogin.getId());
	                            		 	}catch(SQLException e) {
	                            		 		System.out.println("Erro ao encontrar usu�rio: "+e);
	                            		 	}
				                         }else {
				                            	JSONObject objJson = new JSONObject();
				                                objJson.put("operacao", op);
				                                objJson.put("status", "Erro Generico");
				                                prFora.println(objJson);
				                                System.out.println("Login n�o realizado!");
				                         }
		                            	 conexao.desconectar();
		                            	
		                            }catch(SQLException e) {
		                            	System.out.println("Erro ao se conectar com o banco: "+e);
		                            	JSONObject objJson = new JSONObject();
		                                objJson.put("operacao", op);
		                                objJson.put("status", "Erro Generico");
		                                prFora.println(objJson);
		                                System.out.println("Login n�o realizado!");
		                            }
		                           
		                            break;
		                      case 9:
		                    	  System.out.println("Logout em processo...");
		                    	  String tokenLog = mensagemConvertida.getString("token");
		                    	  int idLog = mensagemConvertida.getInt("id");
		                    	  boolean verifica = Token.getInstance().procurarUsuario(idLog, tokenLog);
		                    	  JSONObject objJson = new JSONObject();
		                    	  if(verifica) {
		                    		  Token.getInstance().desanexarUsuario(idLog, tokenLog);
		                    		  objJson.put("operacao", op);
		                    		  objJson.put("status", "OK");
		                    		  prFora.println(objJson);
		                              System.out.println(objJson+"\nLogout realizado com sucesso!");
		                    		  
		                    	  }else {
		                    		  objJson.put("operacao", op);
		                    		  objJson.put("status", "Erro Generico");
		                    		  prFora.println(objJson);
		                              System.out.println("Logout n�o realizado!"); 
		                    	  }
		                        	
		                    }
		                    bfDentro.close();
		                    prFora.close();

		                } catch (IOException e) {
		                    System.out.println("Falha na conex�o");
		                } finally {
		                    // Fecha a conex�o com o cliente
		                    client.close();
		                }

		            } catch (IOException e) {
		                System.out.println("Erro! Conex�o n�o aceita");
		            }
		        }
		    } catch (IOException e) {
		        System.out.println("N�o foi possivel abrir a porta:" + portaServidor);
		        //e.printStackTrace();
		    } finally {
		        // Fecha o socket do servidor
		        server.close();
		    }
		}
}
