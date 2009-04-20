package comportamientos_jugador;

import utils.Filtros;
import acciones.Monedas;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jugador.AgJugador;

public class PagarRobo extends Behaviour{
	private final AgJugador _agj;
	private final Behaviour beh;
	private final AID raid;

	public PagarRobo(AgJugador agj, Behaviour ft, AID aid) {
		_agj = agj;
		beh = ft;
		raid = aid;
	}
	
	@Override
	public void action(){
		
		Monedas m=new Monedas();
		m.setDinero(_agj.getMonedas());
		
		_agj.setMonedas(0);
		
		_agj.sendMSG(ACLMessage.REQUEST, null, m, Filtros.PAGARROBO);
	}

	@Override
	public boolean done() {
		_agj.addBehaviour(beh);
		return true;
	}
}