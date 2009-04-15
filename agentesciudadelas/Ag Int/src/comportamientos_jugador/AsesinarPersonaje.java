package comportamientos_jugador;

import utils.Filtros;
import acciones.Matar;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jugador.AgJugador;

public class AsesinarPersonaje extends Behaviour {

	private final AgJugador _agj;
	private final Behaviour beh;
	private final AID raid;

	public AsesinarPersonaje(AgJugador agj, Behaviour ft, AID aid) {
		_agj = agj;
		beh = ft;
		raid = aid;
	}
	
	@Override
	public void action() {
		Matar matar = new Matar();
		matar.setPersonaje(_agj.getPersonajeMatar());
		
		_agj.sendMSG(ACLMessage.REQUEST, raid, matar, Filtros.MATAR);

		// TODO no hay que leer nada digo yo
		
	}

	@Override
	public boolean done() {
		_agj.addBehaviour(beh);
		return true;
	}

}
