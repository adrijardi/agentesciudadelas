package comportamientos_jugador;

import acciones.DarMonedas;
import acciones.OfertarPersonajes;
import utils.Filtros;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jugador.AgJugador;

public class RecibirMonedas extends Behaviour {
	
	private final AgJugador _agj;

	public RecibirMonedas(AgJugador agj) {
		_agj = agj;
	}

	@Override
	public void action() {
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
		return true;
	}

}
