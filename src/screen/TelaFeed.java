package screen;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.util.Timer;
import java.util.TimerTask;
import services.ControleSessao;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class TelaFeed {
	private TelaLogin login = new TelaLogin();
	private TelaCadastro cadastro = new TelaCadastro();
	private TelaLogout logout = new TelaLogout();
	private JFrame frmFeedSmaicc;
	private JLabel lblIDUsuario;
	private JButton btnAltDados;
	private JButton btnReport;
	private JButton btnLogout;
	private JButton btnCadastro;
	private JButton btnLogin;
	private static boolean state = false;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaFeed window = new TelaFeed();
					window.frmFeedSmaicc.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaFeed() {
		initialize();
		Timer timer = new Timer();
	    timer.schedule(new TimerTask() {
	        @Override
	        public void run() {
	        		update();
	        		//timer.cancel();
	        }
	    }, 0, 1000);
			
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	
		frmFeedSmaicc = new JFrame();
		frmFeedSmaicc.getContentPane().setBackground(new Color(0, 0, 128));
		frmFeedSmaicc.setTitle("Feed SMAICC");
		frmFeedSmaicc.setBounds(100, 100, 880, 469);
		frmFeedSmaicc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFeedSmaicc.getContentPane().setLayout(null);
		
		JScrollPane feedAcidentes = new JScrollPane();
		feedAcidentes.setBounds(30, 78, 609, 316);
		frmFeedSmaicc.getContentPane().add(feedAcidentes);
		
		JLabel lblTitulo = new JLabel("Acidentes Noticiados");
		lblTitulo.setForeground(new Color(236, 236, 255));
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblTitulo.setBounds(30, 26, 317, 42);
		frmFeedSmaicc.getContentPane().add(lblTitulo);
		
		lblIDUsuario = new JLabel("");
		lblIDUsuario.setForeground(new Color(236, 236, 255));
		lblIDUsuario.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblIDUsuario.setBounds(672, 181, 159, 78);
		frmFeedSmaicc.getContentPane().add(lblIDUsuario);
		
		btnCadastro = new JButton("Cadastre-se");
		btnCadastro.setBackground(new Color(236, 236, 255));
		btnCadastro.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnCadastro.setBounds(672, 363, 171, 31);
		frmFeedSmaicc.getContentPane().add(btnCadastro);
		btnCadastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastro.getFrmCadastroSmaicc().setVisible(true);
			}
		});
		
		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnLogin.setBackground(new Color(236, 236, 255));
		btnLogin.setBounds(672, 322, 171, 31);
		frmFeedSmaicc.getContentPane().add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login.getFrmLoginSmaicc().setVisible(true);
				
			}
		});
		
		btnAltDados = new JButton("Alterar Dados");
		btnAltDados.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAltDados.setBackground(new Color(236, 236, 255));
		btnAltDados.setBounds(672, 281, 171, 31);
		frmFeedSmaicc.getContentPane().add(btnAltDados);
		
		btnReport = new JButton("Reportar Incidente");
		btnReport.setBackground(new Color(236, 236, 255));
		btnReport.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnReport.setBounds(672, 363, 171, 31);
		frmFeedSmaicc.getContentPane().add(btnReport);
		btnReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Reportar incidente!");
			}
		});
		
		btnLogout = new JButton("Logout");
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnLogout.setBackground(new Color(236, 236, 255));
		btnLogout.setBounds(672, 322, 171, 31);
		frmFeedSmaicc.getContentPane().add(btnLogout);
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logout.getFrmLogout().setVisible(true);
			}
		});
		
		lblIDUsuario.setVisible(false);
		btnAltDados.setVisible(false);
		btnReport.setVisible(false);
		btnLogout.setVisible(false);
	}
	
	private void update() {
		StringBuilder nomeUsuario = new StringBuilder();
		int idUser = ControleSessao.receberId();
		String nomeUser = ControleSessao.receberNome();
		String tokenUser = ControleSessao.receberToken();
		
		if(state == false) {
			
			lblIDUsuario.setVisible(false);
			btnAltDados.setVisible(false);
			btnReport.setVisible(false);
			btnLogout.setVisible(false);
			btnCadastro.setVisible(true);
			btnLogin.setVisible(true);
			
		}else if(state == true && tokenUser.length() == 20){
			nomeUsuario.append(nomeUser);
			nomeUsuario.append(" ID: ");
			nomeUsuario.append(idUser);
			String nome = nomeUsuario.toString();
			lblIDUsuario.setText(nome);
			lblIDUsuario.setVisible(true);
			btnAltDados.setVisible(true);
			btnReport.setVisible(true);
			btnLogout.setVisible(true);
			btnCadastro.setVisible(false);
			btnLogin.setVisible(false);
		}	
	}
	
	public JFrame getFrmFeedSmaicc() {
		return frmFeedSmaicc;
	}

	public void setFrmFeedSmaicc(JFrame frmFeedSmaicc) {
		this.frmFeedSmaicc = frmFeedSmaicc;
	}

	public static void  setState(boolean newState) {
		state = newState;
	}
	
	
}
