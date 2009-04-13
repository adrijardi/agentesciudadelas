package comportamientos_jugador;

import conceptos.Distrito;
import utils.Filtros;
import acciones.DarDistritos;
import acciones.ObtenerDistritos;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jugador.AgJugador;

public class PedirCartas extends Behaviour {
	
	private final AgJugador _agj;
	private final Behaviour beh;
	private final AID raid;

	public PedirCartas(AgJugador agj, Behaviour ft, AID aid) {
		_agj = agj;
		beh = ft;
		raid = aid;
	}

	@Override
	public void action() {
		ObtenerDistritos od = new ObtenerDistritos();
		od.setJugador(_agj.getJugador());
		_agj.sendMSG(ACLMessage.REQUEST, raid, od, Filtros.ACCION_JUGADOR);
		
		// Se espera a que se reciban los distritos para seleccionar
		ACLMessage msg = _agj.reciveBlockingMessage(Filtros.DARDISTRITOS, true);
		
		try {
			DarDistritos contenido =(DarDistritos) _agj.getContentManager().extractContent(msg);
			Distrito[] descartados = _agj.descartaDistritos(contenido.getDistritos());
			
			// Se envia la respuesta con los descartados
			DarDistritos response = new DarDistritos();
			response.setDistritos(descartados);
			_agj.sendMSG(ACLMessage.INFORM, raid, response, Filtros.DARDISTRITOS);
			
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
