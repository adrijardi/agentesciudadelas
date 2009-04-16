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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Matar: ");
		sb.append(personaje);
		return sb.toString();
	}

}
