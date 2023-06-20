package servidorECHO;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.*;

public class TelaServidorPorta {
	private JLabel lblPorta;
	private JFrame frmSistemaServidor;
	private JTextField textFieldPorta;
	private JButton btnEnviarPorta;
	private JLabel lblPortaRec;
	private JLabel lblIpServidor;
	private JLabel lblSocket;
	public JTextArea trocaMensagens;
	private JScrollPane scrollPaneH;
	public static int portaServer;
	//private boolean stateIp = false;
	private boolean statePortCheck = false;
	//private boolean stateSocket = false;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaServidorPorta window = new TelaServidorPorta();
					window.frmSistemaServidor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaServidorPorta() {
		initialize();
		Timer timer = new Timer();
	    timer.schedule(new TimerTask() {
	        @Override
	        public void run() {
	        		update();
	        		/*if(statePort == true && stateIp == true) {
	        			lblSocket.setText("Socket: "+ServidorECHO.client);
	        			timer.cancel();
	        		}*/
	        		
	        }
	    }, 0, 1000);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSistemaServidor = new JFrame();
		frmSistemaServidor.getContentPane().setBackground(new Color(0, 0, 128));
		frmSistemaServidor.getContentPane().setLayout(null);
		frmSistemaServidor.setTitle("Sistema Servidor");
		frmSistemaServidor.setBounds(100, 100, 636, 450);
		frmSistemaServidor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblTitle = new JLabel("Servidor SMAICC");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Verdana", Font.BOLD, 22));
		lblTitle.setForeground(new Color(255, 255, 255));
		lblTitle.setBounds(178, 10, 262, 26);
		frmSistemaServidor.getContentPane().add(lblTitle);
		
		lblPorta = new JLabel("Informe a porta desejada:");
		lblPorta.setHorizontalAlignment(SwingConstants.CENTER);
		lblPorta.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPorta.setForeground(new Color(255, 255, 255));
		lblPorta.setBounds(159, 141, 303, 26);
		frmSistemaServidor.getContentPane().add(lblPorta);
		
		textFieldPorta = new JTextField();
		textFieldPorta.setFont(new Font("Verdana", Font.PLAIN, 14));
		textFieldPorta.setBounds(209, 177, 199, 26);
		frmSistemaServidor.getContentPane().add(textFieldPorta);
		textFieldPorta.setColumns(10);
		
		
		lblPortaRec = new JLabel("Porta: ");
		lblPortaRec.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblPortaRec.setForeground(new Color(255, 255, 255));
		lblPortaRec.setBackground(new Color(255, 255, 255));
		lblPortaRec.setBounds(122, 46, 149, 26);
		frmSistemaServidor.getContentPane().add(lblPortaRec);
		
		lblIpServidor = new JLabel("IP Servidor: ");
		lblIpServidor.setForeground(Color.WHITE);
		lblIpServidor.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblIpServidor.setBackground(Color.WHITE);
		lblIpServidor.setBounds(287, 46, 286, 26);
		frmSistemaServidor.getContentPane().add(lblIpServidor);
		
		lblSocket = new JLabel("Socket:");
		lblSocket.setForeground(Color.WHITE);
		lblSocket.setFont(new Font("Verdana", Font.PLAIN, 10));
		lblSocket.setBackground(Color.WHITE);
		lblSocket.setBounds(122, 72, 478, 26);
		frmSistemaServidor.getContentPane().add(lblSocket);
		
		btnEnviarPorta = new JButton("Enviar");
		btnEnviarPorta.setFont(new Font("Verdana", Font.BOLD, 16));
		btnEnviarPorta.setForeground(new Color(0, 0, 128));
		btnEnviarPorta.setBackground(new Color(255, 255, 255));
		btnEnviarPorta.setBounds(257, 213, 102, 26);
		frmSistemaServidor.getContentPane().add(btnEnviarPorta);
		btnEnviarPorta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String portaRecebida = textFieldPorta.getText();
				if(portaRecebida.isEmpty()) {
					JOptionPane.showMessageDialog(frmSistemaServidor, "Por favor, preencha o campo informado.", "Erro", JOptionPane.ERROR_MESSAGE);
				}else {
					try {
						int portaRecebidaInt = Integer.parseInt(portaRecebida);
						System.out.println("\tSERVIDOR PORTA - Porta selecionada: "+portaRecebidaInt);
						portaServer = portaRecebidaInt;
						ServidorECHO.portaServidor = portaRecebidaInt;
						JOptionPane.showMessageDialog(frmSistemaServidor, "Valo para porta aceito com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
						statePortCheck = true;
			        } catch (NumberFormatException err) {
			        	JOptionPane.showMessageDialog(frmSistemaServidor, "Por favor, preencha o campo informado com números.", "Erro", JOptionPane.ERROR_MESSAGE);
			        }
				}
			}
		});
		
		trocaMensagens = new JTextArea();
		trocaMensagens.setWrapStyleWord(true);
		trocaMensagens.setFont(new Font("Verdana", Font.PLAIN, 12));
		trocaMensagens.setBounds(10, 108, 602, 294);
		trocaMensagens.setEnabled(false);
		trocaMensagens.setDisabledTextColor(Color.BLACK);
		frmSistemaServidor.getContentPane().add(trocaMensagens);
		
		scrollPaneH = new JScrollPane(trocaMensagens);
	    scrollPaneH.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scrollPaneH.setBounds(10, 108, 602, 294);
	    frmSistemaServidor.getContentPane().add(scrollPaneH);
		
		lblIpServidor.setVisible(false);
		lblSocket.setVisible(false);
		lblPortaRec.setVisible(false);
		trocaMensagens.setVisible(false);
		scrollPaneH.setVisible(false);
	}
	
	private void update() {
		if(statePortCheck) {
			if(ServidorECHO.portaServidor > 0 && ServidorECHO.enderecoIPServidor != null) {
				lblPortaRec.setText("Porta: "+ ServidorECHO.portaServidor);
				lblIpServidor.setText("Endereço IP: "+ServidorECHO.enderecoIPServidor.getHostAddress());
				lblIpServidor.setVisible(true);
				lblPortaRec.setVisible(true);
				//stateIp = true;
				lblSocket.setVisible(true);
				trocaMensagens.setVisible(true);
				scrollPaneH.setVisible(true);
				if(ServidorECHO.client == null) {
					lblSocket.setText("Socket: Aguardando conexão com cliente...");
					//stateSocket = false;
				}else {
					lblSocket.setText("Socket: "+ServidorECHO.client);
					//stateSocket = true;
				}
				
			}
			lblPorta.setVisible(false);
			textFieldPorta.setVisible(false);
			btnEnviarPorta.setVisible(false);	
		}
   }

	public JFrame getFrmSistemaServidor() {
		return frmSistemaServidor;
	}

	public void setFrmSistemaServidor(JFrame frmSistemaServidor) {
		this.frmSistemaServidor = frmSistemaServidor;
	}
}
