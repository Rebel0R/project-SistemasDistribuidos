package screen;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.util.Timer;
import java.util.TimerTask;
import services.ControleSessao;

import services.ValidaDados;
import servidorECHO.ClienteECHO;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.json.*;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class TelaFeed {
	private TelaLogin login = new TelaLogin();
	private TelaCadastro cadastro = new TelaCadastro();
	private TelaLogout logout = new TelaLogout();
	private TelaCadastroIncidente cadInc = new TelaCadastroIncidente();
	private TelaAlterarDados altDados = new TelaAlterarDados();
	private JFrame frmFeedSmaicc;
	private JLabel lblIDUsuario;
	private JLabel lblNomeUser;
	private JButton btnAltDados;
	private JButton btnReport;
	private JButton btnLogout;
	private JButton btnCadastro;
	private JButton btnLogin;
	private static boolean state = false;
	private JTextField textFieldData;
	private JTextField textFieldCidade;
	
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
		
		JLabel lblTitulo = new JLabel("Acidentes Noticiados");
		lblTitulo.setForeground(new Color(236, 236, 255));
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblTitulo.setBounds(30, 26, 317, 42);
		frmFeedSmaicc.getContentPane().add(lblTitulo);
		
		JLabel lblData = new JLabel("Data (yyyy-mm-dd):");
		lblData.setForeground(new Color(240, 248, 255));
		lblData.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblData.setBounds(30, 78, 143, 26);
		frmFeedSmaicc.getContentPane().add(lblData);
		
		textFieldData = new JTextField();
		textFieldData.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldData.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textFieldData.setColumns(10);
		textFieldData.setBounds(30, 104, 143, 26);
		frmFeedSmaicc.getContentPane().add(textFieldData);
		
		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setForeground(new Color(240, 248, 255));
		lblCidade.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCidade.setBounds(183, 78, 168, 26);
		frmFeedSmaicc.getContentPane().add(lblCidade);
		
		textFieldCidade = new JTextField();
		textFieldCidade.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldCidade.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textFieldCidade.setColumns(10);
		textFieldCidade.setBounds(183, 104, 168, 26);
		frmFeedSmaicc.getContentPane().add(textFieldCidade);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setForeground(new Color(240, 248, 255));
		lblEstado.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEstado.setBounds(361, 78, 130, 26);
		frmFeedSmaicc.getContentPane().add(lblEstado);
		
		String[] estados = {"Estados","AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(estados);
		JComboBox<String> comboBoxEstados = new JComboBox<String>(model);
		comboBoxEstados.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboBoxEstados.setBackground(Color.WHITE);
		comboBoxEstados.setBounds(361, 103, 70, 26);
		frmFeedSmaicc.getContentPane().add(comboBoxEstados);
		
		JScrollPane feedAcidentes = new JScrollPane();
		feedAcidentes.setBounds(30, 141, 609, 253);
		frmFeedSmaicc.getContentPane().add(feedAcidentes);
		
		JTextArea textAreaFeed = new JTextArea();
		textAreaFeed.setEditable(false);
		feedAcidentes.setViewportView(textAreaFeed);
		
		JButton btnbuscar = new JButton("Buscar");
		btnbuscar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnbuscar.setBackground(new Color(236, 236, 255));
		btnbuscar.setBounds(451, 100, 123, 31);
		frmFeedSmaicc.getContentPane().add(btnbuscar);
		btnbuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaFeed.setText("");
				String data = textFieldData.getText();
				String cidade = textFieldCidade.getText();
				cidade = cidade.toUpperCase();
				String estado = (String)comboBoxEstados.getSelectedItem();
				JSONObject objetoJSON = new JSONObject();
				if(ValidaDados.validarData(data) && ValidaDados.validarCidade(cidade) && ValidaDados.validarUF(estado)) {
					try {		
						objetoJSON.put("estado", estado);
						objetoJSON.put("cidade", cidade);
						objetoJSON.put("data", data);
						objetoJSON.put("operacao", 4);
						System.out.println("\tLISTAR INCIDENTES\nEnviando mensagem do CLIENTE: " + objetoJSON + "\n pela porta: " + TelaClientePorta.portaCliente +" utilizando o IP: "+TelaClientePorta.ipCliente);
						JSONObject respostaServidor = ClienteECHO.conectarServidor(objetoJSON);
						System.out.println("\tLISTAR INCIDENTES\nRecebendo mensagem do SERVIDOR: "+respostaServidor);
						String status = respostaServidor.getString("status");
						
						if(status.equals("OK")) {
							//System.out.println("LISTAR INCIDENTES: "+respostaServidor);
							JSONArray jsonArrayIncidentes = (JSONArray) respostaServidor.get("incidentes");
							if(jsonArrayIncidentes.isEmpty()) {
								textAreaFeed.setText("\tNENHUM INCIDENTE FOI ENCONTRADO!");
							}else {
								textAreaFeed.setText("\tRESULTADOS DA BUSCA SOBRE: "+cidade+" - "+estado+" DATA: "+data);
								for (Object obj : jsonArrayIncidentes) {
								    JSONObject incidente = (JSONObject) obj;
								    String tipoIncidente = incidente.getString("tipo_incidente");
								    String dataIncidente = incidente.getString("data");
								    String horaIncidente = incidente.getString("hora");
								    String cidadeIncidente = incidente.getString("cidade");
								    String bairroIncidente = incidente.getString("bairro");
								    String estadoIncidente = incidente.getString("estado");
								    String ruaIncidente = incidente.getString("rua");
								    int idIncidente = incidente.getInt("id");
								    textAreaFeed.append("\nData: "+dataIncidente+" Hora: "+horaIncidente+" ID: "+idIncidente+" Tipo de Incidente: "+tipoIncidente+"\nCidade: "+cidadeIncidente+" - "+estadoIncidente+" Rua: "+ruaIncidente+" Bairro: "+bairroIncidente);
								}
								textAreaFeed.append("\n-------------------------------------------------------------------------------\n");
							}
						}else {
							JOptionPane.showMessageDialog(frmFeedSmaicc, "Algo deu errado durante a busca!", "Erro", JOptionPane.ERROR_MESSAGE);
							textAreaFeed.setText("");
						}
							
					}catch(Exception ex) {
						System.out.println("Erro:"+ex);
					}
				}
			}
		});
		
		lblNomeUser = new JLabel("");
		lblNomeUser.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNomeUser.setForeground(new Color(236, 236, 255));
		lblNomeUser.setBackground(new Color(236, 236, 255));
		lblNomeUser.setBounds(672, 189, 159, 37);
		frmFeedSmaicc.getContentPane().add(lblNomeUser);
		
		lblIDUsuario = new JLabel("");
		lblIDUsuario.setForeground(new Color(236, 236, 255));
		lblIDUsuario.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblIDUsuario.setBounds(672, 233, 159, 26);
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
		btnAltDados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				altDados.getFrmAtualizaoCadastral().setVisible(true);
			}
		});
		
		btnReport = new JButton("Reportar Incidente");
		btnReport.setBackground(new Color(236, 236, 255));
		btnReport.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnReport.setBounds(672, 363, 171, 31);
		frmFeedSmaicc.getContentPane().add(btnReport);
		btnReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadInc.getFrmCadastrarIncidente().setVisible(true);
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
		
		lblNomeUser.setVisible(false);
		lblIDUsuario.setVisible(false);
		btnAltDados.setVisible(false);
		btnReport.setVisible(false);
		btnLogout.setVisible(false);
	}
	
	private void update() {
		int idUser = ControleSessao.receberId();
		String idUserStr = Integer.toString(idUser);
		String nomeUser = ControleSessao.receberNome();
		String tokenUser = ControleSessao.receberToken();
		
		if(state == false) {
			lblNomeUser.setVisible(false);
			lblIDUsuario.setVisible(false);
			btnAltDados.setVisible(false);
			btnReport.setVisible(false);
			btnLogout.setVisible(false);
			btnCadastro.setVisible(true);
			btnLogin.setVisible(true);
			
		}else if(state == true && tokenUser.length() == 20){
			lblNomeUser.setText("User: "+nomeUser);
			lblIDUsuario.setText("ID: "+idUserStr);
			lblNomeUser.setVisible(true);
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
