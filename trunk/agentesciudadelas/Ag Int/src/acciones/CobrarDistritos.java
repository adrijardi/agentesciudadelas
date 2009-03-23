package acciones;

import conceptos.Jugador;
import conceptos.Distrito;
import jade.content.AgentAction;

public class CobrarDistritos implements AgentAction {
 
	private Jugador jugador;
	private Integer cantidad;
	
	/*
	 * a–adido por Pablo
	 */
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
	 * A–adido por P
	 */
	public Distrito getDistrito() {
		return distrito;
	}
	public void setDistrito(Distrito distrito) {
		this.distrito = distrito;
	}
	
}
