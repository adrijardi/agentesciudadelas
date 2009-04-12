package comportamientos_jugador;

import conceptos.Distrito;
import utils.Filtros;
import acciones.DarMonedas;
import acciones.ObtenerMonedas;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jugador.AgJugador;

public class ConstruirDistrito extends Behaviour {
	
	private final AgJugador _agj;
	private final Behaviour beh;
	private final AID raid;

	public ConstruirDistrito(AgJugador agj, Behaviour ft, AID aid, Distrito distrito) {
		_agj = agj;
		beh = ft;
		raid = aid;
	}

	@Override
	public void action() {
		/*ObtenerMonedas om = new ObtenerMonedas();
		om.setJugador(_agj.getJugador());
		_agj.sendMSG(ACLMessage.REQUEST, raid, om, Filtros.ACCION_JUGADOR);
		System.out.println("Mandado petición, esperando Monedas");
		ACLMessage msg = _agj.reciveBlockingMessage(Filtros.DARMONEDAS, true);
		try {
			DarMonedas contenido =(DarMonedas) _agj.getContentManager().extractContent(msg);
			_agj.addMonedas(contenido.getMonedas().intValue());
		} catch (UngroundedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CodecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OntologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	@Override
	public boolean done() {
		_agj.addBehaviour(beh);
		return true;
	}

}
