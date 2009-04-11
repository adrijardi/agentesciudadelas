package predicados;

import jade.content.Predicate;
import conceptos.Jugador;
import conceptos.Personaje;

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
