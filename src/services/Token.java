package services;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;

public class Token {
	
	private static Token instance;
	public HashMap<Integer, String> usuariosLogados = new HashMap<>();
	
	public static Token getInstance() {
	    if (instance == null) {
	    	System.out.println("token - novo");
	        instance = new Token();
	    }
	    System.out.println("token - continua");
	    return instance;
	}
	
	public static String gerarToken() {
		 	String alfabeto = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		 	int tamanhoToken = 20;
	        Random random = new Random();
	        StringBuilder tkCriado = new StringBuilder(tamanhoToken);
	        for (int i = 0; i < tamanhoToken; i++) {
	            int index = random.nextInt(alfabeto.length());
	            char randomChar = alfabeto.charAt(index);
	            tkCriado.append(randomChar);
	        }
	        
	        return tkCriado.toString();
	}
	
	public void listarUsuariosLogados() {
		for (Map.Entry<Integer, String> entry : usuariosLogados.entrySet()) {
		    System.out.printf("Key: %d, Value: %s\n", entry.getKey(), entry.getValue());
		}
	}
	
	public void anexarUsuario(String novoToken, Integer idUser) {
		usuariosLogados.put(idUser, novoToken);
		System.out.println("Novo usuário logado");
	}
	
	public void desanexarUsuario(Integer idUser, String tokenUser) {
		boolean verificacao = procurarUsuario(idUser, tokenUser);
		if (verificacao) {
			System.out.println("Token encontrado");
			usuariosLogados.remove(idUser);
		}else {
			System.out.println("Algo deu errado!");
		}
	}
	
	public boolean procurarUsuario(Integer idUser, String tokenUser) {
		//listarUsuariosLogados();
		if (usuariosLogados.containsKey(idUser) && usuariosLogados.containsValue(tokenUser)) {
			System.out.println(idUser+" existe!");
			return true;	
		}else {
			System.out.println(idUser+" não existe!");
			return false;
		}
	}
}
