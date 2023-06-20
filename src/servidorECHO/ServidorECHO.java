package servidorECHO;
import java.net.*;
import java.io.*;
import org.json.*;

import java.sql.SQLException;
import services.Token;
import services.ValidaDados;
import services.ConexaoBancoSQL;
import services.CriptografiaSenha;
import entity.Incidente;
import entity.Usuario;

import java.util.List;


public class ServidorECHO {
	
	 static ServerSocket server;
	 static Socket client;
	 private static TelaServidorPorta telaServidor = new TelaServidorPorta();
	 static InetAddress enderecoIPServidor;
	 public static int portaServidor = -1;
	 private static boolean statePortaServer = false;
	
	 public static void main(String[] args) throws IOException {
		 	telaServidor.getFrmSistemaServidor().setVisible(true);
		 	
		 	while(statePortaServer == false) {
		 		observerPort();
		 		System.out.println("Aguardando...");
		 	}
		    
		    try {	
		        // Cria o socket do servidor e o vincula à porta 20000
		    	/*while(portaServidor <=0) {
		    		//portaServidor = TelaServidorPorta.portaServer;
		    		//System.out.println("Aguardando...");
		    	}*/
		    	server = new ServerSocket(TelaServidorPorta.portaServer);
		    	enderecoIPServidor = InetAddress.getLocalHost();
		        System.out.println("\tSERVIDOR ABERTO NA PORTA " + TelaServidorPorta.portaServer + "...");
		        while (true) {
		            try {
		                // Tenta realizar conexão de um cliente
		                client = server.accept();
		                
		                System.out.println("Cliente conectado ao servidor através do IP: " + client.getInetAddress().getHostAddress());
		                //System.out.println("Cliente do endereço conectado: " + TelaClientePorta.ipCliente);
		                try {
		                    // Cria um buffer de entrada e de saída
		                    BufferedReader bfDentro = new BufferedReader(new InputStreamReader(client.getInputStream()));
		                    PrintWriter prFora = new PrintWriter(client.getOutputStream(), true);

		                    // Lê a mensagem do cliente e avalia a operação
		                    String mensagemRecebida = bfDentro.readLine();
		                    JSONObject mensagemConvertida = new JSONObject(mensagemRecebida);
		                    System.out.println("Mensagem do CLIENTE recebida: " + mensagemConvertida);

		                    int op = mensagemConvertida.getInt("operacao");
		                    System.out.println("Operacação recebida: " + op);
		                    telaServidor.trocaMensagens.setText("");
		                    telaServidor.trocaMensagens.setText("Mensagem do CLIENTE recebida: "+mensagemConvertida+"\n");
		                    ConexaoBancoSQL conexao;
		                    switch (op) {
		                        case 1:
		                        	telaServidor.trocaMensagens.append("Operação 1 - Cadastro\n");
		                            System.out.println("Cadastro em processo...");
		                            String nomeC = mensagemConvertida.getString("nome");
		                            String emailC = mensagemConvertida.getString("email");
		                            String senhaC = mensagemConvertida.getString("senha");
		                            String senhaDescC = CriptografiaSenha.descriptografar(senhaC);
		                            try {
		                            	conexao = new ConexaoBancoSQL("jdbc:mysql://localhost/smaicc", "root", "ju010203");
		                            	Usuario user;
			                            if (ValidaDados.validarCadastro(nomeC, emailC, senhaDescC)) {
			                            	user = new Usuario(nomeC, emailC, senhaC);
			                            	try {
				                            	conexao.inserirUsuario(user);
				                                JSONObject objJson = new JSONObject();
				                                objJson.put("operacao", op);
				                                objJson.put("status", "OK");
				                                System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
				                                telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson);
				                                prFora.println(objJson);
				                                System.out.println("Cadastro realizado com sucesso!");
			                            	}catch(SQLException e) {
			                            		System.out.println("Erro ao inserir usuário: "+user.toString()+"\n"+e);
			                            		JSONObject objJson = new JSONObject();
				                                objJson.put("operacao", op);
				                                objJson.put("status", "Erro Generico");
				                                System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
				                                telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson);
				                                prFora.println(objJson);
				                                System.out.println("Algo inesperado aconteceu ao se conectar com banco de dados");
			                            	}
			                            }else {
			                            	JSONObject objJson = new JSONObject();
			                                objJson.put("operacao", op);
			                                objJson.put("status", "Credenciais inválidas, preencha os campos novamente");
			                                System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
			                                telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson);
			                                prFora.println(objJson);
			                                System.out.println("Cadastro não realizado!");
			                            }
			                            conexao.desconectar();
		                            	
		                            }catch(SQLException e) {
		                            	System.out.println("Erro ao se conectar com o banco: "+e);
		                            	JSONObject objJson = new JSONObject();
		                                objJson.put("operacao", op);
		                                objJson.put("status", "Erro ao se conectar com o banco de dados");
		                                System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
		                                telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson);
		                                prFora.println(objJson);
		                                System.out.println("Cadastro não realizado!");
		                            }
		                            System.out.println("\n");
		                            break;
		                            
		                        case 2:
		                        	telaServidor.trocaMensagens.append("Operação 2 - Login\n");
		                            System.out.println("Login em processo...");
		                            String emailL = mensagemConvertida.getString("email");
		                            String senhaL = mensagemConvertida.getString("senha");		                            
		                            try {
		                            	conexao = new ConexaoBancoSQL("jdbc:mysql://localhost/smaicc", "root", "ju010203");
		                            	 if(ValidaDados.validarLogin(emailL, senhaL)) {
	                            		 	try {
	                            		 		Usuario userLogin = conexao.buscarUsuarioLogin(emailL, senhaL);
	                            		 		if(userLogin != null) {
	                            		 			JSONObject objJson = new JSONObject();
					                            	String novoToken = Token.gerarToken();				                            	
					                                objJson.put("operacao", op);
					                                objJson.put("status", "OK");
					                                objJson.put("token", novoToken);
					                                objJson.put("id", userLogin.getId());
					                                objJson.put("nome", userLogin.getNome());
					                                System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
					                                telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson+"\n");
					                                prFora.println(objJson);
					                                System.out.println(objJson+"\nLogin realizado com sucesso!");
					                                Token.getInstance().anexarUsuario(novoToken, userLogin.getId());
	                            		 		}else {
	                            		 			JSONObject objJson = new JSONObject();
					                                objJson.put("operacao", op);
					                                objJson.put("status", "Usuário não encontrado!");
					                                System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
					                                telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson+"\n");
					                                prFora.println(objJson);
					                                System.out.println("Login não realizado!");
	                            		 		}
	                            		 		
	                            		 	}catch(SQLException e) {
	                            		 		System.out.println("Erro ao encontrar usuário: "+e);
	                            		 		JSONObject objJson = new JSONObject();
				                                objJson.put("operacao", op);
				                                objJson.put("status", "Erro ao se conectar com o banco de dados");
				                                System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
				                                telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson+"\n");
				                                prFora.println(objJson);
				                                System.out.println("Login não realizado!");
	                            		 	}
				                         }else {
				                            	JSONObject objJson = new JSONObject();
				                                objJson.put("operacao", op);
				                                objJson.put("status", "Dados inválidos, preencha os campos corretamente");
				                                System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
				                                telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson+"\n");
				                                prFora.println(objJson);
				                                System.out.println("Login não realizado!");
				                         }
		                            	 conexao.desconectar();
		                            	
		                            }catch(SQLException e) {
		                            	System.out.println("Erro ao se conectar com o banco: "+e);
		                            	JSONObject objJson = new JSONObject();
		                                objJson.put("operacao", op);
		                                objJson.put("status", "Erro ao estabelecer conexão com o banco de dados");
		                                System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
		                                telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson+"\n");
		                                prFora.println(objJson);
		                                System.out.println("Login não realizado!");
		                            }
		                          System.out.println("\n");
		                          break;
	                          case 3:
	                        	    telaServidor.trocaMensagens.append("Operação 3 - Atualização Cadastral\n");
	                        	    System.out.println("Atualização Cadastral em processo...");
		                            String nomeAtt = mensagemConvertida.getString("nome");
		                            String emailAtt = mensagemConvertida.getString("email");
		                            String senhaAtt = mensagemConvertida.getString("senha");
		                            String tokenUserLogado = mensagemConvertida.getString("token");
		                            int idUserLogado = mensagemConvertida.getInt("id");
		                            String senhaDescAtt = CriptografiaSenha.descriptografar(senhaAtt);
		                            try {
		                            	if(Token.getInstance().procurarUsuario(idUserLogado, tokenUserLogado)) {
		                            		conexao = new ConexaoBancoSQL("jdbc:mysql://localhost/smaicc", "root", "ju010203");
			                            	Usuario user;
			                            	JSONObject objJson = new JSONObject();
				                            if (ValidaDados.validarCadastro(nomeAtt, emailAtt, senhaDescAtt)) {
				                            	user = new Usuario(nomeAtt, emailAtt, senhaAtt);
				                            	user.setId(idUserLogado);
				                            	try {				                 
			                            			conexao.alterarDadosUsuario(user);
			                            			Token.getInstance().desanexarUsuario(idUserLogado, tokenUserLogado);			                       							                  
					                                objJson.put("operacao", op);
					                                objJson.put("status", "OK");
					                                System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
					                                telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson+"\n");
					                                prFora.println(objJson);
					                                System.out.println("Atualização Cadastral realizada com sucesso!");						                                				                            						                               					                             	 
							                   
				                            	}catch(SQLException e) {
				                            		System.out.println("Erro ao atualizar os dados do usuário: "+user.toString()+"\n"+e);			           
					                                objJson.put("operacao", op);
					                                objJson.put("status", "Erro ao estabelecer conexão com o banco de dados");
					                                System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
					                                telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson+"\n");
					                                prFora.println(objJson);
					                                System.out.println("Atualização Cadastral não realizada!");
				                            	}
				                            }else {			                            	
				                                objJson.put("operacao", op);
				                                objJson.put("status", "Credenciais inválidas, preencha os campos corretamente");
				                                System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
				                                telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson+"\n");
				                                prFora.println(objJson);
				                                System.out.println("Atualização Cadastral não realizada!");
				                            }
				                            conexao.desconectar();
		                            	}else {
		                            		JSONObject objJson = new JSONObject();
			                                objJson.put("operacao", op);
			                                objJson.put("status", "Erro ao localizar usuário");
			                                System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
			                                telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson+"\n");
			                                prFora.println(objJson);
			                                System.out.println("Atualização Cadastral não realizada! Token não encontrado!");
		                            	}
		               			               	
		                            }catch(SQLException e) {
		                            	System.out.println("Erro ao se conectar com o banco: "+e);
		                            	JSONObject objJson = new JSONObject();
		                                objJson.put("operacao", op);
		                                objJson.put("status", "Erro ao estabelecer conexão com o banco de dados");
		                                System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
		                                telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson+"\n");
		                                prFora.println(objJson);
		                                System.out.println("Atualização Cadastral não realizada!");
		                            }
		                          System.out.println("\n");  
	                        	  break;
		                          
	                          case 4:
	                        	 telaServidor.trocaMensagens.append("Operação 4 - Listar Incidentes\n");
	                        	 System.out.println("Listando Incidente em processo...");
	                        	 String dataList = mensagemConvertida.getString("data");
	                        	 String cidadeList = mensagemConvertida.getString("cidade");
	                        	 String estadoList = mensagemConvertida.getString("estado");
	                        	
	                        	 try {
	                        		JSONObject objJson = new JSONObject();
	                            	conexao = new ConexaoBancoSQL("jdbc:mysql://localhost/smaicc", "root", "ju010203");
		                            if (ValidaDados.validarData(dataList) && ValidaDados.validarUF(estadoList) && ValidaDados.validarCidade(cidadeList)) {
		                            	 objJson.put("operacao", op);
			                             objJson.put("status", "OK");
		                            	 List<Incidente> listaIncidentes = conexao.buscarIncidentes(dataList, cidadeList, estadoList);
		                            	 if(listaIncidentes.isEmpty()) {
		                            		 JSONArray jsonArrayIncidentes = new JSONArray();
		                            		 objJson.put("incidentes", jsonArrayIncidentes);		          
		                            	 }else {
		                            		 JSONArray jsonArrayIncidentes = new JSONArray();
		                            		 for (Incidente incidente : listaIncidentes) {
		                            			    JSONObject jsonIncidente = new JSONObject();
		                            			    jsonIncidente.put("tipo_incidente", incidente.getTipoIncidente());
		                            			    jsonIncidente.put("data", incidente.getData());
		                            			    jsonIncidente.put("hora", incidente.getHora());
		                            			    jsonIncidente.put("cidade", incidente.getCidade());
		                            			    jsonIncidente.put("bairro", incidente.getBairro());
		                            			    jsonIncidente.put("rua", incidente.getRua());
		                            			    jsonIncidente.put("estado", incidente.getEstado());
		                            			    jsonIncidente.put("id_incidente", incidente.getId());
		                            			    
		                            			    jsonArrayIncidentes.put(jsonIncidente);
		                            			}
		                            		 objJson.put("incidentes", jsonArrayIncidentes);		                         
		                            	 }
		                            	 System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
		                            	 telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson+"\n");
		                            	 prFora.println(objJson);
		                            	
		                            }else {
		                                objJson.put("operacao", op);
		                                objJson.put("status", "Dados inválidos, preencha os campos novamente");
		                                System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
		                                telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson+"\n");
		                                prFora.println(objJson);
		                                System.out.println("Listagem de Incidentes não realizada!");
		                            }
		                            conexao.desconectar();
		                            	
		                         }catch(SQLException e) {
		                            	System.out.println("Erro ao se conectar com o banco: "+e);
		                            	JSONObject objJson = new JSONObject();
		                                objJson.put("operacao", op);
		                                objJson.put("status", "Erro ao estabelecer conexão com o banco de dados");
		                                System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
		                                telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson+"\n");
		                                prFora.println(objJson);
		                                System.out.println("Listagem de Incidente não realizada!");
		                            }
	                        	 System.out.println("\n");
	                        	 break;
	                          
	                          case 5:
	                        	 telaServidor.trocaMensagens.append("Operação 5 - Listar Seus Incidentes\n");
	                        	 System.out.println("Listando Seus Incidentes em processo...");
                        	     int idListUser = mensagemConvertida.getInt("id");
                        	     String tokenUser = mensagemConvertida.getString("token");
	                        	
	                        	 try {
	                        		JSONObject objJson = new JSONObject();
	                            	conexao = new ConexaoBancoSQL("jdbc:mysql://localhost/smaicc", "root", "ju010203");
		                            if (Token.getInstance().procurarUsuario(idListUser, tokenUser)) {
		                            	 objJson.put("operacao", op);
			                             objJson.put("status", "OK");
		                            	 List<Incidente> listaIncidentes = conexao.buscarIncidentesIdUser(idListUser);
		                            	 JSONArray jsonArrayIncidentes = new JSONArray();
		                            	 if(listaIncidentes.isEmpty()) {		                            		 
		                            		 objJson.put("incidentes", jsonArrayIncidentes);		          
		                            	 }else {                     
		                            		 for (Incidente incidente : listaIncidentes) {
		                            			    JSONObject jsonIncidente = new JSONObject();
		                            			    jsonIncidente.put("tipo_incidente", incidente.getTipoIncidente());
		                            			    jsonIncidente.put("data", incidente.getData());
		                            			    jsonIncidente.put("hora", incidente.getHora());
		                            			    jsonIncidente.put("cidade", incidente.getCidade());
		                            			    jsonIncidente.put("bairro", incidente.getBairro());
		                            			    jsonIncidente.put("rua", incidente.getRua());
		                            			    jsonIncidente.put("estado", incidente.getEstado());
		                            			    jsonIncidente.put("id_incidente", incidente.getId());
		                            			    
		                            			    jsonArrayIncidentes.put(jsonIncidente);
		                            			}
		                            		 objJson.put("incidentes", jsonArrayIncidentes);		                         
		                            	 }
		                            	 System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
		                            	 telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson+"\n");
		                            	 prFora.println(objJson);
		                            	
		                            }else {
		                                objJson.put("operacao", op);
		                                objJson.put("status", "Erro ao localizar token");
		                                System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
		                                telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson+"\n");
		                                prFora.println(objJson);
		                                System.out.println("Listagem de Incidentes não realizada!");
		                            }
		                            conexao.desconectar();
		                            	
		                         }catch(SQLException e) {
		                            	System.out.println("Erro ao se conectar com o banco: "+e);
		                            	JSONObject objJson = new JSONObject();
		                                objJson.put("operacao", op);
		                                objJson.put("status", "Erro ao estabelecer conexão com o banco de dados");
		                                System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
		                                telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson+"\n");
		                                prFora.println(objJson);
		                                System.out.println("Listagem de Incidente não realizada!");
		                            }
	                         System.out.println("\n");
                        	 break;
	                        
	                          case 6:
	                        	 telaServidor.trocaMensagens.append("Operação 6 - Remover Incidente\n");
	                        	 System.out.println("Removendo Incidente em processo...");
	                        	 int idUserRemove = mensagemConvertida.getInt("id");
	                        	 int idIncidenteRemove = mensagemConvertida.getInt("id_incidente");
                        	     String tokenUserRemove = mensagemConvertida.getString("token");
                        	     
                        	     try {
	                        		JSONObject objJson = new JSONObject();
	                            	conexao = new ConexaoBancoSQL("jdbc:mysql://localhost/smaicc", "root", "ju010203");
		                            if (Token.getInstance().procurarUsuario(idUserRemove, tokenUserRemove)) {
		                            	 objJson.put("operacao", op);
			                             boolean verificaExclusao = conexao.excluirIncidente(idIncidenteRemove, idUserRemove);
		                            	 if(verificaExclusao) {	
		                            		 System.out.println("Sucesso em Remover um Incidente!");
		                            		 objJson.put("status", "OK");      
		                            		 System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
		                            		 telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson+"\n");
			                            	 prFora.println(objJson);
		                            	 }else {                     
		                            		 objJson.put("operacao", op);
			                                 objJson.put("status", "Erro ao remover incidente");
			                                 System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
			                                 telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson+"\n");
			                                 prFora.println(objJson);
			                                 System.out.println("Remoção de Incidente não realizada!");			                            		 	                         
		                            	 }                         	 	                            
		                            }else {
		                                objJson.put("operacao", op);
		                                objJson.put("status", "Erro ao localizar token");
		                                System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
		                                telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson+"\n");
		                                prFora.println(objJson);
		                                System.out.println("Remoção de Incidente não realizada!");
		                            }
		                            conexao.desconectar();
			                            	
			                     }catch(SQLException e) {
		                            	System.out.println("Erro ao se conectar com o banco: "+e);
		                            	JSONObject objJson = new JSONObject();
		                                objJson.put("operacao", op);
		                                objJson.put("status", "Erro estabelecer conexão com o banco de dados");
		                                System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
		                                telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson+"\n");
		                                prFora.println(objJson);
		                                System.out.println("Remoção de Incidente não realizada!");
			                     }
	                        	 break;
		                          
		                      case 7:
		                    	  telaServidor.trocaMensagens.append("Operação 7 - Reportar Incidente\n");
		                    	  System.out.println("Reportando Incidente em processo...");
		                    	  String dataIn = mensagemConvertida.getString("data");
		                          String horaIn = mensagemConvertida.getString("hora");
		                          String estadoIn = mensagemConvertida.getString("estado");
		                          String cidadeIn = mensagemConvertida.getString("cidade");
		                          String bairroIn = mensagemConvertida.getString("bairro");
	                              String ruaIn = mensagemConvertida.getString("rua");
	                              String tokenIn = mensagemConvertida.getString("token");
	                              int idUser = mensagemConvertida.getInt("id");
	                              int tipoIncidenteIn = mensagemConvertida.getInt("tipo_incidente");
	                              
	                              try {
		                            	conexao = new ConexaoBancoSQL("jdbc:mysql://localhost/smaicc", "root", "ju010203");
		                            	Incidente incidente;
			                            if (ValidaDados.validarCadastroIncidente(cidadeIn, estadoIn, ruaIn, bairroIn, dataIn, horaIn, tipoIncidenteIn)) {
			                            	incidente = new Incidente(tipoIncidenteIn, dataIn, horaIn, cidadeIn, bairroIn, ruaIn, estadoIn, tokenIn, idUser);
			                            	try {
				                            	conexao.inserirIncidente(incidente);
				                                JSONObject objJson = new JSONObject();
				                                objJson.put("operacao", op);
				                                objJson.put("status", "OK");
				                                System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
				                                telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson+"\n");
				                                prFora.println(objJson);
				                                System.out.println("Cadastro de Incidente realizado com sucesso!");
			                            	}catch(SQLException e) {
			                            		System.out.println("Erro ao inserir incidente: "+incidente.toString()+"\n"+e);
			                            		JSONObject objJson = new JSONObject();
				                                objJson.put("operacao", op);
				                                objJson.put("status", "Erro ao reportar incidente");
				                                System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
				                                telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson+"\n");
				                                prFora.println(objJson);
				                                System.out.println("Cadastro de Incidente não realizado!");
			                            	}
			                            }else {
			                            	JSONObject objJson = new JSONObject();
			                                objJson.put("operacao", op);
			                                objJson.put("status", "Credenciais inválidas, preencha os campos novamente");
			                                System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
			                                telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson+"\n");
			                                prFora.println(objJson);
			                                System.out.println("Cadastro de Incidente não realizado!");
			                            }
			                            conexao.desconectar();
		                            	
		                            }catch(SQLException e) {
		                            	System.out.println("Erro ao se conectar com o banco: "+e);
		                            	JSONObject objJson = new JSONObject();
		                                objJson.put("operacao", op);
		                                objJson.put("status", "Erro ao estabelecer conexão com o banco de dados");
		                                System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
		                                telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson+"\n");
		                                prFora.println(objJson);
		                                System.out.println("Cadastro de Incidente não realizado!");
		                            }
	                              System.out.println("\n");
		                    	  break;
		                    	  
		                      case 8:
		                    	  telaServidor.trocaMensagens.append("Operação 8 - Excluir Cadastro\n");
		                    	  System.out.println("Excluíndo cadastro em processo...");
		                    	  String tokenExclui = mensagemConvertida.getString("token");
		                    	  int idExclui = mensagemConvertida.getInt("id");
		                    	  String senhaCripUser = mensagemConvertida.getString("senha");
		                    	  String senhaDescripUser = CriptografiaSenha.descriptografar(senhaCripUser);
		                    	  JSONObject objJsonExcluiCad = new JSONObject();
		                    	  if(Token.getInstance().procurarUsuario(idExclui, tokenExclui)) {
		                    		  if(ValidaDados.validarSenha(senhaDescripUser)) {
		                    			  try {		  	                
		  	                            	conexao = new ConexaoBancoSQL("jdbc:mysql://localhost/smaicc", "root", "ju010203");
		  	                            	boolean verificaExclusao = conexao.excluirUsuario(idExclui, senhaCripUser);
			                    			  if(verificaExclusao) {		                    			
			                    				  objJsonExcluiCad.put("operacao", op);
			                    				  objJsonExcluiCad.put("status", "OK");
		    		                    		  System.out.println("Mensagem do SERVIDOR enviada: "+objJsonExcluiCad);
		    		                    		  telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJsonExcluiCad+"\n");
		    		                    		  Token.getInstance().desanexarUsuario(idExclui, tokenExclui);
		    		                    		  prFora.println(objJsonExcluiCad);
		    		                              System.out.println(objJsonExcluiCad+"\nExclusão de Cadastro realizado com sucesso!");
			                    			  }else {
			                    				  objJsonExcluiCad.put("operacao", op);
			                    				  objJsonExcluiCad.put("status", "Exclusão de Cadastro não realizada, algo deu errado!");
					                    		  System.out.println("Mensagem do SERVIDOR enviada: "+objJsonExcluiCad);
					                    		  telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJsonExcluiCad+"\n");
					                    		  prFora.println(objJsonExcluiCad);
					                              System.out.println("Exclusão de Cadastro não realizada!"); 
			                    			  }    	
		  		                            conexao.desconectar();
		  			                            	
		  			                     }catch(SQLException e) {
		  		                            	System.out.println("Erro ao se conectar com o banco: "+e);
		  		                            	JSONObject objJson = new JSONObject();
		  		                                objJson.put("operacao", op);
		  		                                objJson.put("status", "Erro estabelecer conexão com o banco de dados");
		  		                                System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
		  		                                telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson+"\n");
		  		                                prFora.println(objJson);
		  		                                System.out.println("Remoção de Incidente não realizada!");
		  			                     }
		                    			  	    
		                    		  }else {
		                    			  objJsonExcluiCad.put("operacao", op);
		                    			  objJsonExcluiCad.put("status", "Senha incorreta, preencha corretamente");
			                    		  System.out.println("Mensagem do SERVIDOR enviada: "+objJsonExcluiCad);
			                    		  telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJsonExcluiCad+"\n");
			                    		  prFora.println(objJsonExcluiCad);
			                              System.out.println("Exclusão de Cadastro não realizada!");   
		                    		  }
		                    		  	  
		                    	  }else {
		                    		  objJsonExcluiCad.put("operacao", op);
		                    		  objJsonExcluiCad.put("status", "Erro ao localizar token");
		                    		  System.out.println("Mensagem do SERVIDOR enviada: "+objJsonExcluiCad);
		                    		  telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJsonExcluiCad+"\n");
		                    		  prFora.println(objJsonExcluiCad);
		                              System.out.println("Exclusão de Cadastro não realizado!"); 
		                    	  }
			                      System.out.println("\n");
		                    	  break;
		                    	  
		                      case 9:
		                    	  telaServidor.trocaMensagens.append("Operação 9 - Logout\n");
		                    	  System.out.println("Logout em processo...");
		                    	  String tokenLog = mensagemConvertida.getString("token");
		                    	  int idLog = mensagemConvertida.getInt("id");
		                    	  boolean verifica = Token.getInstance().procurarUsuario(idLog, tokenLog);
		                    	  JSONObject objJson = new JSONObject();
		                    	  if(verifica) {
		                    		  Token.getInstance().desanexarUsuario(idLog, tokenLog);
		                    		  objJson.put("operacao", op);
		                    		  objJson.put("status", "OK");
		                    		  System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
		                    		  telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson+"\n");
		                    		  prFora.println(objJson);
		                              System.out.println(objJson+"\nLogout realizado com sucesso!");
		                    		  
		                    	  }else {
		                    		  objJson.put("operacao", op);
		                    		  objJson.put("status", "Erro ao localizar token");
		                    		  System.out.println("Mensagem do SERVIDOR enviada: "+objJson);
		                    		  telaServidor.trocaMensagens.append("Mensagem do SERVIDOR enviada: "+objJson+"\n");
		                    		  prFora.println(objJson);
		                              System.out.println("Logout não realizado!"); 
		                    	  }
			                      System.out.println("\n");
			                      break;
		                        	
		                    }
		                    bfDentro.close();
		                    prFora.close();

		                } catch (IOException e) {
		                    System.out.println("Falha na conexão");
		                } finally {
		                    // Fecha a conexão com o cliente
		                    client.close();
		                }

		            } catch (IOException e) {
		                System.out.println("Erro! Conexão não aceita");
		            }
		        }
		    } catch (IOException e) {
		        System.out.println("Não foi possivel abrir a porta:" + portaServidor);
		        //e.printStackTrace();
		    } finally {
		        // Fecha o socket do servidor
		        server.close();
		    }
		}

     public static void observerPort() {
    	 if(portaServidor > 0) {
    		 statePortaServer = true;
    	 }
     }
}

    