package screen;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import services.ControleSessao;
import servidorECHO.ClienteECHO;

import javax.swing.SwingConstants;

import javax.swing.JButton;

public class TelaLogout {

	private JFrame frmLogout;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLogout window = new TelaLogout();
					window.frmLogout.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaLogout() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogout = new JFrame();
		frmLogout.getContentPane().setBackground(new Color(0, 0, 128));
		frmLogout.setTitle("Logout");
		frmLogout.setBounds(100, 100, 450, 300);
		frmLogout.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogout.getContentPane().setLayout(null);
		
		JLabel lblTitle = new JLabel("LOGOUT");
		lblTitle.setFont(new Font("Verdana", Font.BOLD, 20));
		lblTitle.setForeground(new Color(100, 149, 237));
		lblTitle.setBounds(171, 68, 100, 32);
		frmLogout.getContentPane().add(lblTitle);
		
		JLabel lblSubTitle = new JLabel("Realmente deseja realizar seu logout?");
		lblSubTitle.setHorizontalAlignment(SwingConstants.LEFT);
		lblSubTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSubTitle.setForeground(new Color(240, 248, 245));
		lblSubTitle.setBounds(85, 95, 273, 46);
		frmLogout.getContentPane().add(lblSubTitle);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setForeground(new Color(255, 255, 255));
		btnLogout.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnLogout.setBackground(new Color(102, 103, 255));
		btnLogout.setBounds(113, 151, 100, 32);
		frmLogout.getContentPane().add(btnLogout);
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JSONObject json = new JSONObject();
				String token = ControleSessao.receberToken();
				int id = ControleSessao.receberId();
				if(token.length()==20) {
					json.put("operacao", 9);
					json.put("token", token);
					json.put("id", id);
					System.out.println("Enviando mensagem: " + json + "\n através da porta: " + TelaClientePorta.portaCliente);
					try {
						JSONObject respostaServidor = ClienteECHO.conectarServidor(json);
						String status = respostaServidor.getString("status");
						int operacao = respostaServidor.getInt("operacao");
						
						if(status.equals("OK")) {
							JOptionPane.showMessageDialog(frmLogout, "Operação: +"+operacao+"\nLogout realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
							TelaFeed.setState(false);
							frmLogout.dispose();
						}else {
							JOptionPane.showMessageDialog(frmLogout, "Algo deu errado!", "Erro", JOptionPane.ERROR_MESSAGE);
						}
					} catch (JSONException e1) {
					
						e1.printStackTrace();
					} catch (IOException e1) {
						
						e1.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(frmLogout, "Token inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnCancelar.setBackground(new Color(102, 103, 255));
		btnCancelar.setBounds(223, 151, 100, 32);
		frmLogout.getContentPane().add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmLogout.dispose();
			}
		});
	}

	public JFrame getFrmLogout() {
		return frmLogout;
	}
}
