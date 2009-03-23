package acciones;

import conceptos.Jugador;
import jade.content.AgentAction;

public class PedirDistritosArquitecto implements AgentAction {

	private Jugador jugador;

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

}
