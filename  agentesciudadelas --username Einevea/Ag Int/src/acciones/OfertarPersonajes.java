package acciones;

import conceptos.Jugador;
import conceptos.Personaje;
import jade.content.AgentAction;

public class OfertarPersonajes implements AgentAction {

	private Jugador jugador;
	//private Personaje personaje; debe ser lista

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

}
