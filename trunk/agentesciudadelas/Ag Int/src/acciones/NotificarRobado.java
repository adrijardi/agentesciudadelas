package acciones;

import jade.content.AgentAction;
import conceptos.Jugador;
import conceptos.Personaje;

public class NotificarRobado implements AgentAction {

	private Personaje personaje;
	private Jugador jugadorLadron;
	
	public Personaje getPersonaje() {
		return personaje;
	}

	public void setPersonaje(Personaje personaje) {
		this.personaje = personaje;
	}

	public Jugador getJugadorLadron() {
		return jugadorLadron;
	}

	public void setJugadorLadron(Jugador jugadorLadron) {
		this.jugadorLadron = jugadorLadron;
	}

	
}