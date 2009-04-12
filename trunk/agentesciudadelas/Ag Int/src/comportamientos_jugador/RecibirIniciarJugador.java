package comportamientos_jugador;

import utils.Filtros;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jugador.AgJugador;

public class RecibirIniciarJugador extends Behaviour {

	private final AgJugador _agj;

	public RecibirIniciarJugador(AgJugador agJugador) {
		_agj = agJugador;
	}
	
	@Override
	public void action() {
		// Se obtinen las cartas y monedas iniciales
		_agj.addBehaviour(new RecibirDistritos(_agj));
		_agj.addBehaviour(new RecibirMonedas(_agj));
	}

	@Override
	public boolean done() {
		_agj.addBehaviour(new ElegirPersonajeJugador(_agj));
		return true;
	}

}
