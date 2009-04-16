package acciones;

import jade.content.AgentAction;
import conceptos.Jugador;

public class DarTurno implements AgentAction {

	private Jugador jugador;
	private Boolean muerto;
	private Boolean robado;
	
	public Jugador getJugador() {
		return jugador;
	}
	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
	public Boolean getMuerto() {
		return muerto;
	}
	public void setMuerto(Boolean muerto) {
		this.muerto = muerto;
	}
	public Boolean getRobado() {
		return robado;
	}
	public void setRobado(Boolean robado) {
		this.robado = robado;
	}

}

