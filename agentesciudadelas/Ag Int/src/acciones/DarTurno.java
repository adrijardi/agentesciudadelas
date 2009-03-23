package acciones;

import conceptos.Jugador;
import conceptos.Personaje;
import jade.content.AgentAction;

public class DarTurno implements AgentAction {

	private Jugador jugador;
	private boolean muerto;
	private boolean robado;
	
	public Jugador getJugador() {
		return jugador;
	}
	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
	public boolean isMuerto() {
		return muerto;
	}
	public void setMuerto(boolean muerto) {
		this.muerto = muerto;
	}
	public boolean isRobado() {
		return robado;
	}
	public void setRobado(boolean robado) {
		this.robado = robado;
	}

}

