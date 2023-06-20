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
import servidorECHO.ClienteECHO;

import javax.swing.JTextField;
import javax.swing.JButton;

public class TelaExcluirIncidente {

	private JFrame frmExcluirIncidente;
	private JTextField textFieldIdIncidente;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaExcluirIncidente window = new TelaExcluirIncidente();
					window.frmExcluirIncidente.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaExcluirIncidente() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmExcluirIncidente = new JFrame();
		frmExcluirIncidente.getContentPane().setBackground(new Color(0, 0, 128));
		frmExcluirIncidente.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("EXCLUSÃO DE INCIDENTES");
		lblNewLabel.setForeground(new Color(100, 149, 237));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 20));
		lblNewLabel.setBounds(40, 35, 306, 29);
		frmExcluirIncidente.getContentPane().add(lblNewLabel);
		
		JLabel lblSubTitle = new JLabel("Digite o ID do Incidente que deseja remover:");
		lblSubTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblSubTitle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSubTitle.setForeground(new Color(255, 255, 255));
		lblSubTitle.setBounds(50, 74, 299, 22);
		frmExcluirIncidente.getContentPane().add(lblSubTitle);
		
		textFieldIdIncidente = new JTextField();
		textFieldIdIncidente.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldIdIncidente.setBounds(59, 103, 268, 29);
		frmExcluirIncidente.getContentPane().add(textFieldIdIncidente);
		textFieldIdIncidente.setColumns(10);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setBackground(new Color(102, 153, 255));
		btnExcluir.setForeground(new Color(255, 255, 255));
		btnExcluir.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnExcluir.setBounds(133, 142, 112, 29);
		frmExcluirIncidente.getContentPane().add(btnExcluir);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int idIncidente = Integer.parseInt(textFieldIdIncidente.getText());
				if(ControleSessao.receberToken().length() == 20) {
					JSONObject objetoJSON = new JSONObject();
					objetoJSON.put("operacao", 6);
					objetoJSON.put("id", ControleSessao.receberId());
					objetoJSON.put("token", ControleSessao.receberToken());
					objetoJSON.put("id_incidente", idIncidente);
					try {		
						System.out.println("\tREMOVER INCIDENTE REPORTADO\nEnviando mensagem do CLIENTE: " + objetoJSON + "\n pela porta: " + TelaClientePorta.portaCliente +" utilizando o IP: "+TelaClientePorta.ipCliente);
						JSONObject respostaServidor = ClienteECHO.conectarServidor(objetoJSON);
						System.out.println("\tREMOVER INCIDENTE REPORTADO\nRecebendo mensagem do SERVIDOR: "+respostaServidor);
						String status = respostaServidor.getString("status");
						
						if(status.equals("OK")) {
							JOptionPane.showMessageDialog(frmExcluirIncidente, "Remoção de Incidente realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
							frmExcluirIncidente.dispose();
								
						}else {
							JOptionPane.showMessageDialog(frmExcluirIncidente, status, "Erro", JOptionPane.ERROR_MESSAGE);
						}
							
					}catch(Exception ex) {
						JOptionPane.showMessageDialog(frmExcluirIncidente, "Algo deu errado durante a Remoção de Incidente!", "Erro", JOptionPane.ERROR_MESSAGE);
						System.out.println("Erro:"+ex);
					}
				}else {
					JOptionPane.showMessageDialog(frmExcluirIncidente, "Algo deu errado durante a Remoção de Incidente!", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setBackground(new Color(255, 255, 255));
		btnVoltar.setForeground(new Color(102, 153, 255));
		btnVoltar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnVoltar.setBounds(133, 181, 112, 29);
		frmExcluirIncidente.getContentPane().add(btnVoltar);
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmExcluirIncidente.dispose();
			}
		});
		
		JLabel lblAviso = new JLabel("*Um incidente só pode ser excluído se você o reportou");
		lblAviso.setHorizontalAlignment(SwingConstants.CENTER);
		lblAviso.setForeground(Color.WHITE);
		lblAviso.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAviso.setBounds(17, 227, 353, 41);
		frmExcluirIncidente.getContentPane().add(lblAviso);
		frmExcluirIncidente.setTitle("Excluir Incidente");
		frmExcluirIncidente.setBounds(100, 100, 394, 355);
		frmExcluirIncidente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public JFrame getFrmExcluirIncidente() {
		return frmExcluirIncidente;
	}
}
