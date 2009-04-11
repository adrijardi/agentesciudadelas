package acciones;

import jade.content.AgentAction;
import conceptos.Jugador;

public class ObtenerMonedas implements AgentAction {

	private Jugador jugador;

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
	
}
