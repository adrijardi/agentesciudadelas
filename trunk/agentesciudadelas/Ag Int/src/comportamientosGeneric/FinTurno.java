package comportamientosGeneric;

import utils.Filtros;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jugador.AgJugador;
import jugador.JugadorDani;

public class FinTurno extends Behaviour {
	
	private final AgJugador _agj;
	private final AID _aid;

	public FinTurno(AgJugador agj, AID aid) {
		_agj = agj;
		_aid = aid;
	}

	@Override
	public void action() {
		//Se notifica el fin de turno
		_agj.sendMSG(ACLMessage.CONFIRM, _aid, _agj.getNotificarFinTurnoJugador(),Filtros.NOTIFICARFINTURNOJUGADOR);

	}

	@Override
	public boolean done() {
		//TODO _agj.addBehaviour(new ElegirPersonajeJugador(_agj));
		return true;
	}

}
