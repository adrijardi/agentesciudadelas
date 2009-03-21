package acciones;

import conceptos.Jugador;
import jade.content.AgentAction;

public class CobrarDistritos implements AgentAction {

	private Jugador jugador;
	private Integer cantidad;
	
	public Jugador getJugador() {
		return jugador;
	}
	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

}
