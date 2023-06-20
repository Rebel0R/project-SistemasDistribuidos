package entity;

public class Incidente {
	private int tipoIncidente;
	private String data;
	private String hora;
	private String cidade;
	private String bairro;
	private String rua;
	private String estado;
	private String token;
	private int idUsuario;
	
	private int id = -1;
	
	public Incidente(int tipoIncidente, String data, String hora, String cidade, String bairro, String rua,
			String estado, String token, int idUsuario) {
		this.tipoIncidente = tipoIncidente;
		this.data = data;
		this.hora = hora;
		this.cidade = cidade;
		this.bairro = bairro;
		this.rua = rua;
		this.estado = estado;
		this.token = token;
		this.idUsuario = idUsuario;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getTipoIncidente() {
		return tipoIncidente;
	}
	public String getData() {
		return data;
	}
	public String getHora() {
		return hora;
	}
	public String getCidade() {
		return cidade;
	}
	public String getBairro() {
		return bairro;
	}
	public String getRua() {
		return rua;
	}
	public String getEstado() {
		return estado;
	}
	public String getToken() {
		return token;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	@Override
	public String toString() {
		return "Incidente [tipoIncidente=" + tipoIncidente + ", data=" + data + ", hora=" + hora + ", cidade=" + cidade
				+ ", bairro=" + bairro + ", rua=" + rua + ", estado=" + estado + ", token=" + token + ", idUsuario="
				+ idUsuario + "]";
	}

	

}
