package services;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;

public class Token {
	
	private static Token instance;
	public HashMap<Integer, String> usuariosLogados = new HashMap<>();
	
	public static Token getInstance() {
	    if (instance == null) {
	    	System.out.println("Novo Token gerado");
	        instance = new Token();
	        return instance;
	    }
	    //System.out.println("Token em em execução");
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
		System.out.println("Novo login realizado no sistema!");
	}
	
	public void desanexarUsuario(Integer idUser, String tokenUser) {
		boolean verificacao = procurarUsuario(idUser, tokenUser);
		if (verificacao) {
			usuariosLogados.remove(idUser);
			System.out.println("Token encontrado! Login removido com sucesso!");
		}else {
			System.out.println("Algo deu errado!");
		}
	}
	
	public boolean procurarUsuario(Integer idUser, String tokenUser) {
		//listarUsuariosLogados();
		if (usuariosLogados.containsKey(idUser) && usuariosLogados.containsValue(tokenUser)) {
			System.out.println("Busca encontrada! ID:"+idUser+" existente na lista de logins!");
			return true;	
		}else {
			System.out.println("Falha! ID: "+idUser+" não existente na lista de logins!");
			return false;
		}
	}
}
