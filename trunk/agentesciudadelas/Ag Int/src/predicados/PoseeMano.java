package predicados;

import jade.content.Predicate;
import conceptos.Jugador;

public class PoseeMano implements Predicate {
	
	private Jugador jugador;
	//private Distrito distrito; debe ser una lista de distritos

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
}
