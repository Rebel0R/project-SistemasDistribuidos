package interfaceSMAICC;
import screen.TelaClientePorta;

public class SistemaInterfaceUI {
	private TelaClientePorta clientPort = new TelaClientePorta();	
	
	
	public static void main(String[] args) {
		SistemaInterfaceUI sistUI = new SistemaInterfaceUI();
		sistUI.clientPort.getFramePort().setVisible(true);
		
	}
}
