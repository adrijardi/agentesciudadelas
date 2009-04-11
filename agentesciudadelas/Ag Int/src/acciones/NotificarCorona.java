package acciones;

import jade.content.AgentAction;
import conceptos.Jugador;

public class NotificarCorona implements AgentAction {

	private Jugador jugador;

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
	
	@Override
	public String toString() {
		return "NotificarCorona: "+ jugador.getNombre();
	}

}