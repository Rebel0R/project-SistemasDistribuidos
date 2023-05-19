package screen;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import org.json.JSONObject;
import services.ControleSessao;
import services.ValidaDados;
import servidorECHO.ClienteECHO;

import javax.swing.JButton;

public class TelaCadastroIncidente {

	private JFrame frmCadastrarIncidente;
	private JTextField textFieldRua;
	private JTextField textFieldBairro;
	private JTextField textFieldCidade;
	private JTextField textFieldData;
	private JTextField textFieldHora;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadastroIncidente window = new TelaCadastroIncidente();
					window.frmCadastrarIncidente.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaCadastroIncidente() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCadastrarIncidente = new JFrame();
		frmCadastrarIncidente.getContentPane().setBackground(new Color(0, 0, 128));
		frmCadastrarIncidente.setTitle("Reportar Incidente");
		frmCadastrarIncidente.setBounds(100, 100, 485, 492);
		frmCadastrarIncidente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCadastrarIncidente.getContentPane().setLayout(null);
		
		JLabel lblCadastroDeIncidentes = new JLabel("REPORTAR INCIDENTES");
		lblCadastroDeIncidentes.setForeground(new Color(100, 149, 237));
		lblCadastroDeIncidentes.setFont(new Font("Verdana", Font.BOLD, 21));
		lblCadastroDeIncidentes.setBounds(91, 28, 287, 26);
		frmCadastrarIncidente.getContentPane().add(lblCadastroDeIncidentes);
		
		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setForeground(new Color(240, 248, 255));
		lblCidade.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCidade.setBounds(91, 64, 130, 26);
		frmCadastrarIncidente.getContentPane().add(lblCidade);
		
		textFieldCidade = new JTextField();
		textFieldCidade.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldCidade.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textFieldCidade.setColumns(10);
		textFieldCidade.setBounds(91, 90, 188, 26);
		frmCadastrarIncidente.getContentPane().add(textFieldCidade);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setForeground(new Color(240, 248, 255));
		lblEstado.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEstado.setBounds(298, 64, 130, 26);
		frmCadastrarIncidente.getContentPane().add(lblEstado);
		
		String[] estados = {"Estados","AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(estados);
		JComboBox<String> comboBoxEstados = new JComboBox<>(model);
		comboBoxEstados.setBackground(new Color(255, 255, 255));
		comboBoxEstados.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		comboBoxEstados.setBounds(298, 89, 70, 26);
		frmCadastrarIncidente.getContentPane().add(comboBoxEstados);
		
		JLabel lblRua = new JLabel("Rua:");
		lblRua.setForeground(new Color(240, 248, 255));
		lblRua.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRua.setBounds(91, 123, 130, 26);
		frmCadastrarIncidente.getContentPane().add(lblRua);
		
		textFieldRua = new JTextField();
		textFieldRua.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldRua.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textFieldRua.setColumns(10);
		textFieldRua.setBounds(91, 149, 277, 26);
		frmCadastrarIncidente.getContentPane().add(textFieldRua);
		
		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setForeground(new Color(240, 248, 255));
		lblBairro.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblBairro.setBounds(91, 185, 130, 26);
		frmCadastrarIncidente.getContentPane().add(lblBairro);
		
		textFieldBairro = new JTextField();
		textFieldBairro.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldBairro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textFieldBairro.setColumns(10);
		textFieldBairro.setBounds(91, 211, 277, 26);
		frmCadastrarIncidente.getContentPane().add(textFieldBairro);
		
		JLabel lblData = new JLabel("Data (yyyy-mm-dd):");
		lblData.setForeground(new Color(240, 248, 255));
		lblData.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblData.setBounds(91, 247, 143, 26);
		frmCadastrarIncidente.getContentPane().add(lblData);
		
		textFieldData = new JTextField();
		textFieldData.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldData.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textFieldData.setColumns(10);
		textFieldData.setBounds(91, 273, 143, 26);
		frmCadastrarIncidente.getContentPane().add(textFieldData);
		
		
		JLabel lblHora = new JLabel("Hora (hh:mm):");
		lblHora.setForeground(new Color(240, 248, 255));
		lblHora.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblHora.setBounds(257, 247, 130, 26);
		frmCadastrarIncidente.getContentPane().add(lblHora);
		
		textFieldHora = new JTextField();
		textFieldHora.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldHora.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textFieldHora.setColumns(10);
		textFieldHora.setBounds(257, 273, 112, 26);
		frmCadastrarIncidente.getContentPane().add(textFieldHora);
		
		JLabel lblIncidentes = new JLabel("Incidentes:");
		lblIncidentes.setForeground(new Color(240, 248, 255));
		lblIncidentes.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblIncidentes.setBounds(91, 309, 130, 26);
		frmCadastrarIncidente.getContentPane().add(lblIncidentes);
		
		String[] tiposIncidentes = {"Incidentes","Alagamento", "Deslizamento", "Acidente de carro", "Obstrução da via", "Fissura da via", "Pista em obras", "Lentidão na pista", "Animais na pista", "Nevoeiro", "Tromba d'água"};
		DefaultComboBoxModel<String> modelIncidentes = new DefaultComboBoxModel<>(tiposIncidentes);
		JComboBox<String> comboBoxIncidentes = new JComboBox<String>(modelIncidentes);
		comboBoxIncidentes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboBoxIncidentes.setBackground(Color.WHITE);
		comboBoxIncidentes.setBounds(91, 334, 158, 26);
		frmCadastrarIncidente.getContentPane().add(comboBoxIncidentes);
		
		JButton btnReportar = new JButton("Reportar");
		btnReportar.setForeground(Color.WHITE);
		btnReportar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnReportar.setBackground(new Color(102, 153, 255));
		btnReportar.setBounds(107, 384, 122, 26);
		frmCadastrarIncidente.getContentPane().add(btnReportar);
		btnReportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JSONObject objetoJSON = new JSONObject();
				String uf = (String)comboBoxEstados.getSelectedItem();
				String cidade = textFieldCidade.getText();
				cidade = cidade.toUpperCase();
				String rua = textFieldRua.getText();
				rua = rua.toUpperCase();
				String bairro = textFieldBairro.getText();
				bairro = bairro.toUpperCase();
				String data = textFieldData.getText();
				String hora = textFieldHora.getText();
				String incidente = (String)comboBoxIncidentes.getSelectedItem();
				
				if(ValidaDados.validarCadastroIncidente(cidade, uf, rua, bairro, data, hora, incidente)) {
					System.out.println("\tREPORTAR INCIDENTE\nDados válidos!");
					try {		
						objetoJSON.put("operacao", 7);
						objetoJSON.put("data", data);
						objetoJSON.put("hora", hora);
						objetoJSON.put("estado", uf);
						objetoJSON.put("cidade", cidade);
						objetoJSON.put("bairro", bairro);
						objetoJSON.put("rua", rua);
						objetoJSON.put("token", ControleSessao.receberToken());
						objetoJSON.put("id_user", ControleSessao.receberId());
						objetoJSON.put("tipo_incidente", incidente);
		
						System.out.println("\tREPORTAR INCIDENTE\nEnviando mensagem do CLIENTE: " + objetoJSON + "\n pela porta: " + TelaClientePorta.portaCliente +" utilizando o IP: "+TelaClientePorta.ipCliente);
						JSONObject respostaServidor = ClienteECHO.conectarServidor(objetoJSON);
						System.out.println("\tREPORTAR INCIDENTE\nRecebendo mensagem do SERVIDOR: "+respostaServidor);
						
						String status = respostaServidor.getString("status");
						
						if(status.equals("OK")) {
							System.out.println("Tela Cadastro: "+respostaServidor);
							JOptionPane.showMessageDialog(frmCadastrarIncidente, "Reportar Incidente", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
							frmCadastrarIncidente.dispose();
						}else {
							JOptionPane.showMessageDialog(frmCadastrarIncidente, "Algo deu errado durante o processo de reporte!", "Erro", JOptionPane.ERROR_MESSAGE);
						}
							
					}catch(Exception ex) {
						System.out.println("Erro:"+ex);
					}
				}else {
					if(!ValidaDados.validarBairro(bairro) || !ValidaDados.validarCidade(cidade) || !ValidaDados.validarRua(rua)) {
						JOptionPane.showMessageDialog(frmCadastrarIncidente, "Localização inválida, não utilize acentos ou 'ç'!\nUtilize no máximo 50 caracteres.", "Erro", JOptionPane.ERROR_MESSAGE);
					}
					if(!ValidaDados.validarData(data) || !ValidaDados.validarHora(hora)) {
						JOptionPane.showMessageDialog(frmCadastrarIncidente, "Data ou hora inválida, utilize o padrão 'yyyy-mm-dd' para datas e 'hh:mm' para hora", "Erro", JOptionPane.ERROR_MESSAGE);
					}
					if(!ValidaDados.validarIncidente(incidente)) {
						JOptionPane.showMessageDialog(frmCadastrarIncidente, "Incidente inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
					}
					if(!ValidaDados.validarUF(uf)) {
						JOptionPane.showMessageDialog(frmCadastrarIncidente, "Estado inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
					}
				}
				textFieldCidade.setText("");
				textFieldRua.setText("");
				textFieldBairro.setText("");
				textFieldData.setText("");
				textFieldHora.setText("");
				
			}
		});
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setForeground(new Color(102, 153, 255));
		btnVoltar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnVoltar.setBackground(Color.WHITE);
		btnVoltar.setBounds(242, 384, 122, 26);
		frmCadastrarIncidente.getContentPane().add(btnVoltar);
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmCadastrarIncidente.dispose();
			}
		});

	}

	public JFrame getFrmCadastrarIncidente() {
		return frmCadastrarIncidente;
	}
}
