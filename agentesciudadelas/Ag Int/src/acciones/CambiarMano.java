package acciones;

import tablero.ResumenJugador;
import jade.content.AgentAction;
import conceptos.Jugador;

public class CambiarMano implements AgentAction {

	private Jugador jugador;
	
	public Jugador getJugador() {
		return jugador;
	}
	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

}