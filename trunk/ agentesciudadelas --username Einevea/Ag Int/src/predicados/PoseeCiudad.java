package predicados;

import conceptos.Jugador;
import jade.content.Predicate;

public class PoseeCiudad implements Predicate {
	
	private Jugador jugador;
	//private Distrito distrito; debe ser una lista de distritos

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
	
}