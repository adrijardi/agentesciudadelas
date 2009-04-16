package comportamientos_jugador;

import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jugador.AgJugador;
import utils.Filtros;
import acciones.CobrarDistritosCondotierro;
import acciones.DarMonedas;

public class PedirCobrarCondotierro extends Behaviour {

	private final AgJugador _agj;
	private final Behaviour beh;
	private final AID raid;

	public PedirCobrarCondotierro(AgJugador agj, Behaviour ft, AID aid) {
		_agj = agj;
		beh = ft;
		raid = aid;
	}
	
	@Override
	public void action() {
		CobrarDistritosCondotierro cobrarCondotierro = new CobrarDistritosCondotierro();
		cobrarCondotierro.setJugador(_agj.getJugador());
		
		_agj.sendMSG(ACLMessage.REQUEST, raid, cobrarCondotierro, Filtros.COBRARDISTRITOSCONDOTIERRO);
		
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
