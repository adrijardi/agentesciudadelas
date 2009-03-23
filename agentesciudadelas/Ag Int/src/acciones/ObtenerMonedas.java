package acciones;

import conceptos.Jugador;
import conceptos.Personaje;
import jade.content.AgentAction;

public class ObtenerMonedas implements AgentAction {

	private Jugador jugador;

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
	
}
