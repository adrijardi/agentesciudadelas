package comportamientos_jugador;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jugador.AgJugador;
import utils.Filtros;
import acciones.CobrarDistritosMercader;

public class PedirCobrarMercader extends Behaviour {

	private final AgJugador _agj;
	private final Behaviour beh;
	private final AID raid;

	public PedirCobrarMercader(AgJugador agj, Behaviour ft, AID aid) {
		_agj = agj;
		beh = ft;
		raid = aid;
	}
	
	@Override
	public void action() {
		CobrarDistritosMercader cobrarMercader = new CobrarDistritosMercader();
		cobrarMercader.setJugador(_agj.getJugador());
		
		_agj.sendMSG(ACLMessage.REQUEST, raid, cobrarMercader, Filtros.COBRARDISTRITOSMERCADER);
	}

	@Override
	public boolean done() {
		_agj.addBehaviour(beh);
		return true;
	}

}
