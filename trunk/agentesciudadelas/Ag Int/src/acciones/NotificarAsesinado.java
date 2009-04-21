package acciones;

import jade.content.AgentAction;
import conceptos.Personaje;

public class NotificarAsesinado implements AgentAction {

	private Personaje personaje;

	public Personaje getPersonaje() {
		return personaje;
	}

	public void setPersonaje(Personaje personaje) {
		this.personaje = personaje;
	}
	
	@Override
	public String toString() {
		return "NotificarAsesinado: "+ personaje.getNombre();
	}

}