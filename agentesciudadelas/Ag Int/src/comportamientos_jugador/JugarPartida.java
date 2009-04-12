package comportamientos_jugador;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jugador.AgJugador;
import utils.Filtros;

public class JugarPartida extends Behaviour {
	
	private final AgJugador _agj;

	public JugarPartida(AgJugador agj) {
		_agj = agj;
	}

	@Override
	public void action() {
		//Se espera a que el tablero nos entrege el turno
		ACLMessage msg = _agj.reciveBlockingMessage(Filtros.NOTIFICARTURNO, false);
		
		//Se realizan las acciones definidas por el agente
		_agj.addBehaviour(_agj.jugarTurno(msg));
	}

	@Override
	public boolean done() {
		return true;
	}

}
