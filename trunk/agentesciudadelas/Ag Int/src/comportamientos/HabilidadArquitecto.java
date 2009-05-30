package comportamientos;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import tablero.AgTablero;
import tablero.EstadoPartida;
import tablero.Mazo;
import tablero.ResumenJugador;
import utils.Filtros;
import acciones.DarDistritos;
import conceptos.Distrito;

public class HabilidadArquitecto extends Behaviour {

	private final AgTablero agt;

	public HabilidadArquitecto(AgTablero agTablero) {
		agt = agTablero;
	}

	/*
	 * se ha supuesto que es el propio agente quien no roba a quien han matado
	 * pero no estaria de mas controlarlo
	 */
	@Override
	public void action() {
		EstadoPartida ep = EstadoPartida.getInstance();
		ResumenJugador jugador = ep.getJugActual();

		// Se obtienen los distritos que seleccionara el jugador
		Mazo ma = Mazo.getInstance();
		Distrito[] lista = ma.getDistritos(2);

		// Se le mandan los distritos al jugador
		DarDistritos obj = new DarDistritos();
		obj.setDistritos(lista);
		agt.sendMSG(ACLMessage.INFORM, jugador, obj, Filtros.DARDISTRITOSARQUITECTO);

	}

	@Override
	public boolean done() {
		return true;
	}
}