package comportamientos;

import java.util.LinkedList;

import tablero.AgTablero;
import tablero.EstadoPartida;
import utils.Filtros;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class FinalizarTurno extends Behaviour {

	private final AgTablero agt;
	private final LinkedList<Behaviour> llb;
	private final EstadoPartida ep =  EstadoPartida.getInstance();
	
	public FinalizarTurno(AgTablero agt, LinkedList<Behaviour> llb) {
		this.agt = agt;
		this.llb = llb;
	}

	@Override
	public void action() {
		// Se espera la recepcion de fin de de turno
		System.out.println("Tablero esperando a fin de turno");
		ACLMessage msg = agt.reciveBlockingMessageFrom(Filtros.NOTIFICARFINTURNOJUGADOR, ep.getJugActual());
		// TODO hay que comprobar algo del mensaje?
		for (Behaviour behaviour : llb) {
			agt.removeBehaviour(behaviour);
		}

	}

	@Override
	public boolean done() {
		ep.nextJugadorPorTurnoPersonaje();
		switch (ep.getFase()) {
		case SEL_PERSONAJES:
			agt.addBehaviour(new NotificarDescartado(agt));
			break;

		case FINALIZAR_JUEGO:
			break;
			
		case JUGAR_RONDA:
			agt.addBehaviour(new JugarPersonaje(agt));
			break;

		}
		return true;
	}

}
