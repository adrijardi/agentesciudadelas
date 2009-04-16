package acciones;

import jade.content.AgentAction;
import conceptos.Distrito;
import conceptos.Jugador;

public class PagarDistrito implements AgentAction {
	private Jugador jugador;
	private Integer cantidad;
	private Distrito distrito;

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

	/*
	 * A�adido por P
	 */
	public Distrito getDistrito() {
		return distrito;
	}

	public void setDistrito(Distrito distrito) {
		this.distrito = distrito;
	}
}
