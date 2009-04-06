package comportamientosGeneric;

import tablero.EstadoPartida;
import utils.Filtros;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ElegirPersonaje extends Behaviour {

	@Override
	public void action() {
		block();
		/*
		 * a la espera de q llege el mensaje del agente tablero
		 */
		EstadoPartida ep = EstadoPartida.getInstance();
		MessageTemplate filtroIdentificador = MessageTemplate.MatchConversationId(Filtros.ELEGIR_PERSONAJE);
		MessageTemplate filtroEmisor = MessageTemplate.MatchSender(ep.getResJugadorActual().getIdentificador());
		MessageTemplate plantilla = MessageTemplate.and(filtroEmisor, filtroIdentificador);
		ACLMessage msg = myAgent.receive(plantilla);
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}

}
