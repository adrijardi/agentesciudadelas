package comportamientosGeneric;

import acciones.NotificarFinTurnoJugador;
import utils.Filtros;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jugador.AgJugador;

public class JugarPartida extends Behaviour {
	
	private final AgJugador _agj;

	public JugarPartida(AgJugador agj) {
		_agj = agj;
	}

	@Override
	public void action() {
		//Se espera a que el tablero nos entrege el turno
		ACLMessage msg = _agj.reciveBlockingMessage(Filtros.NOTIFICARTURNO);
		
		//Se realizan las acciones definidas por el agente
		NotificarFinTurnoJugador nftj = _agj.jugarTurno(msg);
		
		//Se notifica el fin de turno
		_agj.sendMSG(ACLMessage.CONFIRM, msg.getSender(), nftj,Filtros.NOTIFICARFINTURNOJUGADOR);
	}

	@Override
	public boolean done() {
		//TODO _agj.addBehaviour(new ElegirPersonajeJugador(_agj));
		return true;
	}

}
