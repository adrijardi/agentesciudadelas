package acciones;

import jade.content.AgentAction;
import conceptos.Personaje;

public class DescartarPrivadoPersonaje implements AgentAction {

	private Personaje personaje1;

	public Personaje getPersonaje1() {
		return personaje1;
	}

	public void setPersonaje1(Personaje personaje1) {
		this.personaje1 = personaje1;
	}

}
