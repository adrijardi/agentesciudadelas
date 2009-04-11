package acciones;

import jade.content.AgentAction;
import conceptos.Personaje;

public class DescartarPublicoPersonajes implements AgentAction {

	private Personaje personaje1;
	private Personaje personaje2;
	
	public Personaje getPersonaje1() {
		return personaje1;
	}
	public void setPersonaje1(Personaje personaje1) {
		this.personaje1 = personaje1;
	}
	public Personaje getPersonaje2() {
		return personaje2;
	}
	public void setPersonaje2(Personaje personaje2) {
		this.personaje2 = personaje2;
	}
	
}
