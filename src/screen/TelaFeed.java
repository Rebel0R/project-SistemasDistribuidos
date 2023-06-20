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
//import javax.swing.JTextArea;
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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TelaFeed {
	private TelaLogin login = new TelaLogin();
	private TelaCadastro cadastro = new TelaCadastro();
	private TelaLogout logout = new TelaLogout();
	private TelaCadastroIncidente cadInc = new TelaCadastroIncidente();
	private TelaAlterarDados altDados = new TelaAlterarDados();
	private TelaExcluirIncidente excluirInc = new TelaExcluirIncidente();
	private JFrame frmFeedSmaicc;
	private JLabel lblIDUsuario;
	private JLabel lblNomeUser;
	private JButton btnAltDados;
	private JButton btnReport;
	private JButton btnLogout;
	private JButton btnCadastro;
	private JButton btnLogin;
	private JButton btnSeusIncidentes;
	private static boolean state = false;
	private JTextField textFieldData;
	private JTextField textFieldCidade;
	private JButton btnRemoverIncidente;
	private JTable tableIncidentes;
	
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
		frmFeedSmaicc.setBounds(100, 100, 955, 488);
		frmFeedSmaicc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFeedSmaicc.getContentPane().setLayout(null);
		
		JLabel lblTitulo = new JLabel("Acidentes Reportados");
		lblTitulo.setBounds(30, 10, 317, 42);
		lblTitulo.setForeground(new Color(236, 236, 255));
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 28));
		frmFeedSmaicc.getContentPane().add(lblTitulo);
		
		JLabel lblData = new JLabel("Data (yyyy-mm-dd):");
		lblData.setBounds(30, 62, 143, 26);
		lblData.setForeground(new Color(240, 248, 255));
		lblData.setFont(new Font("Tahoma", Font.BOLD, 13));
		frmFeedSmaicc.getContentPane().add(lblData);
		
		textFieldData = new JTextField();
		textFieldData.setBounds(30, 88, 143, 26);
		textFieldData.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldData.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textFieldData.setColumns(10);
		frmFeedSmaicc.getContentPane().add(textFieldData);
		
		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setBounds(183, 62, 168, 26);
		lblCidade.setForeground(new Color(240, 248, 255));
		lblCidade.setFont(new Font("Tahoma", Font.BOLD, 14));
		frmFeedSmaicc.getContentPane().add(lblCidade);
		
		textFieldCidade = new JTextField();
		textFieldCidade.setBounds(183, 88, 168, 26);
		textFieldCidade.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldCidade.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textFieldCidade.setColumns(10);
		frmFeedSmaicc.getContentPane().add(textFieldCidade);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(361, 62, 130, 26);
		lblEstado.setForeground(new Color(240, 248, 255));
		lblEstado.setFont(new Font("Tahoma", Font.BOLD, 14));
		frmFeedSmaicc.getContentPane().add(lblEstado);
		
		String[] estados = {"Estados","AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(estados);
		JComboBox<String> comboBoxEstados = new JComboBox<String>(model);
		comboBoxEstados.setBounds(361, 87, 70, 26);
		comboBoxEstados.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboBoxEstados.setBackground(Color.WHITE);
		frmFeedSmaicc.getContentPane().add(comboBoxEstados);
		
		String[] colunas = {"ID", "Tipo Incidente", "Data", "Hora", "Cidade", "UF", "Rua", "Bairro"};
        Object[][] dados = {
           
        };
        DefaultTableModel tableModel = new DefaultTableModel(dados, colunas);
		tableIncidentes = new JTable(tableModel);
		tableIncidentes.setBounds(30, 439, 696, -289);
		frmFeedSmaicc.getContentPane().add(tableIncidentes);
		
		JScrollPane scrollPane = new JScrollPane(tableIncidentes);
		// Definir a política de rolagem horizontal
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// Definir o tamanho e a posição do JScrollPane
		scrollPane.setBounds(30, 124, 696, 303);

		// Adicionar o JScrollPane ao conteúdo do JFrame
		frmFeedSmaicc.getContentPane().add(scrollPane);
		
		JButton btnbuscar = new JButton("Buscar");
		btnbuscar.setBounds(451, 84, 123, 31);
		btnbuscar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnbuscar.setBackground(new Color(236, 236, 255));
		frmFeedSmaicc.getContentPane().add(btnbuscar);
		btnbuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//textAreaFeed.setText("");
				DefaultTableModel tableModel = (DefaultTableModel) tableIncidentes.getModel();
				tableModel.setRowCount(0);
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
							JSONArray jsonArrayIncidentes = (JSONArray) respostaServidor.get("incidentes");
							if(jsonArrayIncidentes.isEmpty()) {
								//textAreaFeed.setText("\tNENHUM INCIDENTE FOI ENCONTRADO!");
								JOptionPane.showMessageDialog(frmFeedSmaicc, "Nenhum Incidente foi encontrado", "Resultado de Busca", JOptionPane.INFORMATION_MESSAGE);
							}else {
								//textAreaFeed.setText("\tRESULTADOS DA BUSCA SOBRE: "+cidade+" - "+estado+" DATA: "+data);
								for (Object obj : jsonArrayIncidentes) {
									String nomeTipoIncidente = "";
								    JSONObject incidente = (JSONObject) obj;
								    int tipoIncidente = incidente.getInt("tipo_incidente");
								    String dataIncidente = incidente.getString("data");
								    String horaIncidente = incidente.getString("hora");
								    String cidadeIncidente = incidente.getString("cidade");
								    String bairroIncidente = incidente.getString("bairro");
								    String estadoIncidente = incidente.getString("estado");
								    String ruaIncidente = incidente.getString("rua");
								    int idIncidente = incidente.getInt("id_incidente");
								    switch(tipoIncidente) {
									case 0:
										nomeTipoIncidente = "Sem Incidentes";
										break;
									case 1:
										nomeTipoIncidente = "Alagamento";
										break;
									case 2:
										nomeTipoIncidente = "Deslizamento";
										break;
									case 3:
										nomeTipoIncidente = "Acidente de carro";
										break;
									case 4:
										nomeTipoIncidente = "Obstrução da via";
										break;
									case 5:
										nomeTipoIncidente = "Fissura da via";
										break;
									case 6:
										nomeTipoIncidente = "Pista em obras";
										break;
									case 7:
										nomeTipoIncidente = "Lentidão na pista";
										break;
									case 8:
										nomeTipoIncidente = "Animais na pista";
										break;
									case 9:
										nomeTipoIncidente = "Nevoeiro";
										break;
									case 10:
										nomeTipoIncidente = "Tromba d'água";
										break;
								}
								    //textAreaFeed.append("\n\tData: "+dataIncidente+" Hora: "+horaIncidente+" ID: "+idIncidente+" Tipo de Incidente: "+nomeTipoIncidente+"\n\tCidade: "+cidadeIncidente+" - "+estadoIncidente+" Rua: "+ruaIncidente+" Bairro: "+bairroIncidente);
								    Object[] novaLinha = {idIncidente, nomeTipoIncidente, dataIncidente, horaIncidente, cidadeIncidente, estadoIncidente, ruaIncidente, bairroIncidente};
								    tableModel.addRow(novaLinha);
								}
								
							}
						}else {
							JOptionPane.showMessageDialog(frmFeedSmaicc, status, "Erro", JOptionPane.ERROR_MESSAGE);
						}
							
					}catch(Exception ex) {
						JOptionPane.showMessageDialog(frmFeedSmaicc, "Algo deu errado durante a busca!", "Erro", JOptionPane.ERROR_MESSAGE);
						System.out.println("Erro:"+ex);
					}
				}else {
					JOptionPane.showMessageDialog(frmFeedSmaicc, "Credenciais inválidas, preencha os campos corretamente", "Erro", JOptionPane.ERROR_MESSAGE);
				}
				textFieldData.setText("");
				textFieldCidade.setText("");
			}
		});
		
		lblNomeUser = new JLabel("");
		lblNomeUser.setBounds(747, 149, 159, 31);
		lblNomeUser.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNomeUser.setForeground(new Color(236, 236, 255));
		lblNomeUser.setBackground(new Color(236, 236, 255));
		frmFeedSmaicc.getContentPane().add(lblNomeUser);
		
		lblIDUsuario = new JLabel("");
		lblIDUsuario.setBounds(747, 184, 159, 26);
		lblIDUsuario.setForeground(new Color(236, 236, 255));
		lblIDUsuario.setFont(new Font("Tahoma", Font.BOLD, 15));
		frmFeedSmaicc.getContentPane().add(lblIDUsuario);
		
		btnCadastro = new JButton("Cadastre-se");
		btnCadastro.setBounds(747, 314, 171, 31);
		btnCadastro.setBackground(new Color(236, 236, 255));
		btnCadastro.setFont(new Font("Tahoma", Font.PLAIN, 18));
		frmFeedSmaicc.getContentPane().add(btnCadastro);
		btnCadastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastro.getFrmCadastroSmaicc().setVisible(true);
			}
		});
		
		btnLogin = new JButton("Login");
		btnLogin.setBounds(747, 273, 171, 31);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnLogin.setBackground(new Color(236, 236, 255));
		frmFeedSmaicc.getContentPane().add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login.getFrmLoginSmaicc().setVisible(true);
				
			}
		});
		
		btnAltDados = new JButton("Alterar Dados");
		btnAltDados.setBounds(747, 232, 171, 31);
		btnAltDados.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAltDados.setBackground(new Color(236, 236, 255));
		frmFeedSmaicc.getContentPane().add(btnAltDados);
		btnAltDados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				altDados.getFrmAtualizaoCadastral().setVisible(true);
			}
		});
		
		btnReport = new JButton("Reportar Incidente");
		btnReport.setBounds(747, 314, 171, 31);
		btnReport.setBackground(new Color(236, 236, 255));
		btnReport.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmFeedSmaicc.getContentPane().add(btnReport);
		btnReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadInc.getFrmCadastrarIncidente().setVisible(true);
			}
		});
		
		btnLogout = new JButton("Logout");
		btnLogout.setBounds(747, 273, 171, 31);
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnLogout.setBackground(new Color(236, 236, 255));
		frmFeedSmaicc.getContentPane().add(btnLogout);
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logout.getFrmLogout().setVisible(true);
			}
		});
		
		btnRemoverIncidente = new JButton("Remover Incidente");
		btnRemoverIncidente.setBounds(747, 396, 171, 31);
		btnRemoverIncidente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRemoverIncidente.setBackground(new Color(236, 236, 255));
		frmFeedSmaicc.getContentPane().add(btnRemoverIncidente);
		btnRemoverIncidente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirInc.getFrmExcluirIncidente().setVisible(true);
			}
		});
		
		btnSeusIncidentes = new JButton("Seus Incidentes");
		btnSeusIncidentes.setBounds(747, 355, 171, 31);
		btnSeusIncidentes.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnSeusIncidentes.setBackground(new Color(236, 236, 255));
		frmFeedSmaicc.getContentPane().add(btnSeusIncidentes);
		
		
		btnSeusIncidentes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ControleSessao.receberToken().length() == 20) {
					//textAreaFeed.setText("");
					DefaultTableModel tableModel = (DefaultTableModel) tableIncidentes.getModel();
					tableModel.setRowCount(0);
					JSONObject objetoJSON = new JSONObject();
					objetoJSON.put("operacao", 5);
					objetoJSON.put("id", ControleSessao.receberId());
					objetoJSON.put("token", ControleSessao.receberToken());
					try {		
						System.out.println("\tLISTAR SEUS INCIDENTES REPORTADOS\nEnviando mensagem do CLIENTE: " + objetoJSON + "\n pela porta: " + TelaClientePorta.portaCliente +" utilizando o IP: "+TelaClientePorta.ipCliente);
						JSONObject respostaServidor = ClienteECHO.conectarServidor(objetoJSON);
						System.out.println("\tLISTAR SEUS INCIDENTES REPORTADOS\nRecebendo mensagem do SERVIDOR: "+respostaServidor);
						String status = respostaServidor.getString("status");
						
						if(status.equals("OK")) {
							JSONArray jsonArrayIncidentes = (JSONArray) respostaServidor.get("incidentes");
							if(jsonArrayIncidentes.isEmpty()) {
								//textAreaFeed.setText("\tNENHUM INCIDENTE FOI ENCONTRADO!");
								JOptionPane.showMessageDialog(frmFeedSmaicc, "Nenhum Incidente foi encontrado", "Resultado de Busca", JOptionPane.INFORMATION_MESSAGE);
							}else {
								//textAreaFeed.setText("\tSEUS INCIDENTES REPORTADOS");
								for (Object obj : jsonArrayIncidentes) {
									String nomeTipoIncidente = "";
								    JSONObject incidente = (JSONObject) obj;
								    int tipoIncidente = incidente.getInt("tipo_incidente");
								    String dataIncidente = incidente.getString("data");
								    String horaIncidente = incidente.getString("hora");
								    String cidadeIncidente = incidente.getString("cidade");
								    String bairroIncidente = incidente.getString("bairro");
								    String estadoIncidente = incidente.getString("estado");
								    String ruaIncidente = incidente.getString("rua");
								    int idIncidente = incidente.getInt("id_incidente");
								    switch(tipoIncidente) {
									case 0:
										nomeTipoIncidente = "Sem Incidentes";
										break;
									case 1:
										nomeTipoIncidente = "Alagamento";
										break;
									case 2:
										nomeTipoIncidente = "Deslizamento";
										break;
									case 3:
										nomeTipoIncidente = "Acidente de carro";
										break;
									case 4:
										nomeTipoIncidente = "Obstrução da via";
										break;
									case 5:
										nomeTipoIncidente = "Fissura da via";
										break;
									case 6:
										nomeTipoIncidente = "Pista em obras";
										break;
									case 7:
										nomeTipoIncidente = "Lentidão na pista";
										break;
									case 8:
										nomeTipoIncidente = "Animais na pista";
										break;
									case 9:
										nomeTipoIncidente = "Nevoeiro";
										break;
									case 10:
										nomeTipoIncidente = "Tromba d'água";
										break;
								}
								    //textAreaFeed.append("\n\tData: "+dataIncidente+" Hora: "+horaIncidente+" ID: "+idIncidente+" Tipo de Incidente: "+nomeTipoIncidente+"\n\tCidade: "+cidadeIncidente+" - "+estadoIncidente+" Rua: "+ruaIncidente+" Bairro: "+bairroIncidente);
								    //textAreaFeed.append("\n-----------------------------------------------------------------------------------------------------------------------------------------\n");
								    Object[] novaLinha = {idIncidente, nomeTipoIncidente, dataIncidente, horaIncidente, cidadeIncidente, estadoIncidente, ruaIncidente, bairroIncidente};
								    tableModel.addRow(novaLinha);
								}
								
							}
						}else {
							JOptionPane.showMessageDialog(frmFeedSmaicc, status, "Erro", JOptionPane.ERROR_MESSAGE);
							//textAreaFeed.setText("");
						}
							
					}catch(Exception ex) {
						JOptionPane.showMessageDialog(frmFeedSmaicc, "Algo deu errado durante a busca !", "Erro", JOptionPane.ERROR_MESSAGE);
						System.out.println("Erro:"+ex);
					}
				}
			}
		});
		
		lblNomeUser.setVisible(false);
		lblIDUsuario.setVisible(false);
		btnAltDados.setVisible(false);
		btnReport.setVisible(false);
		btnLogout.setVisible(false);
		btnSeusIncidentes.setVisible(false);
		btnRemoverIncidente.setVisible(false);
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
			btnSeusIncidentes.setVisible(false);
			btnRemoverIncidente.setVisible(false);
			
		}else if(state == true && tokenUser.length() == 20){
			lblNomeUser.setText("User: "+nomeUser);
			lblIDUsuario.setText("ID: "+idUserStr);
			lblNomeUser.setVisible(true);
			lblIDUsuario.setVisible(true);
			btnAltDados.setVisible(true);
			btnReport.setVisible(true);
			btnLogout.setVisible(true);
			btnSeusIncidentes.setVisible(true);
			btnRemoverIncidente.setVisible(true);
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
