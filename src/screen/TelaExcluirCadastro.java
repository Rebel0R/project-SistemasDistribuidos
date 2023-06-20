package screen;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import org.json.JSONObject;

import services.ControleSessao;
import services.CriptografiaSenha;
import servidorECHO.ClienteECHO;

import javax.swing.JTextField;
import javax.swing.JButton;

public class TelaExcluirCadastro {

	private JFrame frmExcluirCadastro;
	private JTextField textFieldSenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaExcluirCadastro window = new TelaExcluirCadastro();
					window.frmExcluirCadastro.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaExcluirCadastro() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmExcluirCadastro = new JFrame();
		frmExcluirCadastro.getContentPane().setBackground(new Color(0, 0, 128));
		frmExcluirCadastro.setTitle("Excluir Cadastro");
		frmExcluirCadastro.setBounds(100, 100, 450, 340);
		frmExcluirCadastro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmExcluirCadastro.getContentPane().setLayout(null);
		
		JLabel lblExcluirCadastro = new JLabel("EXCLUIR CADASTRO");
		lblExcluirCadastro.setHorizontalAlignment(SwingConstants.CENTER);
		lblExcluirCadastro.setForeground(new Color(100, 149, 237));
		lblExcluirCadastro.setFont(new Font("Verdana", Font.BOLD, 21));
		lblExcluirCadastro.setBounds(52, 30, 329, 27);
		frmExcluirCadastro.getContentPane().add(lblExcluirCadastro);
		
		JLabel lblInformeSuaSenha = new JLabel("Informe sua senha:");
		lblInformeSuaSenha.setForeground(new Color(240, 248, 255));
		lblInformeSuaSenha.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblInformeSuaSenha.setBounds(91, 82, 145, 26);
		frmExcluirCadastro.getContentPane().add(lblInformeSuaSenha);
		
		textFieldSenha = new JTextField();
		textFieldSenha.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldSenha.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textFieldSenha.setColumns(10);
		textFieldSenha.setBounds(91, 108, 251, 26);
		frmExcluirCadastro.getContentPane().add(textFieldSenha);
		
		JButton btnExcluirCadastro = new JButton("Excluir Cadastro");
		btnExcluirCadastro.setForeground(Color.WHITE);
		btnExcluirCadastro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnExcluirCadastro.setBackground(new Color(102, 153, 255));
		btnExcluirCadastro.setBounds(144, 175, 145, 26);
		frmExcluirCadastro.getContentPane().add(btnExcluirCadastro);
		btnExcluirCadastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//frmExcluirCadastro.dispose();
				String senhaUser = textFieldSenha.getText();
				if(senhaUser.isEmpty()) {
					JOptionPane.showMessageDialog(frmExcluirCadastro, "Preencha o campo de senha corretamente", "Erro", JOptionPane.ERROR_MESSAGE);
				}else {
					int resposta = JOptionPane.showConfirmDialog(null, "Você confirma essa ação?", "Exclusão ", JOptionPane.OK_CANCEL_OPTION);    
			        boolean confirmado = (resposta == JOptionPane.OK_OPTION);
			        if (confirmado) {
			            System.out.println("Ação confirmada!");
			            if(ControleSessao.receberToken().length() == 20) {
							JSONObject objetoJSON = new JSONObject();
							objetoJSON.put("operacao", 8);
							objetoJSON.put("id", ControleSessao.receberId());
							objetoJSON.put("token", ControleSessao.receberToken());
							String senhaCrip = CriptografiaSenha.criptografar(senhaUser);
							objetoJSON.put("senha", senhaCrip);
							try {		
								System.out.println("\tEXCLUSÃO CADASTRAL\nEnviando mensagem do CLIENTE: " + objetoJSON + "\n pela porta: " + TelaClientePorta.portaCliente +" utilizando o IP: "+TelaClientePorta.ipCliente);
								JSONObject respostaServidor = ClienteECHO.conectarServidor(objetoJSON);
								System.out.println("\tEXCLUSÃO CADASTRAL\nRecebendo mensagem do SERVIDOR: "+respostaServidor);
								String status = respostaServidor.getString("status");
								if(status.equals("OK")) {
									JOptionPane.showMessageDialog(frmExcluirCadastro, "Exclusão de Cadastro realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
									TelaAlterarDados.stateAttCad = true;
									frmExcluirCadastro.dispose();
										
								}else {
									JOptionPane.showMessageDialog(frmExcluirCadastro, status, "Erro", JOptionPane.ERROR_MESSAGE);
								}
									
							}catch(Exception ex) {
								JOptionPane.showMessageDialog(frmExcluirCadastro, "Algo deu errado durante a Exclusão de Cadastro!", "Erro", JOptionPane.ERROR_MESSAGE);
								System.out.println("Erro:"+ex);
							}
						}else {
							JOptionPane.showMessageDialog(frmExcluirCadastro, "Algo deu errado durante a Exclusão de Cadastro!", "Erro", JOptionPane.ERROR_MESSAGE);
						}
			        } else {
			        	JOptionPane.showMessageDialog(frmExcluirCadastro, "Exclusão cadastral interrompida por parte do usuário", "Operação Interrompida", JOptionPane.INFORMATION_MESSAGE);
			        }
				}
			}
		});
		
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setForeground(new Color(102, 153, 255));
		btnVoltar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnVoltar.setBackground(Color.WHITE);
		btnVoltar.setBounds(144, 211, 145, 26);
		frmExcluirCadastro.getContentPane().add(btnVoltar);
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmExcluirCadastro.dispose();
			}
		});
		
	}

	public JFrame getFrmExcluirCadastro() {
		return frmExcluirCadastro;
	}
}
