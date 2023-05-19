package servidorECHO;

import java.net.*;
import java.io.*;
import org.json.*;

import screen.TelaClientePorta;

public class ClienteECHO {
	private static JSONObject mensagemConvertida;

    public static JSONObject conectarServidor(JSONObject mensagemCliente) throws IOException, JSONException {

        int portUser = TelaClientePorta.portaCliente;
      
        //Socket cliente = new Socket("localhost", portUser);
        //adicionar a opção de ip  -> Usando no pc do vini
        //Socket cliente = new Socket("10.20.8.11", portUser);
        Socket cliente = new Socket(TelaClientePorta.ipCliente, portUser);
        //System.out.println("\tENVIANDO DADOS DO CLIENTE");
        
        //Criando Buffer de entrada e saída
        BufferedReader bfDentro = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
        PrintWriter prFora = new PrintWriter(cliente.getOutputStream());
        
        String mensagemEnviada = mensagemCliente.toString();

        prFora.println(mensagemEnviada);
        prFora.flush();

        String mensagemRecebida = bfDentro.readLine();
        //System.out.println("Mensagem recebida do servidor: \n" + mensagemRecebida);
        
        mensagemConvertida = new JSONObject(mensagemRecebida);
        
        bfDentro.close();
        prFora.close();
        cliente.close();
        return mensagemConvertida;

    }
    
    public static JSONObject getMensagemFinal(){
        return mensagemConvertida;
    }

}
