package services;

public class ValidaDados {
	
	public static boolean validarNome(String name) { 
		if(name.isEmpty() || name.length()<3 || name.length()>50) {
			return false;
		}else {
			return true;
		}
	}
	public static boolean validarEmail(String email) { 
		if(email.isEmpty()||!email.contains("@")) {
			return false;
		}
		String[] emailPartes = email.split("@");
	    if (emailPartes.length != 2) {
	        return false;
	    }
	    if(emailPartes[0].length() < 3 && emailPartes[1].length() < 3) {
	    	return false;
	    }
	    if(emailPartes[0].length() > 50 && emailPartes[1].length() > 15) {
	    	return false;
	    }
	    return true;
	}
	public static boolean validarSenha(String senha) {
		if(senha.isEmpty() || senha.length()<5 || senha.length()>10) {
			return false;
		}
		return true;
	}
	
	public static boolean validarCadastro(String nome, String email, String senha) {
		boolean nomeValido = validarNome(nome);
		boolean emailValido = validarNome(email);
		boolean senhaValido = validarNome(senha);
		
		if(nomeValido == false || emailValido == false || senhaValido == false) {
			return false;
		}
		return true;
	}
	
	public static boolean validarLogin(String email, String senha) {
		boolean emailValido = validarNome(email);
		boolean senhaValido = validarNome(senha);
		
		if(emailValido == false || senhaValido == false) {
			return false;
		}
		return true;
	}
}
