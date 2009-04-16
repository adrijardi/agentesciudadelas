package comportamientos_jugador;

import utils.Filtros;
import acciones.CobrarDistritosRey;
import acciones.Matar;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jugador.AgJugador;

public class PedirCobrarRey extends Behaviour {

	private final AgJugador _agj;
	private final Behaviour beh;
	private final AID raid;

	public PedirCobrarRey(AgJugador agj, Behaviour ft, AID aid) {
		_agj = agj;
		beh = ft;
		raid = aid;
	}
	
	@Override
	public void action() {
		CobrarDistritosRey cobrarRey = new CobrarDistritosRey();
		cobrarRey.setJugador(_agj.getJugador());
		
		_agj.sendMSG(ACLMessage.REQUEST, raid, cobrarRey, Filtros.COBRARDISTRITOSREY);
	}

	@Override
	public boolean done() {
		_agj.addBehaviour(beh);
		return true;
	}

}
