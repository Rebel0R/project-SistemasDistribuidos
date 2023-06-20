package services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

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
		if(senha.isEmpty() || senha.length()<5 || senha.length()>10 || !senha.matches("[a-zA-Z0-9]+")) {
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
	
	public static boolean validarStringCedilhaAcentos(String str) {
		if(str.isEmpty() || str.matches(".*[çÇ].*") || str.equals(str.toLowerCase()) || str.matches(".*[áàãâéèêíïóôõöúü].*") || str.matches(".*[ÁÀÃÂÉÈÊÍÏÓÔÕÖÚÜ].*")) {
			return false;
		}
		return true;
	}
	
	public static boolean validarRua(String rua) {
		//boolean validaRua = validarStringCedilhaAcentos(rua);
		if(rua.length()>50 || rua.equals(rua.toLowerCase())|| rua.isEmpty() || rua.matches(".*[çÇ].*") || rua.matches(".*[áàãâéèêíïóôõöúü].*") || rua.matches(".*[ÁÀÃÂÉÈÊÍÏÓÔÕÖÚÜ].*")) {
			return false;
		}
		
		return true;
	}
	
	public static boolean validarBairro(String bairro) {
		//boolean validaBairro = validarStringCedilhaAcentos(bairro);
		if(bairro.length()>50 || bairro.equals(bairro.toLowerCase()) || bairro.isEmpty() || bairro.matches(".*[çÇ].*") || bairro.matches(".*[áàãâéèêíïóôõöúü].*") || bairro.matches(".*[ÁÀÃÂÉÈÊÍÏÓÔÕÖÚÜ].*") ) {
			return false;
		}
		
		return true;
	}
	
	public static boolean validarCidade(String cidade) {
		//boolean validaCidade = validarStringCedilhaAcentos(cidade);
		if(cidade.length()>50 || cidade.equals(cidade.toLowerCase()) || cidade.isEmpty() || cidade.matches(".*[çÇ].*") || cidade.matches(".*[áàãâéèêíïóôõöúü].*") || cidade.matches(".*[ÁÀÃÂÉÈÊÍÏÓÔÕÖÚÜ].*")) {
			return false;
		}
		
		return true;
	}
	
	public static boolean validarUF(String estado) {
		if(estado.length()!=2 || estado.equals(estado.toLowerCase()) || estado.equals("Estados")) {
			return false;
		}
		return true;
	}
	public static boolean validarData(String data) {
		//formato da data = yyyy-mm-dd
		try {
		    LocalDate dataFormatada = LocalDate.parse(data);
		
		    if (dataFormatada != null) {
		       return true;
		    } else {
		       return false;
		    }
		} catch (DateTimeParseException e) {
		    System.out.println("Data inválida: " + data);
		    return false;
		}
	}
	
	public static boolean validarHora(String hora) {
		try {
		    LocalTime horaFormatada = LocalTime.parse(hora);

		    // Verificar se o horário é válido
		    if (horaFormatada != null) {
		        return true;
		    } else {
		        return false;
		    }
		} catch (DateTimeParseException e) {
		    System.out.println("Horário inválido: " + hora);
		    return false;
		}
	}
	
	public static boolean validarIncidente(int incidente) {
		if(incidente < 0 || incidente > 10) {
			return false;
		}
		return true;
	}
	
	public static boolean validarCadastroIncidente(String cidade, String estado, String rua, String bairro, String data, String hora, int incidente) {
		if(!validarCidade(cidade) || !validarUF(estado)|| !validarRua(rua) || !validarBairro(bairro) || !validarData(data) || !validarHora(hora) || !validarIncidente(incidente)) {
			return false;
		}
		return true;
	}
}
