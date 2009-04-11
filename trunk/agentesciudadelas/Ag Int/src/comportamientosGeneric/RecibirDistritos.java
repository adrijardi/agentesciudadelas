package comportamientosGeneric;

import acciones.DarDistritos;
import acciones.DarMonedas;
import tablero.AgTablero;
import utils.Filtros;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jugador.AgJugador;

public class RecibirDistritos extends Behaviour {
	
	private final AgJugador _agj;

	public RecibirDistritos(AgJugador agj) {
		_agj = agj;
	}

	@Override
	public void action() {
		ACLMessage msg = _agj.reciveBlockingMessage(Filtros.DARDISTRITOS, false);
		try {
			DarDistritos contenido = (DarDistritos) _agj.getContentManager().extractContent(msg);
			_agj.addDistritos(contenido.getDistritos());			
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
		// TODO Auto-generated method stub
		return true;
	}

}
