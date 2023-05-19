package screen;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;
import org.json.JSONObject;
import servidorECHO.ClienteECHO;
import services.ControleSessao;
import services.CriptografiaSenha;
import services.ValidaDados;


public class TelaLogin {
	private JFrame frmLoginSmaicc;
	private JTextField textFieldEmail;
	private JTextField textFieldSenha;
	//private TelaFeed feed = new TelaFeed();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLogin window = new TelaLogin();
					window.frmLoginSmaicc.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLoginSmaicc = new JFrame();
		frmLoginSmaicc.setTitle("Login SMAICC");
		frmLoginSmaicc.getContentPane().setBackground(new Color(0, 0, 128));
		frmLoginSmaicc.getContentPane().setLayout(null);
		frmLoginSmaicc.setBounds(100, 100, 528, 346);
		frmLoginSmaicc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel labelTitulo = new JLabel("SEU LOGIN");
		labelTitulo.setBounds(190, 31, 134, 21);
		labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitulo.setForeground(new Color(100, 149, 237));
		labelTitulo.setFont(new Font("Verdana", Font.BOLD, 20));
		frmLoginSmaicc.getContentPane().add(labelTitulo);
		
		JLabel labelEmail = new JLabel("E-mail:");
		labelEmail.setBounds(132, 74, 58, 13);
		labelEmail.setForeground(new Color(240, 248, 255));
		labelEmail.setFont(new Font("Tahoma", Font.BOLD, 14));
		frmLoginSmaicc.getContentPane().add(labelEmail);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setForeground(new Color(0, 0, 128));
		textFieldEmail.setBounds(132, 98, 249, 26);
		textFieldEmail.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		frmLoginSmaicc.getContentPane().add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		JLabel labelSenha = new JLabel("Senha:");
		labelSenha.setBounds(132, 134, 70, 13);
		labelSenha.setForeground(new Color(240, 248, 255));
		labelSenha.setFont(new Font("Tahoma", Font.BOLD, 14));
		frmLoginSmaicc.getContentPane().add(labelSenha);
		
		textFieldSenha = new JTextField();
		textFieldSenha.setForeground(new Color(0, 0, 128));
		textFieldSenha.setBounds(132, 158, 249, 26);
		textFieldSenha.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldSenha.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textFieldSenha.setColumns(10);
		frmLoginSmaicc.getContentPane().add(textFieldSenha);
		
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.setBounds(196, 204, 122, 26);
		btnEnviar.setForeground(Color.WHITE);
		btnEnviar.setBackground(new Color(102, 153, 255));
		btnEnviar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmLoginSmaicc.getContentPane().add(btnEnviar);
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JSONObject objetoJSON = new JSONObject();
				String email = textFieldEmail.getText();
				String senha = textFieldSenha.getText();
				if(ValidaDados.validarLogin(email, senha)) {
					String senhaCrip = CriptografiaSenha.criptografar(senha);
					try {
						objetoJSON.put("email", email);
						objetoJSON.put("senha", senhaCrip);
						objetoJSON.put("operacao", 2);
						
						System.out.println("\tLOGIN\nEnviando mensagem do CLIENTE: " + objetoJSON + "\n pela porta: " + TelaClientePorta.portaCliente +" utilizando o IP: "+TelaClientePorta.ipCliente);
						JSONObject respostaServidor = ClienteECHO.conectarServidor(objetoJSON);
						System.out.println("\tLOGIN\nRecebendo mensagem do SERVIDOR: "+respostaServidor);
						String status = respostaServidor.getString("status");
						int operacao = respostaServidor.getInt("operacao");
						String token = respostaServidor.getString("token");
						String nomeUser = respostaServidor.getString("nome");
						int idUsuario = respostaServidor.getInt("id");
						
						if(status.equals("OK")) {
							JOptionPane.showMessageDialog(frmLoginSmaicc, "Operação: "+operacao+"\nUsuário logado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
							ControleSessao.armazenarDadosSessao(token, nomeUser, idUsuario);
							textFieldEmail.setText("");
							textFieldSenha.setText("");
							TelaFeed.setState(true);
							frmLoginSmaicc.dispose();
						}else {
							JOptionPane.showMessageDialog(frmLoginSmaicc, "Algo deu errado durante o login!", "Erro", JOptionPane.ERROR_MESSAGE);
						}
						
					}catch(Exception ex) {
						System.out.println("Erro ao se conectar: "+ex);
					}
				}else {
					if(ValidaDados.validarEmail(email) == false) {
						JOptionPane.showMessageDialog(frmLoginSmaicc, "E-mail inválido, preencha o campo corretamente", "Erro", JOptionPane.ERROR_MESSAGE);
					}
					if(ValidaDados.validarSenha(senha) == false) {
						JOptionPane.showMessageDialog(frmLoginSmaicc, "Senha inválida, utilize entre 5 e 10 caracteres.", "Erro", JOptionPane.ERROR_MESSAGE);
					}
				}
				
				
			}
		});
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setBounds(195, 240, 123, 26);
		btnVoltar.setForeground(Color.WHITE);
		btnVoltar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnVoltar.setBackground(new Color(102, 153, 255));
		frmLoginSmaicc.getContentPane().add(btnVoltar);
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmLoginSmaicc.dispose();
			}
		});
	}

	public JFrame getFrmLoginSmaicc() {
		return frmLoginSmaicc;
	}

	public JTextField getTextFieldEmail() {
		return textFieldEmail;
	}

	public JTextField getTextFieldSenha() {
		return textFieldSenha;
	}
}
