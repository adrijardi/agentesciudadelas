package acciones;

import jade.content.AgentAction;
import conceptos.Distrito;
import conceptos.Jugador;
import conceptos.Personaje;

public class PedirConstruirDistrito implements AgentAction {

	/*
	 * Mensaje de los agentes jugadores
	 */
	private Jugador jugador;
	private Personaje personaje;
	private Distrito distrito;
	
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
	public Distrito getDistrito() {
		return distrito;
	}
	public void setDistrito(Distrito distrito) {
		this.distrito = distrito;
	}

}