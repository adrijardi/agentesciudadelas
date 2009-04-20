package acciones;

import jade.content.AgentAction;

public class Monedas implements AgentAction{

	private int dinero;

	public void setDinero(int dinero) {
		this.dinero = dinero;
	}

	public int getDinero() {
		return dinero;
	}	
}