package acciones;

import jade.content.AgentAction;
import conceptos.Distrito;
import conceptos.Jugador;

public class SeleccionarPersonaje implements AgentAction{
	private Integer id_jugador;

	public Integer getId_jugador() {
		return id_jugador;
	}

	public void setId_jugador(Integer id_jugador) {
		this.id_jugador = id_jugador;
	}
	
}
