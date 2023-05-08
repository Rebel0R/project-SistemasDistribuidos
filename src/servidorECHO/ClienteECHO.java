package servidorECHO;

import java.net.*;
import java.io.*;
import org.json.*;

import screen.TelaClientePorta;

public class ClienteECHO {
	private static JSONObject mensagemConvertida;

    public static JSONObject conectarServidor(JSONObject mensagemCliente) throws IOException, JSONException {

        int portUser = TelaClientePorta.portaCliente;
      
        Socket server = new Socket("localhost", portUser);
        System.out.println("\tCLIENTE");
        
        //Criando Buffer de entrada e saída
        BufferedReader bfDentro = new BufferedReader(new InputStreamReader(server.getInputStream()));
        PrintWriter prFora = new PrintWriter(server.getOutputStream());
        
        String mensagemEnviada = mensagemCliente.toString();

        prFora.println(mensagemEnviada);
        prFora.flush();

        String mensagemRecebida = bfDentro.readLine();
        System.out.println("Mensagem recebida: " + mensagemRecebida);
        
        mensagemConvertida = new JSONObject(mensagemRecebida);
        
        bfDentro.close();
        prFora.close();
        server.close();
        return mensagemConvertida;

    }
    
    public static JSONObject getMensagemFinal(){
        return mensagemConvertida;
    }

}
