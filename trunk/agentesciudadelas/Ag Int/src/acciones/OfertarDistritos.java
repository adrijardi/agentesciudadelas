package acciones;

import jade.content.AgentAction;
import conceptos.Distrito;

public class OfertarDistritos implements AgentAction {

	private Distrito distrito1;
	private Distrito distrito2;
	
	public Distrito getDistrito1() {
		return distrito1;
	}
	public void setDistrito1(Distrito distrito1) {
		this.distrito1 = distrito1;
	}
	public Distrito getDistrito2() {
		return distrito2;
	}
	public void setDistrito2(Distrito distrito2) {
		this.distrito2 = distrito2;
	}
	
}
