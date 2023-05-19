package screen;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.json.JSONObject;

import services.ControleSessao;
import services.CriptografiaSenha;
import services.ValidaDados;
import servidorECHO.ClienteECHO;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaAlterarDados {

	private JFrame frmAtualizaoCadastral;
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
					TelaAlterarDados window = new TelaAlterarDados();
					window.frmAtualizaoCadastral.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaAlterarDados() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAtualizaoCadastral = new JFrame();
		frmAtualizaoCadastral.getContentPane().setBackground(new Color(0, 0, 128));
		frmAtualizaoCadastral.getContentPane().setLayout(null);
		frmAtualizaoCadastral.setTitle("Atualização Cadastral");
		frmAtualizaoCadastral.setBounds(100, 100, 464, 433);
		frmAtualizaoCadastral.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblTitulo = new JLabel("ATUALIZAÇÃO CADASTRAL");
		lblTitulo.setBounds(64, 21, 329, 27);
		lblTitulo.setForeground(new Color(100, 149, 237));
		lblTitulo.setFont(new Font("Verdana", Font.BOLD, 21));
		frmAtualizaoCadastral.getContentPane().add(lblTitulo);
		
		JLabel lblOlaUser = new JLabel("Olá, ");
		lblOlaUser.setForeground(new Color(255, 255, 255));
		lblOlaUser.setFont(new Font("Verdana", Font.PLAIN, 18));
		lblOlaUser.setBounds(74, 58, 292, 27);
		frmAtualizaoCadastral.getContentPane().add(lblOlaUser);
		lblOlaUser.setText("Olá, "+ControleSessao.receberNome());
		
		
		JLabel lblNome = new JLabel("Nome Completo:");
		lblNome.setForeground(new Color(240, 248, 255));
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNome.setBounds(64, 101, 316, 26);
		frmAtualizaoCadastral.getContentPane().add(lblNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldNome.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textFieldNome.setColumns(10);
		textFieldNome.setBounds(64, 127, 316, 26);
		frmAtualizaoCadastral.getContentPane().add(textFieldNome);
		
		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setForeground(new Color(240, 248, 255));
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEmail.setBounds(64, 156, 316, 26);
		frmAtualizaoCadastral.getContentPane().add(lblEmail);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(64, 182, 316, 26);
		frmAtualizaoCadastral.getContentPane().add(textFieldEmail);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setForeground(new Color(240, 248, 255));
		lblSenha.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSenha.setBounds(64, 209, 316, 26);
		frmAtualizaoCadastral.getContentPane().add(lblSenha);
		
		textFieldSenha = new JTextField();
		textFieldSenha.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldSenha.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textFieldSenha.setColumns(10);
		textFieldSenha.setBounds(64, 235, 316, 26);
		frmAtualizaoCadastral.getContentPane().add(textFieldSenha);
		
		JButton btnAlterar = new JButton("Alterar Cadastro");
		btnAlterar.setForeground(Color.WHITE);
		btnAlterar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAlterar.setBackground(new Color(102, 153, 255));
		btnAlterar.setBounds(143, 288, 145, 26);
		frmAtualizaoCadastral.getContentPane().add(btnAlterar);
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JSONObject objetoJSON = new JSONObject();
				String novoNome = textFieldNome.getText();
				String novoEmail = textFieldEmail.getText();
				String novaSenha = textFieldSenha.getText();
				
				if(ValidaDados.validarCadastro(novoNome, novoEmail, novaSenha)) {
					try {		
						String senhaCrip  = CriptografiaSenha.criptografar(novaSenha);
						objetoJSON.put("nome", novoNome);
						objetoJSON.put("email", novoEmail);
						objetoJSON.put("senha", senhaCrip);
						objetoJSON.put("token", ControleSessao.receberToken());
						objetoJSON.put("id", ControleSessao.receberId());
						objetoJSON.put("operacao", 3);
						System.out.println("\tATUALIZAÇÃO CADASTRAL\nEnviando mensagem do CLIENTE: " + objetoJSON + "\n pela porta: " + TelaClientePorta.portaCliente +" utilizando o IP: "+TelaClientePorta.ipCliente);
						JSONObject respostaServidor = ClienteECHO.conectarServidor(objetoJSON);
						System.out.println("\tATUALIZAÇÃO CADASTRAL\nRecebendo mensagem do SERVIDOR: "+respostaServidor);
						
						String status = respostaServidor.getString("status");
						
						if(status.equals("OK")) {
							System.out.println("Tela Cadastro: "+respostaServidor);
							JOptionPane.showMessageDialog(frmAtualizaoCadastral, "Atualização dos Dados realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
							TelaFeed.setState(false);
							ControleSessao.limparDadosSessao();
							frmAtualizaoCadastral.dispose();
						}else {
							JOptionPane.showMessageDialog(frmAtualizaoCadastral, "Algo deu errado durante a Atualização Cadastral!", "Erro", JOptionPane.ERROR_MESSAGE);
						}
							
					}catch(Exception ex) {
						System.out.println("Erro:"+ex);
					}
				}else {
					if(ValidaDados.validarNome(novoNome)== false) {
						JOptionPane.showMessageDialog(frmAtualizaoCadastral, "Nome inválido, utilize entre 3 e 50 caracteres.", "Erro", JOptionPane.ERROR_MESSAGE);
					}
					if(ValidaDados.validarEmail(novoEmail)== false) {
						JOptionPane.showMessageDialog(frmAtualizaoCadastral, "E-mail inválido, preencha o campo corretamente", "Erro", JOptionPane.ERROR_MESSAGE);
					}
					if(ValidaDados.validarSenha(novaSenha)==false) {
						JOptionPane.showMessageDialog(frmAtualizaoCadastral, "Senha inválida, utilize entre 5 e 10 caracteres.", "Erro", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setForeground(new Color(102, 153, 255));
		btnVoltar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnVoltar.setBackground(Color.WHITE);
		btnVoltar.setBounds(143, 324, 145, 26);
		frmAtualizaoCadastral.getContentPane().add(btnVoltar);
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmAtualizaoCadastral.dispose();
			}
		});
		
	}

	public JFrame getFrmAtualizaoCadastral() {
		return frmAtualizaoCadastral;
	}
}
