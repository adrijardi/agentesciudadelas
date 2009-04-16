package comportamientos_jugador;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jugador.AgJugador;
import utils.Filtros;
import acciones.CobrarDistritosObispo;

public class PedirCobrarObispo extends Behaviour {

	private final AgJugador _agj;
	private final Behaviour beh;
	private final AID raid;

	public PedirCobrarObispo(AgJugador agj, Behaviour ft, AID aid) {
		_agj = agj;
		beh = ft;
		raid = aid;
	}
	
	@Override
	public void action() {
		CobrarDistritosObispo cobrarObispo = new CobrarDistritosObispo();
		cobrarObispo.setJugador(_agj.getJugador());
		
		_agj.sendMSG(ACLMessage.REQUEST, raid, cobrarObispo, Filtros.COBRARDISTRITOSOBISPO);
	}

	@Override
	public boolean done() {
		_agj.addBehaviour(beh);
		return true;
	}

}
