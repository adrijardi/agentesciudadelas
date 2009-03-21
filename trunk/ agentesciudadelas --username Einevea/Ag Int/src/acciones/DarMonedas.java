package acciones;

import conceptos.Jugador;
import conceptos.Personaje;
import jade.content.AgentAction;

public class DarMonedas implements AgentAction {

	private Integer monedas;

	public Integer getMonedas() {
		return monedas;
	}

	public void setMonedas(Integer monedas) {
		this.monedas = monedas;
	}
	
}