package services;

public class CriptografiaSenha {
	
	public static String criptografar(String senha) {
        String senhaCriptografada = ""; // string que vai armazenar a nova senha cifrada
        
        for (int i = 0; i < senha.length(); i++) {
            int ascii = (int) senha.charAt(i); // pega o código ASCII do caractere atual
            
            // cifra o caractere de acordo com a Cifra de César com duas casas à direita
            int novoAscii = (ascii + 2) % 256;
            
            senhaCriptografada += (char) novoAscii; // adiciona o caractere cifrado na nova senha
        }
        
        return senhaCriptografada;
    }
	
	public static String descriptografar(String senhaCriptografada) {
	    String senhaDescriptografada = ""; // string que vai armazenar a nova senha decifrada
	    
	    for (int i = 0; i < senhaCriptografada.length(); i++) {
	        int ascii = (int) senhaCriptografada.charAt(i); // pega o código ASCII do caractere atual
	        
	        // decifra o caractere de acordo com a Cifra de César com duas casas à esquerda
	        int novoAscii = (ascii - 2) % 256;
	        if (novoAscii < 0) {
	            novoAscii += 256;
	        }
	        
	        senhaDescriptografada += (char) novoAscii; // adiciona o caractere decifrado na nova senha
	    }
	    
	    return senhaDescriptografada;
	}
}
