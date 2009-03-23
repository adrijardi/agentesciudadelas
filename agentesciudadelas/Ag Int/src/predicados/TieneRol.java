package predicados;

import conceptos.Jugador;
import conceptos.Personaje;
import jade.content.Predicate;

public class TieneRol implements Predicate {
	
	private Jugador jugador;
	private Personaje personaje;
	
	public Jugador getJugador() {
		return jugador;
	}
	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
	public Personaje getPersonaje() {
		return personaje;
	}
	public void setPersonaje(Personaje personaje) {
		this.personaje = personaje;
	}

}
