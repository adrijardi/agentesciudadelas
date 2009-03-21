package acciones;

import conceptos.Personaje;
import jade.content.AgentAction;

public class Robar implements AgentAction {

	private Personaje personaje;

	public Personaje getPersonaje() {
		return personaje;
	}

	public void setPersonaje(Personaje personaje) {
		this.personaje = personaje;
	}
	
}