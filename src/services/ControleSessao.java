package services;

public class ControleSessao {
	
	public static String token;
	public static String nome;
	public static int id;
	
	public static void armazenarDadosSessao(String tokenLogin, String nomeLogin, int idLogin) {
		token = tokenLogin;
		nome = nomeLogin;
		id = idLogin;
		System.out.println("Dados Armazenados com sucesso no controle de sessão!");
	}
	
	public static int receberId() {
		return id;
	}
	public static String receberToken() {
		return token;
	}
	public static String receberNome() {
		return nome;
	}
	
	public static void limparDadosSessao() {
		token = "";
		nome = "";
		id = -1;
		System.out.println("Dados Apagados com sucesso no controle de sessão!");
	}
}
