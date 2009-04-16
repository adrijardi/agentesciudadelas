package comportamientos_jugador;

import utils.Filtros;
import acciones.CobrarDistritosRey;
import acciones.DarMonedas;
import acciones.Matar;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
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
		
		int monedas = 0;
		ACLMessage msg = _agj.reciveBlockingMessage(Filtros.DARMONEDAS, false);
		try {
			DarMonedas contenido = (DarMonedas) _agj.getContentManager().extractContent(msg);
			monedas += contenido.getMonedas().intValue();
			_agj.addMonedas(monedas);
			
		} catch (UngroundedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CodecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OntologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean done() {
		_agj.addBehaviour(beh);
		return true;
	}

}
