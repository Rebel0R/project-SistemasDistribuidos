package screen;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;


public class TelaClientePorta {
	private TelaFeed feed = new TelaFeed();
	public static String ipCliente;
	public static int portaCliente;
	private JFrame framePort;
	private JTextField textFieldPort;
	private JTextField textFieldIp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaClientePorta window = new TelaClientePorta();
					window.framePort.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaClientePorta() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		framePort = new JFrame();
		framePort.setTitle("Porta Cliente");
		framePort.getContentPane().setBackground(new Color(0, 0, 128));
		framePort.setBounds(100, 100, 520, 372);
		framePort.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		framePort.getContentPane().setLayout(null);
		
		JLabel lblTitulo = new JLabel("Bem-vindo, Usuário");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setForeground(new Color(236, 236, 255));
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblTitulo.setBounds(119, 37, 255, 35);
		framePort.getContentPane().add(lblTitulo);
		
		JLabel lblIP = new JLabel("Informe o endere\u00E7o IP:");
		lblIP.setForeground(new Color(236, 236, 255));
		lblIP.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblIP.setBounds(163, 94, 178, 24);
		framePort.getContentPane().add(lblIP);
		
		textFieldIp = new JTextField();
		textFieldIp.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldIp.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textFieldIp.setColumns(10);
		textFieldIp.setBounds(158, 128, 189, 24);
		framePort.getContentPane().add(textFieldIp);
		
		JLabel lblInput = new JLabel("Informe uma porta para se conectar:");
		lblInput.setForeground(new Color(236, 236, 255));
		lblInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblInput.setBounds(117, 162, 272, 24);
		framePort.getContentPane().add(lblInput);
		
		textFieldPort = new JTextField();
		textFieldPort.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textFieldPort.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldPort.setBounds(158, 197, 189, 24);
		framePort.getContentPane().add(textFieldPort);
		textFieldPort.setColumns(10);
		
		JButton btnConectar = new JButton("Conectar");
		btnConectar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnConectar.setBackground(new Color(236, 236, 255));
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String portaRecebida = textFieldPort.getText();
				String ipRecebido = textFieldIp.getText();
				int portaRecebidaInt = Integer.parseInt(portaRecebida);
				if(portaRecebida.isEmpty() || ipRecebido.isEmpty()) {
					JOptionPane.showMessageDialog(framePort, "Por favor, preencha o campo informado.", "Erro", JOptionPane.ERROR_MESSAGE);
				}else if(portaRecebidaInt < 20000 || portaRecebidaInt>25000) {
					JOptionPane.showMessageDialog(framePort, "Por favor, informe um valor entre 20000 e 25000", "Erro", JOptionPane.ERROR_MESSAGE);
				}else {
					System.out.println("\tCLIENTE\nEndereço IP: "+ipRecebido+"\nPorta escolhida: "+portaRecebida);
					portaCliente = portaRecebidaInt;
					ipCliente = ipRecebido;
					feed.getFrmFeedSmaicc().setVisible(true);
					framePort.dispose();
					
				}
			}
		});
		btnConectar.setBounds(199, 241, 107, 35);
		framePort.getContentPane().add(btnConectar);
		
		
	}

	public JFrame getFramePort() {
		return framePort;
	}
}
