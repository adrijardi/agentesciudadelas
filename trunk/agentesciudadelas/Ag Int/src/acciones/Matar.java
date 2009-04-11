package acciones;

import jade.content.AgentAction;
import conceptos.Personaje;

public class Matar implements AgentAction {

	private Personaje personaje;

	public Personaje getPersonaje() {
		return personaje;
	}

	public void setPersonaje(Personaje personaje) {
		this.personaje = personaje;
	}

}
