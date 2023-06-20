package screen;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import org.json.JSONObject;

import servidorECHO.ClienteECHO;
import services.CriptografiaSenha;
import services.ValidaDados;

public class TelaCadastro {
	private JFrame frmCadastroSmaicc;
	private JTextField textFieldNome;
	private JTextField textFieldEmail;
	private JTextField textFieldSenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadastro window = new TelaCadastro();
					window.frmCadastroSmaicc.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaCadastro() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCadastroSmaicc = new JFrame();
		frmCadastroSmaicc.getContentPane().setBackground(new Color(0, 0, 128));
		frmCadastroSmaicc.setTitle("Cadastro SMAICC");
		frmCadastroSmaicc.setBounds(100, 100, 483, 387);
		frmCadastroSmaicc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCadastroSmaicc.getContentPane().setLayout(null);
		
		JLabel lblTitulo = new JLabel("CADASTRO");
		lblTitulo.setForeground(new Color(100, 149, 237));
		lblTitulo.setFont(new Font("Verdana", Font.BOLD, 20));
		lblTitulo.setBounds(169, 25, 130, 26);
		frmCadastroSmaicc.getContentPane().add(lblTitulo);
		
		JLabel lblNome = new JLabel("Nome Completo:");
		lblNome.setForeground(new Color(240, 248, 255));
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNome.setBounds(109, 71, 130, 26);
		frmCadastroSmaicc.getContentPane().add(lblNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldNome.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textFieldNome.setBounds(109, 97, 250, 26);
		frmCadastroSmaicc.getContentPane().add(textFieldNome);
		textFieldNome.setColumns(10);
		
		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setForeground(new Color(240, 248, 255));
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEmail.setBounds(109, 126, 130, 26);
		frmCadastroSmaicc.getContentPane().add(lblEmail);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(109, 152, 250, 26);
		frmCadastroSmaicc.getContentPane().add(textFieldEmail);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setForeground(new Color(240, 248, 255));
		lblSenha.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSenha.setBounds(109, 179, 130, 26);
		frmCadastroSmaicc.getContentPane().add(lblSenha);
		
		textFieldSenha = new JTextField();
		textFieldSenha.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldSenha.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textFieldSenha.setColumns(10);
		textFieldSenha.setBounds(109, 205, 250, 26);
		frmCadastroSmaicc.getContentPane().add(textFieldSenha);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setForeground(Color.WHITE);
		btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCadastrar.setBackground(new Color(102, 153, 255));
		btnCadastrar.setBounds(173, 251, 122, 26);
		frmCadastroSmaicc.getContentPane().add(btnCadastrar);
		btnCadastrar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				JSONObject objetoJSON = new JSONObject();
				String nome = textFieldNome.getText();
				String email = textFieldEmail.getText();
				String senha = textFieldSenha.getText();
				
				if(ValidaDados.validarCadastro(nome, email, senha)) {
					try {		
						String novaSenha  = CriptografiaSenha.criptografar(senha);
						objetoJSON.put("nome", nome);
						objetoJSON.put("email", email);
						objetoJSON.put("senha", novaSenha);
						objetoJSON.put("operacao", 1);
						System.out.println("\tCADASTRO\nEnviando mensagem do CLIENTE: " + objetoJSON + "\n pela porta: " + TelaClientePorta.portaCliente +" utilizando o IP: "+TelaClientePorta.ipCliente);
						JSONObject respostaServidor = ClienteECHO.conectarServidor(objetoJSON);
						System.out.println("\tCADASTRO\nRecebendo mensagem do SERVIDOR: "+respostaServidor);
						
						String status = respostaServidor.getString("status");
						
						if(status.equals("OK")) {
							System.out.println("Tela Cadastro: "+respostaServidor);
							JOptionPane.showMessageDialog(frmCadastroSmaicc, "Usuário Cadastrado", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
							frmCadastroSmaicc.dispose();
						}else {
							JOptionPane.showMessageDialog(frmCadastroSmaicc, status, "Erro", JOptionPane.ERROR_MESSAGE);
						}
							
					}catch(Exception ex) {
						System.out.println("Erro:"+ex);
						JOptionPane.showMessageDialog(frmCadastroSmaicc, "Algo deu errado durante o cadastro!", "Erro", JOptionPane.ERROR_MESSAGE);
					}
				}else {
					if(ValidaDados.validarNome(nome)== false) {
						JOptionPane.showMessageDialog(frmCadastroSmaicc, "Nome inválido, utilize entre 3 e 50 caracteres.", "Erro", JOptionPane.ERROR_MESSAGE);
					}
					if(ValidaDados.validarEmail(email)== false) {
						JOptionPane.showMessageDialog(frmCadastroSmaicc, "E-mail inválido, preencha o campo corretamente", "Erro", JOptionPane.ERROR_MESSAGE);
					}
					if(ValidaDados.validarSenha(senha)==false) {
						JOptionPane.showMessageDialog(frmCadastroSmaicc, "Senha inválida, utilize entre 5 e 10 caracteres.", "Erro", JOptionPane.ERROR_MESSAGE);
					}
				}
				
				
				textFieldEmail.setText("");
				textFieldNome.setText("");
				textFieldSenha.setText("");
			}
		});
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setForeground(new Color(102, 153, 255));
		btnVoltar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnVoltar.setBackground(Color.WHITE);
		btnVoltar.setBounds(173, 287, 122, 26);
		frmCadastroSmaicc.getContentPane().add(btnVoltar);
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmCadastroSmaicc.dispose();
			}
		});
	}
	

	public JFrame getFrmCadastroSmaicc() {
		return frmCadastroSmaicc;
	}

	public JTextField getTextFieldNome() {
		return textFieldNome;
	}

	public JTextField getTextFieldEmail() {
		return textFieldEmail;
	}

	public JTextField getTextFieldSenha() {
		return textFieldSenha;
	}
}
