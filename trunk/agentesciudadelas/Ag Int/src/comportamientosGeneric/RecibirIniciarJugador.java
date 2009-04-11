package comportamientosGeneric;

import jade.core.behaviours.Behaviour;
import jugador.AgJugador;

public class RecibirIniciarJugador extends Behaviour {

	private final AgJugador _agj;

	public RecibirIniciarJugador(AgJugador agJugador) {
		_agj = agJugador;
	}
	
	@Override
	public void action() {
		//TODO Hay que obtener los mensajes de cartas y de monedas (utilizar las funciones de recepcion de mensajes definidas en el agente
	}

	@Override
	public boolean done() {
		_agj.addBehaviour(new ElegirPersonajeJugador(_agj));
		return true;
	}

}
