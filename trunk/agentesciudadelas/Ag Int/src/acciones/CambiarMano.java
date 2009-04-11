package acciones;

import jade.content.AgentAction;
import conceptos.Jugador;

public class CambiarMano implements AgentAction {

	private Jugador jugador1;
	private Jugador jugador2;
	
	public Jugador getJugador1() {
		return jugador1;
	}
	public void setJugador1(Jugador jugador1) {
		this.jugador1 = jugador1;
	}
	public Jugador getJugador2() {
		return jugador2;
	}
	public void setJugador2(Jugador jugador2) {
		this.jugador2 = jugador2;
	}

}
