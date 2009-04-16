package acciones;

import jade.content.AgentAction;
import conceptos.Jugador;
import conceptos.Personaje;

public class ElegirPersonaje implements AgentAction {

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
	
	public String toString(){
		StringBuilder ret = new StringBuilder();
		ret.append("ElegirPersonaje: ");
		ret.append(personaje.getNombre());
		return ret.toString();
	}

}
